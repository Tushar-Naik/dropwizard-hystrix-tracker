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

package io.dropwizard.hystrix.path.tracker.trackers;

import io.dropwizard.hystrix.path.tracker.filters.FilterBuilder;
import io.dropwizard.hystrix.path.tracker.filters.impl.PathTrackerFilter;
import io.dropwizard.setup.Environment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

/**
 * @author tushar.naik
 * @version 1.0
 * @since 09/10/16 - 11:42 AM
 */
@Provider
@Slf4j
@AllArgsConstructor
//TODO make this more generic, with supplier sort of design
public class RuntimeFeature implements DynamicFeature {

    /* environment used to register the filters */
    final private Environment environment;

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        TrackPath trackPath = resourceInfo.getResourceMethod().getAnnotation(TrackPath.class);
        if (trackPath != null) {
            final Class<? extends Filter> filterClass = PathTrackerFilter.class;
            log.info("Registering filter:{} method:{} key:{} resourcePath:{}",
                     filterClass.getSimpleName(),
                     resourceInfo.getResourceMethod().getName(),
                     trackPath.commandKey(),
                     trackPath.pathRegex());
            FilterBuilder.newBuilder()
                         .addUrlMapping(trackPath.pathRegex())
                         .name(filterClass.getSimpleName() + ":" + UUID.randomUUID().toString())
                         .withClass(filterClass)
                         .addInitParam("commandKey", trackPath.commandKey())
                         .build()
                         .consume(environment);
        }
    }
}
