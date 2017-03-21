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

package io.dropwizard.client.filter.filter;

import io.dropwizard.client.filter.config.ClientFilterConfig;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * DynamicFeature to register {@link ClientCheckFilter} on methods containing {@link ClientRestriction} annotation
 *
 * @author tushar.naik
 * @version 1.0
 * @see ClientRestriction,ClientCheckFilter
 * @since 23/10/16 - 9:01 PM
 */
@AllArgsConstructor
public class ClientCheckFeature implements DynamicFeature {

    /* Logger for logging */
    private static final Logger logger = LoggerFactory.getLogger(ClientCheckFeature.class.getSimpleName());

    /* client filter configurations that define valid headers */
    private ClientFilterConfig clientFilterConfig;

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        Class<ClientRestriction> clientRestrictionClass = ClientRestriction.class;
        if (resourceInfo.getResourceMethod().getAnnotation(clientRestrictionClass) != null) {
            String message = "Registering filter: " + ClientCheckFilter.class.getSimpleName() + " against method: " + resourceInfo.getResourceMethod().getName();
            System.out.println(message);
            logger.info(message);
            featureContext.register(new ClientCheckFilter(clientFilterConfig));
        }
    }
}
