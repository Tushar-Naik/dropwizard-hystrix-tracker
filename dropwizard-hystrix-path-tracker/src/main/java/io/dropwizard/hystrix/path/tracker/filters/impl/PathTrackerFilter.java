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

import io.dropwizard.hystrix.path.tracker.hystrix.TrackerCommand;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This is a filter, that will track the path of the incoming request, using a {@link TrackerCommand.VoidTracker}
 * It will be wrapping the inner operations for the uri, with a SEMAPHORE based {@link com.netflix.hystrix.HystrixCommand}
 *
 * @author tushar.naik
 * @version 1.0
 * @see TrackerCommand
 * @since 10/10/16 - 5:23 PM
 */
public class PathTrackerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String uriPath = httpServletRequest.getRequestURI().split("\\?")[0];
        String path = identifyPath(uriPath);
        new TrackerCommand.VoidTracker(path, () -> filterChain.doFilter(servletRequest, servletResponse)).execute();
    }

    @Override
    public void destroy() {
    }

    private String identifyPath(String requestUri) {
        String[] decomposedApi = StringUtils.split(requestUri, "/");
        return decomposedApi[decomposedApi.length - 1];
    }
}
