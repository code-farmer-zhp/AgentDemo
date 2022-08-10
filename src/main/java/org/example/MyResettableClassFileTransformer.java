package org.example;

import com.alibaba.ttl.threadpool.agent.internal.logging.Logger;
import com.alibaba.ttl.threadpool.agent.internal.transformlet.JavassistTransformlet;
import com.alibaba.ttl.threadpool.agent.internal.transformlet.impl.TtlExecutorTransformlet;
import com.alibaba.ttl.threadpool.agent.internal.transformlet.impl.TtlForkJoinTransformlet;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyResettableClassFileTransformer implements ResettableClassFileTransformer {


    private ResettableClassFileTransformer resettableClassFileTransformer;

    private ClassFileTransformer transformer;

    public MyResettableClassFileTransformer(ResettableClassFileTransformer resettableClassFileTransformer) {
        this.resettableClassFileTransformer = resettableClassFileTransformer;

        //Logger.setLoggerImplType("STDOUT");
        List<JavassistTransformlet> transformletList = new ArrayList<>();
        transformletList.add(new TtlExecutorTransformlet(false));
        transformletList.add(new TtlForkJoinTransformlet(false));
        try {
            Constructor<?> declaredConstructor = Class.forName("com.alibaba.ttl.threadpool.agent.TtlTransformer").getDeclaredConstructor(List.class);
            declaredConstructor.setAccessible(true);
            transformer = (ClassFileTransformer) declaredConstructor.newInstance(transformletList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<AgentBuilder.Transformer> iterator(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, Class<?> classBeingRedefined, ProtectionDomain protectionDomain) {
        return resettableClassFileTransformer.iterator(typeDescription, classLoader, module, classBeingRedefined, protectionDomain);
    }

    @Override
    public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy) {
        return resettableClassFileTransformer.reset(instrumentation, redefinitionStrategy);

    }

    @Override
    public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy,
                         AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator) {
        return resettableClassFileTransformer.reset(instrumentation, redefinitionStrategy, redefinitionBatchAllocator);
    }

    @Override
    public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy,
                         AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy) {
        return resettableClassFileTransformer.reset(instrumentation, redefinitionStrategy, redefinitionDiscoveryStrategy);
    }

    @Override
    public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy,
                         AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator,
                         AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy) {
        return resettableClassFileTransformer.reset(instrumentation, redefinitionStrategy, redefinitionBatchAllocator, redefinitionDiscoveryStrategy);
    }

    @Override
    public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy,
                         AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy,
                         AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
        return resettableClassFileTransformer.reset(instrumentation, redefinitionStrategy,
                redefinitionDiscoveryStrategy, redefinitionListener);

    }

    @Override
    public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy,
                         AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator,
                         AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
        return resettableClassFileTransformer.reset(instrumentation, redefinitionStrategy,
                redefinitionBatchAllocator, redefinitionListener);
    }

    @Override
    public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy,
                         AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy,
                         AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator,
                         AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
        return resettableClassFileTransformer.reset(instrumentation, redefinitionStrategy,
                redefinitionDiscoveryStrategy, redefinitionBatchAllocator, redefinitionListener);
    }

    @Override
    public boolean reset(Instrumentation instrumentation, ResettableClassFileTransformer classFileTransformer,
                         AgentBuilder.RedefinitionStrategy redefinitionStrategy,
                         AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy,
                         AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator,
                         AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
        return resettableClassFileTransformer.reset(instrumentation, classFileTransformer, redefinitionStrategy,
                redefinitionDiscoveryStrategy, redefinitionBatchAllocator, redefinitionListener);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        byte[] transform = resettableClassFileTransformer.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        if (transform != null) {
            return transform;
        }
        return transformer.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
    }
}
