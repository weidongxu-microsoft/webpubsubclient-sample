// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package io.weidongxu.util.chat;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.Completions;
import com.azure.ai.openai.models.CompletionsOptions;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.util.Configuration;
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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

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
                        String conversation = getConversation(text);
                        String reply = complete(conversation);
                        addReply(reply);
                        client.sendToGroup(group, reply, new SendToGroupOptions().setNoEcho(true));
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

    private static final List<String> conversation = new CopyOnWriteArrayList<>();
    static {
        conversation.add("<|im_start|>system\nThe following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\n<|im_end|>");
    }

    private static String getConversation(String input) {
        conversation.add("<|im_start|>user\n" + input + "\n<|im_end|>");
        return String.join("\n", conversation) + "\n<|im_start|>assistant\n";
    }

    private static void addReply(String reply) {
        conversation.add("<|im_start|>assistant\n" + reply + "\n<|im_end|>");
    }

    private static OpenAIClient completionsClient;

    private static String complete(String prompt) {
        final String model = "gpt-35-turbo";

        if (completionsClient == null) {
            completionsClient = new OpenAIClientBuilder()
                    .endpoint(Configuration.getGlobalConfiguration().get("ENDPOINT"))
                    .credential(new AzureKeyCredential(Configuration.getGlobalConfiguration().get("API_KEY")))
                    .httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                    .buildClient();
        }

        Completions completions = completionsClient.getCompletions(model,
                new CompletionsOptions(Collections.singletonList(prompt))
                        .setMaxTokens(512)
                        .setStop(Collections.singletonList("<|im_end|>")));
        return completions.getChoices().get(0).getText();
    }
}
