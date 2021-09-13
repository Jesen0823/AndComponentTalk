package com.jesen.order.impl;

import com.jesen.common_lib.order.drawable.OrderDrawable;
import com.jesen.component_annotation.ComARouter;
import com.jesen.order.R;

/**
 * 订单模块对外暴露接口实现类，其他模块可以获取返回res资源
 */
@ComARouter(path = "/order/getDrawable")
public class OrderDrawableImpl implements OrderDrawable {
    @Override
    public int getDrawable() {
        return R.drawable.pic_test;
    }
}
