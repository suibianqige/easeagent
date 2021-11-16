/*
 * Copyright (c) 2017, MegaEase
 * All rights reserved.
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

package com.megaease.easeagent.plugin.bridge;

import com.megaease.easeagent.plugin.api.Context;
import com.megaease.easeagent.plugin.api.InitializeContext;
import com.megaease.easeagent.plugin.api.Reporter;
import com.megaease.easeagent.plugin.api.config.Config;
import com.megaease.easeagent.plugin.api.logging.ILoggerFactory;
import com.megaease.easeagent.plugin.api.config.IConfigFactory;
import com.megaease.easeagent.plugin.api.logging.Logger;
import com.megaease.easeagent.plugin.api.logging.Mdc;
import com.megaease.easeagent.plugin.api.metric.MetricRegistry;
import com.megaease.easeagent.plugin.api.metric.MetricRegistrySupplier;
import com.megaease.easeagent.plugin.api.metric.name.NameFactory;
import com.megaease.easeagent.plugin.api.metric.name.Tags;

import java.util.function.Supplier;

/**
 * the bridge api will be initiated when agent startup
 */
public final class EaseAgent {
    public static volatile MetricRegistrySupplier metricRegistrySupplier = NoOpMetrics.NO_OP_METRIC_SUPPLIER;
    public static volatile Supplier<Context> contextSupplier = () -> NoOpContext.NO_OP_CONTEXT;
    public static volatile Supplier<InitializeContext> initializeContextSupplier = () -> NoOpContext.NO_OP_CONTEXT;
    public static volatile ILoggerFactory loggerFactory = NoOpLoggerFactory.INSTANCE;
    public static volatile Mdc loggerMdc = NoOpLoggerFactory.NO_OP_MDC_INSTANCE;
    public static volatile IConfigFactory configFactory = new NoOpConfigFactory();

    /*
     * api interface add here
     */


    /**
     * @see ILoggerFactory#getLogger(Class)
     */
    public static Logger getLogger(Class clzss) {
        return loggerFactory.getLogger(clzss);
    }

    /**
     * @see MetricRegistrySupplier#newMetricRegistry(Config, NameFactory, Tags)
     */
    public static MetricRegistry newMetricRegistry(Config config, NameFactory nameFactory, Tags tags) {
        return metricRegistrySupplier.newMetricRegistry(config, nameFactory, tags);
    }

    /**
     * @see MetricRegistrySupplier#reporter(Config)
     */
    public static Reporter metricReporter(Config config) {
        return metricRegistrySupplier.reporter(config);
    }
}
