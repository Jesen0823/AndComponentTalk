package com.jesen.order.impl;

import com.jesen.common_lib.bean.user.BaseUser;
import com.jesen.common_lib.bean.user.IUser;
import com.jesen.component_annotation.ComARouter;
import com.jesen.order.model.UserInfo;

@ComARouter(path = "/order/getUserInfo")
public class OrderUserImpl implements IUser {
    @Override
    public BaseUser getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("尊享顾客");
        userInfo.setAccount("kkx@126.com");
        userInfo.setPassword("9876");
        userInfo.setVipLevel(20);
        return userInfo;
    }
}
