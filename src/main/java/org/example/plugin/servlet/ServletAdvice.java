package org.example.plugin.servlet;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import org.apache.commons.lang.StringUtils;
import org.example.context.TagContext;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ServletAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Argument(value = 0, readOnly = false, typing = Assigner.Typing.DYNAMIC) Object req,
                             @Advice.Argument(value = 1, readOnly = false, typing = Assigner.Typing.DYNAMIC) Object resp) {

        HttpServletRequest request = (HttpServletRequest) req;
        String tag = request.getHeader(TagContext.TAG);
        if (StringUtils.isNotEmpty(tag)) {
            //log.info("有对应的tag标记{}", tag);
            System.out.println("有对应的tag标记" + tag);
            TagContext.setTag(tag);
        }
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit() {
        TagContext.remove();
    }

}
