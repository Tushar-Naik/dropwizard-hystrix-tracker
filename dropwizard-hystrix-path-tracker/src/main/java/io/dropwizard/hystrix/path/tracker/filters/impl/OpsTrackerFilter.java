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
import io.dropwizard.hystrix.path.tracker.hystrix.TrackerCommand;
import io.dropwizard.setup.Environment;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.EnumSet;

/**
 * This filter wraps inner operations within a SEMAPHORE based HystrixCommand ({@link TrackerCommand.VoidTracker})
 * The {@link TrackerCommand.VoidTracker} will be an overall OPS tracker, hinged at a method level
 * (ie: GET, POST, PUT, etc)
 *
 * @author tushar.naik
 * @version 1.0
 * @since 05/10/16 - 3:32 PM
 */
public class OpsTrackerFilter implements EnvironmentConsumer, Filter {

    @Override
    public void accept(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("OpsTracker", this);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod();
        new TrackerCommand.VoidTracker(method, () -> filterChain.doFilter(servletRequest, servletResponse)).execute();
    }

    @Override
    public void destroy() {
    }
}
