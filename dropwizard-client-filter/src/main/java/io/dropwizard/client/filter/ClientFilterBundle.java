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

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.client.filter.config.ClientFilterConfig;
import io.dropwizard.client.filter.filter.ClientCheckFeature;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author tushar.naik
 * @version 1.0  22/03/17 - 12:21 AM
 */
public abstract class ClientFilterBundle<T extends Configuration> implements ConfiguredBundle<T> {

    public abstract ClientFilterConfig getClientFilterConfig(T t);

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        environment.jersey().register(new ClientCheckFeature(getClientFilterConfig(configuration)));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }
}
