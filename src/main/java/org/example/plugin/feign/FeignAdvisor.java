package org.example.plugin.feign;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import org.example.plugin.Advisor;

import java.net.URL;

public class FeignAdvisor implements Advisor {
    @Override
    public ElementMatcher<TypeDescription> buildTypesMatcher() {
        return ElementMatchers.hasSuperType(ElementMatchers.named("feign.Client$Default"))
                .and(ElementMatchers.not(ElementMatchers.isAbstract()));
    }

    @Override
    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
        return ElementMatchers.isMethod()
                .and(ElementMatchers.takesArguments(1))
                .and(ElementMatchers.takesArgument(0, URL.class))
                .and(ElementMatchers.nameStartsWith("getConnection"));
    }

    @Override
    public Class<?> adviceClass() {
        return URLConnectionAdvice.class;
    }
}
