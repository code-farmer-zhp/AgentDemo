package org.example.plugin.feign;

import net.bytebuddy.asm.Advice;

import org.apache.commons.lang.StringUtils;
import org.example.context.TagContext;

import java.net.HttpURLConnection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URLConnectionAdvice {

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Return(readOnly = false) HttpURLConnection urlConnection) {
        System.out.println("url connection");
        String tag = TagContext.getTag();
        if (StringUtils.isNotEmpty(tag)) {
            // log.info("透传tag{}", tag);
            System.out.println("透传tag:" + tag);
            urlConnection.addRequestProperty(TagContext.TAG, tag);
        }
    }
}
