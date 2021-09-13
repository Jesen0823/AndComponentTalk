package com.jesen.andcomponenttalk.test;

import com.jesen.component_annotation.model.RouterBean;
import com.jesen.component_arouter_api.core.ComARouterLoadPath;
import com.jesen.order.Order_MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟ComARouter路由器的组文件对应的路径
 * 仅仅是样板代码，展示了预期的要生成的类的样式
 */
public class ComARouter$$Path$$order implements ComARouterLoadPath {

    @Override
    public Map<String, RouterBean> loadPath() {
        Map<String, RouterBean> pathMap = new HashMap<>();
        pathMap.put("/order/Order_MainActivity", RouterBean.create(
                RouterBean.Type.ACTIVITY,
                Order_MainActivity.class,
                "/order/Order_MainActivity",
                "order")
        );
        return pathMap;
    }
}
