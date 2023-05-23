// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.ai.openai.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/** Post body schema to create a prompt completion from a deployment. */
@Fluent
public final class CompletionsOptions {
    /*
     * An optional prompt to complete from, encoded as a string, a list of strings, or
     * a list of token lists. Defaults to <|endoftext|>. The prompt to complete from.
     * If you would like to provide multiple prompts, use the POST variant of this
     * method. Note that <|endoftext|> is the document separator that the model sees
     * during training, so if a prompt is not specified the model will generate as if
     * from the beginning of a new document. Maximum allowed size of string list is
     * 2048.
     */
    @JsonProperty(value = "prompt")
    private List<String> prompt;

    /*
     * The maximum number of tokens to generate. Has minimum of 0.
     */
    @JsonProperty(value = "max_tokens")
    private Integer maxTokens;

    /*
     * What sampling temperature to use. Higher values means the model will take more
     * risks. Try 0.9 for more creative applications, and 0 (argmax sampling) for ones
     * with a well-defined answer.
     * We generally recommend using this or `top_p` but
     * not both.
     * Minimum of 0 and maximum of 2 allowed.
     *
     */
    @JsonProperty(value = "temperature")
    private Double temperature;

    /*
     * An alternative to sampling with temperature, called nucleus sampling, where the
     * model considers the results of the tokens with top_p probability mass. So 0.1
     * means only the tokens comprising the top 10% probability mass are
     * considered.
     * We generally recommend using this or `temperature` but not
     * both.
     * Minimum of 0 and maximum of 1 allowed.
     *
     */
    @JsonProperty(value = "top_p")
    private Double topP;

    /*
     * Defaults to null. Modify the likelihood of specified tokens appearing in the
     * completion. Accepts a json object that maps tokens (specified by their token ID
     * in the GPT tokenizer) to an associated bias value from -100 to 100. You can use
     * this tokenizer tool (which works for both GPT-2 and GPT-3) to convert text to
     * token IDs. Mathematically, the bias is added to the logits generated by the
     * model prior to sampling. The exact effect will vary per model, but values
     * between -1 and 1 should decrease or increase likelihood of selection; values
     * like -100 or 100 should result in a ban or exclusive selection of the relevant
     * token. As an example, you can pass {"50256" &#58; -100} to prevent the
     * <|endoftext|> token from being generated.
     */
    @JsonProperty(value = "logit_bias")
    private Map<String, Integer> logitBias;

    /*
     * The ID of the end-user, for use in tracking and rate-limiting.
     */
    @JsonProperty(value = "user")
    private String user;

    /*
     * How many snippets to generate for each prompt. Minimum of 1 and maximum of 128
     * allowed.
     */
    @JsonProperty(value = "n")
    private Integer n;

    /*
     * Include the log probabilities on the `logprobs` most likely tokens, as well the
     * chosen tokens. So for example, if `logprobs` is 10, the API will return a list
     * of the 10 most likely tokens. If `logprobs` is 0, only the chosen tokens will
     * have logprobs returned. Minimum of 0 and maximum of 100 allowed.
     */
    @JsonProperty(value = "logprobs")
    private Integer logprobs;

    /*
     * The name of the model to use
     */
    @JsonProperty(value = "model")
    private String model;

    /*
     * Echo back the prompt in addition to the completion
     */
    @JsonProperty(value = "echo")
    private Boolean echo;

    /*
     * A sequence which indicates the end of the current document.
     */
    @JsonProperty(value = "stop")
    private List<String> stop;

    /*
     * Completion configuration
     */
    @JsonProperty(value = "completion_config")
    private String completionConfig;

    /*
     * can be used to disable any server-side caching, 0=no cache, 1=prompt prefix
     * enabled, 2=full cache
     */
    @JsonProperty(value = "cache_level")
    private Integer cacheLevel;

    /*
     * How much to penalize new tokens based on their existing frequency in the text
     * so far. Decreases the model's likelihood to repeat the same line verbatim. Has
     * minimum of -2 and maximum of 2.
     */
    @JsonProperty(value = "presence_penalty")
    private Double presencePenalty;

    /*
     * How much to penalize new tokens based on whether they appear in the text so
     * far. Increases the model's likelihood to talk about new topics.
     */
    @JsonProperty(value = "frequency_penalty")
    private Double frequencyPenalty;

    /*
     * How many generations to create server side, and display only the best. Will not
     * stream intermediate progress if best_of > 1. Has maximum value of 128.
     */
    @JsonProperty(value = "best_of")
    private Integer bestOf;

    /** Creates an instance of CompletionsOptions class. */
    public CompletionsOptions() {}

    /**
     * Get the prompt property: An optional prompt to complete from, encoded as a string, a list of strings, or a list
     * of token lists. Defaults to &lt;|endoftext|&gt;. The prompt to complete from. If you would like to provide
     * multiple prompts, use the POST variant of this method. Note that &lt;|endoftext|&gt; is the document separator
     * that the model sees during training, so if a prompt is not specified the model will generate as if from the
     * beginning of a new document. Maximum allowed size of string list is 2048.
     *
     * @return the prompt value.
     */
    public List<String> getPrompt() {
        return this.prompt;
    }

    /**
     * Set the prompt property: An optional prompt to complete from, encoded as a string, a list of strings, or a list
     * of token lists. Defaults to &lt;|endoftext|&gt;. The prompt to complete from. If you would like to provide
     * multiple prompts, use the POST variant of this method. Note that &lt;|endoftext|&gt; is the document separator
     * that the model sees during training, so if a prompt is not specified the model will generate as if from the
     * beginning of a new document. Maximum allowed size of string list is 2048.
     *
     * @param prompt the prompt value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setPrompt(List<String> prompt) {
        this.prompt = prompt;
        return this;
    }

    /**
     * Get the maxTokens property: The maximum number of tokens to generate. Has minimum of 0.
     *
     * @return the maxTokens value.
     */
    public Integer getMaxTokens() {
        return this.maxTokens;
    }

    /**
     * Set the maxTokens property: The maximum number of tokens to generate. Has minimum of 0.
     *
     * @param maxTokens the maxTokens value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
        return this;
    }

    /**
     * Get the temperature property: What sampling temperature to use. Higher values means the model will take more
     * risks. Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer. We
     * generally recommend using this or `top_p` but not both. Minimum of 0 and maximum of 2 allowed.
     *
     * @return the temperature value.
     */
    public Double getTemperature() {
        return this.temperature;
    }

    /**
     * Set the temperature property: What sampling temperature to use. Higher values means the model will take more
     * risks. Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer. We
     * generally recommend using this or `top_p` but not both. Minimum of 0 and maximum of 2 allowed.
     *
     * @param temperature the temperature value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setTemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    /**
     * Get the topP property: An alternative to sampling with temperature, called nucleus sampling, where the model
     * considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top
     * 10% probability mass are considered. We generally recommend using this or `temperature` but not both. Minimum of
     * 0 and maximum of 1 allowed.
     *
     * @return the topP value.
     */
    public Double getTopP() {
        return this.topP;
    }

    /**
     * Set the topP property: An alternative to sampling with temperature, called nucleus sampling, where the model
     * considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top
     * 10% probability mass are considered. We generally recommend using this or `temperature` but not both. Minimum of
     * 0 and maximum of 1 allowed.
     *
     * @param topP the topP value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setTopP(Double topP) {
        this.topP = topP;
        return this;
    }

    /**
     * Get the logitBias property: Defaults to null. Modify the likelihood of specified tokens appearing in the
     * completion. Accepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an
     * associated bias value from -100 to 100. You can use this tokenizer tool (which works for both GPT-2 and GPT-3) to
     * convert text to token IDs. Mathematically, the bias is added to the logits generated by the model prior to
     * sampling. The exact effect will vary per model, but values between -1 and 1 should decrease or increase
     * likelihood of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant
     * token. As an example, you can pass {"50256" &amp;#58; -100} to prevent the &lt;|endoftext|&gt; token from being
     * generated.
     *
     * @return the logitBias value.
     */
    public Map<String, Integer> getLogitBias() {
        return this.logitBias;
    }

    /**
     * Set the logitBias property: Defaults to null. Modify the likelihood of specified tokens appearing in the
     * completion. Accepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an
     * associated bias value from -100 to 100. You can use this tokenizer tool (which works for both GPT-2 and GPT-3) to
     * convert text to token IDs. Mathematically, the bias is added to the logits generated by the model prior to
     * sampling. The exact effect will vary per model, but values between -1 and 1 should decrease or increase
     * likelihood of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant
     * token. As an example, you can pass {"50256" &amp;#58; -100} to prevent the &lt;|endoftext|&gt; token from being
     * generated.
     *
     * @param logitBias the logitBias value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setLogitBias(Map<String, Integer> logitBias) {
        this.logitBias = logitBias;
        return this;
    }

    /**
     * Get the user property: The ID of the end-user, for use in tracking and rate-limiting.
     *
     * @return the user value.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Set the user property: The ID of the end-user, for use in tracking and rate-limiting.
     *
     * @param user the user value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setUser(String user) {
        this.user = user;
        return this;
    }

    /**
     * Get the n property: How many snippets to generate for each prompt. Minimum of 1 and maximum of 128 allowed.
     *
     * @return the n value.
     */
    public Integer getN() {
        return this.n;
    }

    /**
     * Set the n property: How many snippets to generate for each prompt. Minimum of 1 and maximum of 128 allowed.
     *
     * @param n the n value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setN(Integer n) {
        this.n = n;
        return this;
    }

    /**
     * Get the logprobs property: Include the log probabilities on the `logprobs` most likely tokens, as well the chosen
     * tokens. So for example, if `logprobs` is 10, the API will return a list of the 10 most likely tokens. If
     * `logprobs` is 0, only the chosen tokens will have logprobs returned. Minimum of 0 and maximum of 100 allowed.
     *
     * @return the logprobs value.
     */
    public Integer getLogprobs() {
        return this.logprobs;
    }

    /**
     * Set the logprobs property: Include the log probabilities on the `logprobs` most likely tokens, as well the chosen
     * tokens. So for example, if `logprobs` is 10, the API will return a list of the 10 most likely tokens. If
     * `logprobs` is 0, only the chosen tokens will have logprobs returned. Minimum of 0 and maximum of 100 allowed.
     *
     * @param logprobs the logprobs value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setLogprobs(Integer logprobs) {
        this.logprobs = logprobs;
        return this;
    }

    /**
     * Get the model property: The name of the model to use.
     *
     * @return the model value.
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Set the model property: The name of the model to use.
     *
     * @param model the model value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setModel(String model) {
        this.model = model;
        return this;
    }

    /**
     * Get the echo property: Echo back the prompt in addition to the completion.
     *
     * @return the echo value.
     */
    public Boolean isEcho() {
        return this.echo;
    }

    /**
     * Set the echo property: Echo back the prompt in addition to the completion.
     *
     * @param echo the echo value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setEcho(Boolean echo) {
        this.echo = echo;
        return this;
    }

    /**
     * Get the stop property: A sequence which indicates the end of the current document.
     *
     * @return the stop value.
     */
    public List<String> getStop() {
        return this.stop;
    }

    /**
     * Set the stop property: A sequence which indicates the end of the current document.
     *
     * @param stop the stop value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setStop(List<String> stop) {
        this.stop = stop;
        return this;
    }

    /**
     * Get the completionConfig property: Completion configuration.
     *
     * @return the completionConfig value.
     */
    public String getCompletionConfig() {
        return this.completionConfig;
    }

    /**
     * Set the completionConfig property: Completion configuration.
     *
     * @param completionConfig the completionConfig value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setCompletionConfig(String completionConfig) {
        this.completionConfig = completionConfig;
        return this;
    }

    /**
     * Get the cacheLevel property: can be used to disable any server-side caching, 0=no cache, 1=prompt prefix enabled,
     * 2=full cache.
     *
     * @return the cacheLevel value.
     */
    public Integer getCacheLevel() {
        return this.cacheLevel;
    }

    /**
     * Set the cacheLevel property: can be used to disable any server-side caching, 0=no cache, 1=prompt prefix enabled,
     * 2=full cache.
     *
     * @param cacheLevel the cacheLevel value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setCacheLevel(Integer cacheLevel) {
        this.cacheLevel = cacheLevel;
        return this;
    }

    /**
     * Get the presencePenalty property: How much to penalize new tokens based on their existing frequency in the text
     * so far. Decreases the model's likelihood to repeat the same line verbatim. Has minimum of -2 and maximum of 2.
     *
     * @return the presencePenalty value.
     */
    public Double getPresencePenalty() {
        return this.presencePenalty;
    }

    /**
     * Set the presencePenalty property: How much to penalize new tokens based on their existing frequency in the text
     * so far. Decreases the model's likelihood to repeat the same line verbatim. Has minimum of -2 and maximum of 2.
     *
     * @param presencePenalty the presencePenalty value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setPresencePenalty(Double presencePenalty) {
        this.presencePenalty = presencePenalty;
        return this;
    }

    /**
     * Get the frequencyPenalty property: How much to penalize new tokens based on whether they appear in the text so
     * far. Increases the model's likelihood to talk about new topics.
     *
     * @return the frequencyPenalty value.
     */
    public Double getFrequencyPenalty() {
        return this.frequencyPenalty;
    }

    /**
     * Set the frequencyPenalty property: How much to penalize new tokens based on whether they appear in the text so
     * far. Increases the model's likelihood to talk about new topics.
     *
     * @param frequencyPenalty the frequencyPenalty value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setFrequencyPenalty(Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
        return this;
    }

    /**
     * Get the bestOf property: How many generations to create server side, and display only the best. Will not stream
     * intermediate progress if best_of &gt; 1. Has maximum value of 128.
     *
     * @return the bestOf value.
     */
    public Integer getBestOf() {
        return this.bestOf;
    }

    /**
     * Set the bestOf property: How many generations to create server side, and display only the best. Will not stream
     * intermediate progress if best_of &gt; 1. Has maximum value of 128.
     *
     * @param bestOf the bestOf value to set.
     * @return the CompletionsOptions object itself.
     */
    public CompletionsOptions setBestOf(Integer bestOf) {
        this.bestOf = bestOf;
        return this;
    }
}
