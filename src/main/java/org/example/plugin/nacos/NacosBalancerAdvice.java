package org.example.plugin.nacos;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.Server;

import net.bytebuddy.asm.Advice;

import org.apache.commons.lang.StringUtils;
import org.example.context.TagContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NacosBalancerAdvice {

    @Advice.OnMethodExit
    private static void filterServer(@Advice.Return(readOnly = false) List<Server> instances) {
        if (instances == null) {
            return;
        }
        String tag = TagContext.getTag();
        if (StringUtils.isEmpty(tag)) {
            return;
        }

        List<Server> newList = new ArrayList<>();
        for (Server server : instances) {
            Map<String, String> metadata = ((NacosServer) server).getMetadata();
            if (metadata == null) {
                continue;
            }
            if (tag.equals(metadata.get(TagContext.TAG))) {
                newList.add(server);
            }
        }
        instances = newList;
    }
}
