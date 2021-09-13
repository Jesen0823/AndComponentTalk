package com.jesen.personal.usr;

import com.jesen.common_lib.bean.user.BaseUser;
import com.jesen.common_lib.bean.user.IUser;
import com.jesen.component_annotation.ComARouter;
import com.jesen.personal.model.UserInfo;

/**
 * personal模块实现的内容
 */
@ComARouter(path = "/personal/getUserInfo")
public class PersonalUserImpl implements IUser {

    @Override
    public BaseUser getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Person用户");
        userInfo.setAccount("person@126.com");
        userInfo.setPassword("88888");
        userInfo.setVipLevel(1);
        return userInfo;
    }
}