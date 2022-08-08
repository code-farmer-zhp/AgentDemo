package org.example.plugin.thread;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.util.concurrent.Callable;

public class ThreadAdvice {

    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className,
                             @Advice.Origin("#m") String methodName,
                             @Advice.Argument(value = 0, readOnly = false, typing = Assigner.Typing.DYNAMIC) Object task) {
        if (task instanceof TtlRunnable) {
            task = TtlRunnable.get(((TtlRunnable) task).getRunnable());
        } else if (task instanceof TtlCallable) {
            task = TtlCallable.get(((TtlCallable<?>) task).getCallable());
        } else if (task instanceof Runnable) {
            task = TtlRunnable.get((Runnable) task);
        } else if (task instanceof Callable) {
            task = TtlCallable.get((Callable) task);
        }
    }
}
