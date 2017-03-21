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

package io.dropwizard.hystrix.path.tracker.filters;

import io.dropwizard.setup.Environment;
import lombok.AllArgsConstructor;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * @author tushar.naik
 * @version 1.0
 * @since 05/10/16 - 4:13 PM
 */

@AllArgsConstructor
public class EnvironmentConsumingFilter {
    private String name;
    private Class<? extends Filter> klass;
    private Map<String, String> initParams;
    private List<String> urlMappings;

    public void consume(Environment environment) {
        /* if name has not been initialized */
        if (name == null) {
            name = klass.getSimpleName();
        }

        /* register dynamic filter, for class, with provided url mappings and init params */
        FilterRegistration.Dynamic filter = environment.servlets().addFilter(name, klass);
        urlMappings.forEach(u -> filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, u));
        initParams.forEach(filter::setInitParameter);
    }
}
