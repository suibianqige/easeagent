/*
 * Copyright (c) 2021, MegaEase
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.megaease.easeagent.mock.metrics;

import com.codahale.metrics.MetricRegistry;
import com.megaease.easeagent.metrics.MetricProviderImpl;
import com.megaease.easeagent.metrics.impl.MetricRegistryImpl;
import com.megaease.easeagent.plugin.api.metric.ServiceMetric;

public class MockMetricUtils {
    public static void clear(com.megaease.easeagent.plugin.api.metric.MetricRegistry metricRegistry) {
        if (!(metricRegistry instanceof MetricRegistryImpl)) {
            return;
        }
        MetricRegistry metricRegistry1 = ((MetricRegistryImpl) metricRegistry).getMetricRegistry();
        for (String s : metricRegistry1.getNames()) {
            metricRegistry.remove(s);
        }
    }

    public static void clear(ServiceMetric serviceMetric) {
        MockMetricUtils.clear(serviceMetric.getMetricRegistry());
    }

    public static void clearAll() {
        for (com.megaease.easeagent.plugin.api.metric.MetricRegistry metricRegistry : MetricProviderImpl.getRegistryList()) {
            clear(metricRegistry);
        }
    }
}
