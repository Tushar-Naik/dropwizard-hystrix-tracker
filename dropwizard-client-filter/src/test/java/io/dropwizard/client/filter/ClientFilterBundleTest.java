/*
 * Copyright 2017 Tushar-Naik <tushar.knaik@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.dropwizard.client.filter;

import io.dropwizard.client.filter.application.TestApp;
import io.dropwizard.client.filter.application.TestConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

/**
 * @author tushar.naik
 * @version 1.0  22/03/17 - 2:12 PM
 */
public class ClientFilterBundleTest {
    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE =
            new DropwizardAppRule<>(TestApp.class, ResourceHelpers.resourceFilePath("test-config.yaml"));

    @Test
    public void testClientNonFiltering() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format("http://localhost:%d/service/api/cat/api1", RULE.getLocalPort()))
                .header("X-ABC-HEADER", "Client1")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        request = new Request.Builder()
                .url(String.format("http://localhost:%d/service/api/cat/api1", RULE.getLocalPort()))
                .header("X-ABC-HEADER", "Client2")
                .build();
        response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
    }

    /**
     * testing no headers
     */
    @Test
    public void testClientFiltering() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format("http://localhost:%d/service/api/cat/api1", RULE.getLocalPort()))
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 403);
    }

    /**
     * testing invalid headers
     */
    @Test
    public void testInvalidClientFiltering() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format("http://localhost:%d/service/api/cat/api1", RULE.getLocalPort()))
                .header("X-ABC-HEADER", "Client4")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 403);
    }


}