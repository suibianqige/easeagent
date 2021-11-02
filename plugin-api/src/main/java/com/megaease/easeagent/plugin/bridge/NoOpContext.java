package com.megaease.easeagent.plugin.bridge;

import com.megaease.easeagent.plugin.api.Context;
import com.megaease.easeagent.plugin.api.InitializeContext;
import com.megaease.easeagent.plugin.api.config.Config;
import com.megaease.easeagent.plugin.api.context.AsyncContext;
import com.megaease.easeagent.plugin.api.context.ProgressContext;
import com.megaease.easeagent.plugin.api.trace.*;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public class NoOpContext {
    public static final NoopContext NO_OP_CONTEXT = NoopContext.INSTANCE;
    public static final EmptyAsyncContext NO_OP_ASYNC_CONTEXT = EmptyAsyncContext.INSTANCE;
    public static final NoopProgressContext NO_OP_PROGRESS_CONTEXT = NoopProgressContext.INSTANCE;

    public static class NoopContext implements InitializeContext {
        private static final NoopContext INSTANCE = new NoopContext();

        @Override
        public boolean isNoop() {
            return true;
        }

        @Override
        public Tracing currentTracing() {
            return NoOpTracer.NO_OP_TRACING;
        }

        @Override
        public <V> V get(Object key) {
            return null;
        }

        @Override
        public <V> V remove(Object key) {
            return null;
        }

        @Override
        public <V> V put(Object key, V value) {
            return value;
        }

        @Override
        public Config getConfig() {
            return NoOpConfig.INSTANCE;
        }

        @Override
        public int enter(Object key) {
            return 0;
        }

        @Override
        public int out(Object key) {
            return 0;
        }

        @Override
        public AsyncContext exportAsync(Request request) {
            return EmptyAsyncContext.INSTANCE;
        }

        @Override
        public Span importAsync(AsyncContext snapshot) {
            return NoOpTracer.NO_OP_SPAN;
        }

        @Override
        public ProgressContext nextProgress(Request request) {
            return NoopProgressContext.INSTANCE;
        }

        @Override
        public ProgressContext importProgress(Request request) {
            return NoopProgressContext.INSTANCE;
        }

        @Override
        public Map<Object, Object> clear() {
            return Collections.emptyMap();
        }

        public void setCurrentTracing(Tracing tracing) {

        }

        @Override
        public void pushConfig(Config config) {

        }

        @Override
        public Config popConfig() {
            return NoOpConfig.INSTANCE;
        }
    }


    public static class EmptyAsyncContext implements AsyncContext {
        private static final EmptyAsyncContext INSTANCE = new EmptyAsyncContext();

        @Override
        public boolean isNoop() {
            return true;
        }

        @Override
        public Tracing getTracer() {
            return NoOpTracer.NO_OP_TRACING;
        }

        @Override
        public Context getContext() {
            return NoopContext.INSTANCE;
        }

        @Override
        public Span importToCurr() {
            return NoOpTracer.NO_OP_SPAN;
        }

        @Override
        public Map<Object, Object> getAll() {
            return Collections.emptyMap();
        }

        @Override
        public void putAll(Map<Object, Object> context) {

        }
    }

    public static class NoopProgressContext implements ProgressContext {
        private static final NoopProgressContext INSTANCE = new NoopProgressContext();

        @Override
        public Span span() {
            return NoOpTracer.NO_OP_SPAN;
        }

        @Override
        public Scope scope() {
            return NoOpTracer.NO_OP_SCOPE;
        }

        @Override
        public void setHeader(String name, String value) {

        }

        @Override
        public Map<String, String> getHeader() {
            return Collections.emptyMap();
        }

        @Override
        public AsyncContext async() {
            return EmptyAsyncContext.INSTANCE;
        }

        @Override
        public Context getContext() {
            return NoopContext.INSTANCE;
        }

        @Override
        public void finish(Response response) {

        }
    }

}
