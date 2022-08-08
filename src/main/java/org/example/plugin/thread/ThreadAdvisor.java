package org.example.plugin.thread;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import org.example.plugin.Advisor;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;

public class ThreadAdvisor implements Advisor {

    @Override
    public ElementMatcher<TypeDescription> buildTypesMatcher() {
        return ElementMatchers.any()
                .and(ElementMatchers.hasSuperType(ElementMatchers.named("java.util.concurrent.ExecutorService"))
                        .or(ElementMatchers.hasSuperType(ElementMatchers.named("java.util.concurrent.CompletionService"))))
                .or(ElementMatchers.hasSuperType(ElementMatchers.named("java.util.concurrent.Executor")))
                .and(ElementMatchers.not(ElementMatchers.isInterface()))
                .and(ElementMatchers.not(ElementMatchers.isAbstract()));
    }

    @Override
    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
        return ElementMatchers.isMethod()
                .and(ElementMatchers.named("submit")
                        .or(ElementMatchers.named("execute"))
                        .or(ElementMatchers.named("schedule"))
                        .or(ElementMatchers.named("scheduleAtFixedRate"))
                        .or(ElementMatchers.named("scheduleWithFixedDelay"))
                        .or(ElementMatchers.named("invoke")))
                .and(ElementMatchers.takesArgument(0, Runnable.class)
                        .or(ElementMatchers.takesArgument(0, Callable.class)));
    }

    @Override
    public Class<?> adviceClass() {
        return ThreadAdvice.class;
    }
}
