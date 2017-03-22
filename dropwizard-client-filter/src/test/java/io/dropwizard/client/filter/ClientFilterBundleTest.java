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
    }

    @Test
    public void testClientFiltering() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format("http://localhost:%d/service/api/cat/api1", RULE.getLocalPort()))
//                .header("X-ABC-HEADER", "Client1")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 403);
    }


}