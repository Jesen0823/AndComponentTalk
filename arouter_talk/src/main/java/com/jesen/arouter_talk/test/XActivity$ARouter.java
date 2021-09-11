package com.jesen.arouter_talk.test;

import com.jesen.arouter_talk.MainActivity;

/**
 *  模拟注解器将要生成的类
 * */
public class XActivity$ARouter {

    public static Class<?> findTargetClass(String path){
        if (path.equalsIgnoreCase("/app/MainActivity")){
            return MainActivity.class;

        }
        return null;
    }
}
