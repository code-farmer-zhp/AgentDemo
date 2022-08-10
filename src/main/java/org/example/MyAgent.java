package org.example;

import com.alibaba.ttl.threadpool.agent.TtlAgent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

import org.example.plugin.Advisor;
import org.example.utils.PackageUtil;

import java.lang.instrument.Instrumentation;
import java.util.List;

public class MyAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("loading dynamic agent ..." + MyAgent.class.getClassLoader());

        TtlAgent.premain(agentArgs, inst);
        AgentBuilder agentBuilder = new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(new AgentBuilder.TransformerDecorator() {
                    @Override
                    public ResettableClassFileTransformer decorate(ResettableClassFileTransformer classFileTransformer) {

                        return new MyResettableClassFileTransformer(classFileTransformer);

                    }
                }).disableClassFormatChanges();

        List<Class<?>> advisors = PackageUtil.getClass("org.example.plugin", Advisor.class);

        for (Class<?> advisorClass : advisors) {
            if (advisorClass.isInterface()) {
                continue;
            }
            Advisor advisor;
            try {
                advisor = (Advisor) advisorClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
                @Override
                public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                        TypeDescription typeDescription,
                                                        ClassLoader classLoader, JavaModule javaModule) {
                    builder = builder.visit(Advice.to(advisor.adviceClass()).on(advisor.buildMethodsMatcher()));
                    return builder;
                }
            };

            agentBuilder = agentBuilder.type(advisor.buildTypesMatcher()).transform(transformer);

        }

        agentBuilder.installOn(inst);

    }

}
