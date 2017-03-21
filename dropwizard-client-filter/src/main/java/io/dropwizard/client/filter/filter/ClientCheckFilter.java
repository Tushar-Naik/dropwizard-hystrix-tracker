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

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.client.filter.config.ClientFilterConfig;
import io.dropwizard.client.filter.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Set;

/**
 * This filter may be used to put a check on clients
 * ie: check if a header is a valid one or not
 *
 * @author tushar.naik
 * @version 1.0
 * @since 08/10/16 - 11:22 PM
 */
@Slf4j
@Provider
@AllArgsConstructor
public class ClientCheckFilter implements ContainerRequestFilter, RequestAbort<Object> {

    /* client filter config */
    private ClientFilterConfig config;

    /**
     * this will filter out calls that do not contain headers (part of {@link ClientFilterConfig})
     * and check if the header value is a valid one or now
     *
     * @param requestContext request context
     * @throws IOException ex
     * @see ClientFilterConfig
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        /* return if the config is null */
        if (config == null) {
            return;
        }

        String header = config.getHeader();
        Set<String> validClients = config.getValidClients();

        /* return if any config is missing, after issuing a warning */
        if (Strings.isNullOrEmpty(header)) {
            log.warn("unable to filter since header in ClientFilterConfig is null. Passing through request nevertheless");
        } else if (Utils.isCollectionNullOrEmpty(validClients)) {
            log.warn("unable to filter since validClients in ClientFilterConfig is null. Passing through request nevertheless");
        } else {
            String client = requestContext.getHeaderString(header);
            if (Strings.isNullOrEmpty(client)) {
                abort(requestContext, Response.Status.FORBIDDEN, ImmutableMap.of("response", "Client header: " + header + " must be defined."));
            } else if (!validClients.contains(client)) {
                abort(requestContext, Response.Status.FORBIDDEN, ImmutableMap.of("response", "Client shall not pass. Invalid client value"));
            }
        }
    }
}
