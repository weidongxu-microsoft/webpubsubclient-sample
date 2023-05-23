// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.ai.openai;

import com.azure.core.util.ServiceVersion;

/** Service version of OpenAiClient. */
public enum OpenAiServiceVersion implements ServiceVersion {
    /** Enum value 2022-12-01. */
    V2022_12_01("2022-12-01");

    private final String version;

    OpenAiServiceVersion(String version) {
        this.version = version;
    }

    /** {@inheritDoc} */
    @Override
    public String getVersion() {
        return this.version;
    }

    /**
     * Gets the latest service version supported by this client library.
     *
     * @return The latest {@link OpenAiServiceVersion}.
     */
    public static OpenAiServiceVersion getLatest() {
        return V2022_12_01;
    }
}