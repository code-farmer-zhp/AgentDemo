package org.example.plugin.nacos;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import org.example.plugin.Advisor;

import java.util.List;

public class NacosAdvisor implements Advisor {
    @Override
    public ElementMatcher<TypeDescription> buildTypesMatcher() {
        return ElementMatchers.hasSuperType(ElementMatchers.named("com.netflix.loadbalancer.AbstractServerPredicate"))
                .and(ElementMatchers.not(ElementMatchers.isAbstract()));
    }

    @Override
    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
        return ElementMatchers.isMethod()
                .and(ElementMatchers.takesArguments(2))
                .and(ElementMatchers.takesArgument(0, List.class))
                .and(ElementMatchers.takesArgument(1, Object.class))
                .and(ElementMatchers.nameStartsWith("getEligibleServers"));
    }

    @Override
    public Class<?> adviceClass() {
        return NacosBalancerAdvice.class;
    }
}
