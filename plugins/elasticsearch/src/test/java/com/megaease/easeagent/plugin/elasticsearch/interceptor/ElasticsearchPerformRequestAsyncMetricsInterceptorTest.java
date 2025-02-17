/*
 * Copyright (c) 2021 MegaEase
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

package com.megaease.easeagent.plugin.elasticsearch.interceptor;

import com.megaease.easeagent.mock.plugin.api.MockEaseAgent;
import com.megaease.easeagent.plugin.api.Context;
import com.megaease.easeagent.plugin.bridge.EaseAgent;
import com.megaease.easeagent.plugin.interceptor.MethodInfo;
import org.junit.Before;
import org.junit.Test;

@MockEaseAgent
public class ElasticsearchPerformRequestAsyncMetricsInterceptorTest extends ElasticsearchBaseTest {
    ElasticsearchPerformRequestAsync4MetricsInterceptor interceptor;

    @Before
    public void before() {
        super.before();
        interceptor = new ElasticsearchPerformRequestAsync4MetricsInterceptor();
    }

    @Test
    public void performSuccess() {
        MethodInfo methodInfo = MethodInfo.builder()
            .invoker(this)
            .method("perform")
            .args(new Object[]{request, responseListener})
            .build();

        Context context = EaseAgent.getContext();
        interceptor.init(config, "", "", "");
        interceptor.before(methodInfo, context);
        AsyncResponse4MetricsListener traceListener = (AsyncResponse4MetricsListener) methodInfo.getArgs()[1];
        traceListener.onSuccess(this.successResponse);
        this.assertMetric(interceptor.getElasticsearchMetric().getNameFactory(),
            interceptor.getElasticsearchMetric().getMetricRegistry(), true);

    }

    @Test
    public void performFail() {
        MethodInfo methodInfo = MethodInfo.builder()
            .invoker(this)
            .method("perform")
            .args(new Object[]{request, responseListener})
            .build();

        Context context = EaseAgent.getContext();
        interceptor.init(config, "", "", "");
        interceptor.before(methodInfo, context);
        AsyncResponse4MetricsListener traceListener = (AsyncResponse4MetricsListener) methodInfo.getArgs()[1];
        traceListener.onSuccess(this.failResponse);
        this.assertMetric(interceptor.getElasticsearchMetric().getNameFactory(),
            interceptor.getElasticsearchMetric().getMetricRegistry(), false);
    }

    @Test
    public void performFailThrowable() {
        MethodInfo methodInfo = MethodInfo.builder()
            .invoker(this)
            .method("perform")
            .args(new Object[]{request, responseListener})
            .build();

        Context context = EaseAgent.getContext();
        interceptor.init(config, "", "", "");
        interceptor.before(methodInfo, context);
        AsyncResponse4MetricsListener traceListener = (AsyncResponse4MetricsListener) methodInfo.getArgs()[1];
        traceListener.onFailure(new RuntimeException(this.errMsg));
        this.assertMetric(interceptor.getElasticsearchMetric().getNameFactory(),
            interceptor.getElasticsearchMetric().getMetricRegistry(), false);
    }
}
