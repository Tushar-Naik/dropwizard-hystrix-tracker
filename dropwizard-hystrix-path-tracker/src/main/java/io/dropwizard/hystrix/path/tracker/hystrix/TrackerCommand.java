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

package io.dropwizard.hystrix.path.tracker.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import io.dropwizard.hystrix.path.tracker.util.ERunnable;
import io.dropwizard.hystrix.path.tracker.util.ESupplier;

/**
 * This is a HystrixCommand command that returns what the supplier gives
 * The execution mode here is SEMAPHORE only
 * Use this to track the execution of any supplier
 *
 * @author tushar.naik
 * @version 1.0  21/03/17 - 6:05 PM
 */
public class TrackerCommand<T> extends HystrixCommand<T> {

    private ESupplier<T> supplier;

    public TrackerCommand(String key, ESupplier<T> supplier) {
        super(HystrixCommand.Setter
                      .withGroupKey(HystrixCommandGroupKey.Factory.asKey(key))
                      .andCommandKey(HystrixCommandKey.Factory.asKey(key))
                      .andCommandPropertiesDefaults(
                              HystrixCommandProperties.Setter()
                                                      .withCircuitBreakerEnabled(false)
                                                      .withExecutionTimeoutEnabled(false)
                                                      .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                                      .withExecutionIsolationSemaphoreMaxConcurrentRequests(Integer.MAX_VALUE)
                                                      .withFallbackIsolationSemaphoreMaxConcurrentRequests(Integer.MAX_VALUE)));
        this.supplier = supplier;
    }

    public TrackerCommand(String key, String group, ESupplier<T> supplier) {
        super(HystrixCommand.Setter
                      .withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                      .andCommandKey(HystrixCommandKey.Factory.asKey(key))
                      .andCommandPropertiesDefaults(
                              HystrixCommandProperties.Setter()
                                                      .withCircuitBreakerEnabled(false)
                                                      .withExecutionTimeoutEnabled(false)
                                                      .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                                      .withExecutionIsolationSemaphoreMaxConcurrentRequests(Integer.MAX_VALUE)
                                                      .withFallbackIsolationSemaphoreMaxConcurrentRequests(Integer.MAX_VALUE)));
        this.supplier = supplier;
    }

    @Override
    protected T run() throws Exception {
        return supplier.get();
    }

    /**
     * This is a HystrixCommand that extends a {@link TrackerCommand<Void>} to track the execution of a runnable
     * Use this to track the execution of any {@link Runnable}
     */
    public static class VoidTracker extends TrackerCommand<Void> {

        /**
         * @param key      Command Key
         * @param runnable runnable reference
         */
        public VoidTracker(String key, ERunnable runnable) {
            super(key, () -> {
                runnable.run();
                return null;
            });
        }
    }
}
