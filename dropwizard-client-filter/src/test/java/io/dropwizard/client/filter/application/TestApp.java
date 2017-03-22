package io.dropwizard.client.filter.application;

import io.dropwizard.Application;
import io.dropwizard.client.filter.ClientFilterBundle;
import io.dropwizard.client.filter.config.ClientFilterConfig;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author tushar.naik
 * @version 1.0  22/03/17 - 2:13 PM
 */
public class TestApp extends Application<TestConfiguration> {
    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new ClientFilterBundle<TestConfiguration>() {
            @Override
            public ClientFilterConfig getClientFilterConfig(TestConfiguration testConfiguration) {
                return testConfiguration.getClientFilterConfig();
            }
        });
    }

    @Override
    public void run(TestConfiguration testConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new TestResource());
    }
}
