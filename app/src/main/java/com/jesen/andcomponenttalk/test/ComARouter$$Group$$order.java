package com.jesen.andcomponenttalk.test;

import com.jesen.component_arouter_api.core.ComARouterLoadGroup;
import com.jesen.component_arouter_api.core.ComARouterLoadPath;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟ComARouter路由器的组文件
 * 仅仅是样板代码，展示了预期的要生成的类的样式
 * */
public class ComARouter$$Group$$order implements ComARouterLoadGroup {

    @Override
    public Map<String, Class<? extends ComARouterLoadPath>> loadGroup() {
        Map<String, Class<? extends ComARouterLoadPath>> groupMap = new HashMap<>();
        groupMap.put("order",ComARouter$$Path$$order.class);
        return groupMap;
    }
}
