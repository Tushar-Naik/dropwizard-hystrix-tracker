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

import io.dropwizard.setup.Environment;

import java.util.function.Consumer;

/**
 * @author tushar.naik
 * @version 1.0
 * @since 05/10/16 - 3:30 PM
 */
@FunctionalInterface
public interface EnvironmentConsumer extends Consumer<Environment> {
}
