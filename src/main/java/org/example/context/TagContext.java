package org.example.context;

import com.alibaba.ttl.TransmittableThreadLocal;

public class TagContext {

    public static final String TAG = "tag";

    private static final TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal();


    public static void setTag(String tag) {
        threadLocal.set(tag);
    }

    public static String getTag() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
