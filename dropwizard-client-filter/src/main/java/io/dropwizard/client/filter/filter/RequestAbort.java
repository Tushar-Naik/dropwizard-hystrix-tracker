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

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

/**
 * @author tushar.naik
 * @version 1.0
 * @since 10/10/16 - 2:24 PM
 */
public interface RequestAbort<T> {

    /**
     * abort request context as bad request (400 status)
     *
     * @param requestContext request context
     * @param response       response
     */
    default void abort(ContainerRequestContext requestContext, T response) {
        abort(requestContext, Response.Status.BAD_REQUEST, response);
    }

    /**
     * abort request with given response status, and error response
     *
     * @param requestContext request context
     * @param status         error http response status
     * @param response       error response
     */
    default void abort(ContainerRequestContext requestContext, Response.Status status, T response) {
        requestContext.abortWith(Response.status(status)
                .entity(response)
                .build());
    }
}
