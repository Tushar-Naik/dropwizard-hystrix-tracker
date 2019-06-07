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

package io.dropwizard.hystrix.path.tracker.filters.impl;

import io.dropwizard.hystrix.path.tracker.trackers.EnvironmentConsumer;
import io.dropwizard.hystrix.path.tracker.filters.FilterBuilder;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

/**
 * A simple CORS (Cross Origin Resource) setup registration
 *
 * @author tushar.naik
 * @version 1.0
 * @since 01/10/16 - 10:41 AM
 */
public class DefaultCorsSetupFilter implements EnvironmentConsumer {

    @Override
    public void accept(Environment environment) {
        new FilterBuilder().name("CORS")
                .withFilter(new CrossOriginFilter())
                .addUrlMapping("/*")
                .addInitParam(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS")
                .addInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*")
                .addInitParam(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*")
                .addInitParam("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin")
                .addInitParam("allowCredentials", "true")
                .build()
                .accept(environment);
    }
}
