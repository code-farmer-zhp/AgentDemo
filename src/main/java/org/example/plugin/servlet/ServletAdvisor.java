package org.example.plugin.servlet;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import org.example.plugin.Advisor;

public class ServletAdvisor implements Advisor {
    @Override
    public ElementMatcher<TypeDescription> buildTypesMatcher() {
        return ElementMatchers.hasSuperType(ElementMatchers.named("org.springframework.web.servlet.DispatcherServlet"))
                .and(ElementMatchers.not(ElementMatchers.isAbstract()));
    }

    @Override
    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
        return ElementMatchers.isMethod()
                .and(ElementMatchers.takesArguments(2))
                .and(ElementMatchers.takesArgument(0, ElementMatchers.named("javax.servlet.http.HttpServletRequest")))
                .and(ElementMatchers.takesArgument(1, ElementMatchers.named("javax.servlet.http.HttpServletResponse")))
                .and(ElementMatchers.named("doDispatch"));
    }

    @Override
    public Class<?> adviceClass() {
        return ServletAdvice.class;
    }
}
