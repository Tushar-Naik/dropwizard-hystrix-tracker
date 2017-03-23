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

package io.dropwizard.hystrix.path.tracker;

import com.google.common.collect.Lists;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.hystrix.path.tracker.filters.impl.OpsTrackerFilter;
import io.dropwizard.hystrix.path.tracker.trackers.EnvironmentConsumer;
import io.dropwizard.hystrix.path.tracker.trackers.RuntimeFeature;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.List;

/**
 * Add this bundle to enable tracking on resource files containing {@link io.dropwizard.hystrix.path.tracker.trackers.TrackPath}
 *
 * @param <T> Type of configuration
 * @author tushar.naik
 * @version 1.0  21/03/17 - 5:29 PM
 */
public class TrackerBundle<T extends Configuration> implements ConfiguredBundle<T> {
    private List<EnvironmentConsumer> filterSetupCallbacks = Lists.newArrayList();

    public List<EnvironmentConsumer> getFilterSetupCallbacks() {
        return filterSetupCallbacks;
    }

    @Override
    public void initialize(final Bootstrap<?> bootstrap) {
        filterSetupCallbacks.add(new OpsTrackerFilter());
    }

    @Override
    public void run(final T t, final Environment environment) throws Exception {
        /* setup all filters */
        filterSetupCallbacks.forEach(c -> c.accept(environment));
        environment.jersey().register(new RuntimeFeature(environment));
    }
}