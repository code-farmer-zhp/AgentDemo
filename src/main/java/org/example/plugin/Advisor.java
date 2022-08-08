package org.example.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface Advisor {

    ElementMatcher<TypeDescription> buildTypesMatcher();


    ElementMatcher<MethodDescription> buildMethodsMatcher();


    Class<?> adviceClass();
}
