package com.jesen.andcomponenttalk.user;

import com.jesen.andcomponenttalk.model.UseInfo;
import com.jesen.common_lib.bean.user.BaseUser;
import com.jesen.common_lib.bean.user.IUser;
import com.jesen.component_annotation.ComARouter;

@ComARouter(path = "/app/getUserInfo")
public class IUserImpl implements IUser {
    @Override
    public BaseUser getUserInfo() {
        UseInfo useInfo = new UseInfo();
        useInfo.setName("Jesen");
        useInfo.setAccount("xxx@qq.com");
        useInfo.setPassword("1234");
        useInfo.setVipLevel(12);
        return useInfo;
    }
}
