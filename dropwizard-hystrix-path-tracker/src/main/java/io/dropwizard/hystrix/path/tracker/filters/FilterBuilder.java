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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.dropwizard.hystrix.path.tracker.util.Builder;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;

/**
 * @author tushar.naik
 * @version 1.0
 * @since 05/10/16 - 3:34 PM
 */
public class FilterBuilder implements Builder<EnvironmentConsumingFilter> {

    private String name;
    private Filter filter;
    private Map<String, String> initParams = Maps.newConcurrentMap();
    private List<String> urlMappings = Lists.newArrayList();

    public static FilterBuilder newBuilder() {
        return new FilterBuilder();
    }

    public FilterBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FilterBuilder withFilter(Filter klass) {
        this.filter = klass;
        return this;
    }

    public FilterBuilder addInitParam(String key, String value) {
        initParams.put(key, value);
        return this;
    }

    public FilterBuilder addUrlMapping(String urlMapping) {
        urlMappings.add(urlMapping);
        return this;
    }

    @Override
    public EnvironmentConsumingFilter build() {
        return new EnvironmentConsumingFilter(name, filter, initParams, urlMappings);
    }
}
