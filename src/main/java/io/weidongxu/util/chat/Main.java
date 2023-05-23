// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package io.weidongxu.util.chat;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatMessage;
import com.azure.ai.openai.models.ChatMessageDelta;
import com.azure.ai.openai.models.ChatRole;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.util.Configuration;
import com.azure.core.util.IterableStream;
import com.azure.core.util.logging.ClientLogger;
import com.azure.messaging.webpubsub.WebPubSubServiceAsyncClient;
import com.azure.messaging.webpubsub.WebPubSubServiceClientBuilder;
import com.azure.messaging.webpubsub.client.WebPubSubClient;
import com.azure.messaging.webpubsub.client.WebPubSubClientBuilder;
import com.azure.messaging.webpubsub.client.models.GroupMessageEvent;
import com.azure.messaging.webpubsub.client.models.SendToGroupOptions;
import com.azure.messaging.webpubsub.client.models.WebPubSubClientCredential;
import com.azure.messaging.webpubsub.client.models.WebPubSubDataType;
import com.azure.messaging.webpubsub.models.GetClientAccessTokenOptions;
import com.azure.messaging.webpubsub.models.WebPubSubClientAccessToken;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    private static final ClientLogger LOGGER = new ClientLogger(Main.class);

    public static void main(String[] args) throws Exception {

        // browser https://learn.microsoft.com/azure/azure-web-pubsub/quickstart-live-demo

        String hubName = "Hub";
        String groupName = "Group1";
        String userName = "bot";

        // prepare the clientCredential
        WebPubSubServiceAsyncClient serviceClient = new WebPubSubServiceClientBuilder()
                .connectionString(Configuration.getGlobalConfiguration().get("CONNECTION_STRING"))
                .hub(hubName)
                .buildAsyncClient();
        WebPubSubClientCredential clientCredential = new WebPubSubClientCredential(Mono.defer(() ->
                serviceClient.getClientAccessToken(new GetClientAccessTokenOptions()
                                .setUserId(userName)
                                .addRole("webpubsub.joinLeaveGroup")
                                .addRole("webpubsub.sendToGroup"))
                        .map(WebPubSubClientAccessToken::getUrl)));

        CompletableFuture<Void> clientStopped = new CompletableFuture<>();

        // create client
        WebPubSubClient client = new WebPubSubClientBuilder()
                .credential(clientCredential)
                .buildClient();

        client.addOnGroupMessageEventHandler(event -> {
            String group = event.getGroup();
            if (groupName.equals(group) && !userName.equals(event.getFromUserId())) {
                if (event.getDataType() == WebPubSubDataType.TEXT || event.getDataType() == WebPubSubDataType.JSON) {
                    String text = getMessageText(event);
                    if ("exit".equals(text)) {
                        // asked to exit
                        client.sendToGroup(group, "Goodbye.", new SendToGroupOptions().setNoEcho(true));
                        client.stop();
                    } else {
                        List<ChatMessage> conversation = getConversation(text);
                        streamCompletions(client, groupName, conversation);
                    }
                }
            }
        });
        client.addOnStoppedEventHandler(event -> clientStopped.complete(null));

        // start client, join group and send "Hello"
        client.start();
        client.joinGroup(groupName);
        client.sendToGroup(groupName, "Hello.", new SendToGroupOptions().setNoEcho(true));

        // wait for client to stop
        clientStopped.get();
    }

    private static String getMessageText(GroupMessageEvent message) {
        if (message.getDataType() == WebPubSubDataType.JSON) {
            return message.getData().toObject(String.class);
        } else if (message.getDataType() == WebPubSubDataType.TEXT) {
            return message.getData().toString();
        } else {
            return null;
        }
    }

    private static final List<ChatMessage> conversation = new CopyOnWriteArrayList<>();
    static {
        conversation.add(new ChatMessage(ChatRole.SYSTEM)
                .setContent("The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly."));
    }

    private static List<ChatMessage> getConversation(String input) {
        conversation.add(new ChatMessage(ChatRole.USER)
                .setContent(input));
        return conversation;
    }

    private static void addReply(String reply) {
        conversation.add(new ChatMessage(ChatRole.ASSISTANT)
                .setContent(reply));
    }

    private static OpenAIClient completionsClient;

    private static void streamCompletions(WebPubSubClient client, String groupName, List<ChatMessage> messages) {
        final String model = "gpt-4";

        if (completionsClient == null) {
            completionsClient = new OpenAIClientBuilder()
                    .endpoint(Configuration.getGlobalConfiguration().get("ENDPOINT"))
                    .credential(new AzureKeyCredential(Configuration.getGlobalConfiguration().get("API_KEY")))
                    .httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                    .buildClient();
        }

        IterableStream<ChatCompletions> completions = completionsClient.getChatCompletionsStream(model,
                new ChatCompletionsOptions(messages)
                        .setMaxTokens(1024));
        StringBuilder reply = new StringBuilder();
        StringBuilder line = new StringBuilder();
        for (ChatCompletions item : completions) {
            ChatMessageDelta message = item.getChoices().get(0).getDelta();
            if (message != null && message.getContent() != null) {
                LOGGER.info(message.getContent());

                reply.append(message.getContent());
                line.append(message.getContent());

                if (message.getContent().endsWith("\n")) {
                    client.sendToGroup(groupName, line.toString(), new SendToGroupOptions().setNoEcho(true));
                    line = new StringBuilder();
                }
            }
        }
        if (line.length() > 0) {
            client.sendToGroup(groupName, line.toString(), new SendToGroupOptions().setNoEcho(true));
        }

        addReply(reply.toString());
    }
}
