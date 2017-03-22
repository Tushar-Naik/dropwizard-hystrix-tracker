package io.dropwizard.client.filter.application;

import io.dropwizard.Configuration;
import io.dropwizard.client.filter.config.ClientFilterConfig;
import lombok.Data;

/**
 * @author tushar.naik
 * @version 1.0  22/03/17 - 2:12 PM
 */
@Data
public class TestConfiguration extends Configuration {
    private ClientFilterConfig clientFilterConfig;
}
