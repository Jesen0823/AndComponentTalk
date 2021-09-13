package com.jesen.component_compiler.utils;
/**
 * 常量类
 * */
public class Constants {

    // 注解处理器中支持的注解类型
    public static final String IROUTER_ANNOTATION_TYPES = "com.jesen.component_annotation.ComARouter";

    // 每个子模块的模块名
    public static final String MODULE_NAME = "moduleName";

    // 包名，用于存放APT生成的类文件
    public static final String APT_PACKAGE = "packageNameForAPT";

    // Activity全类名
    public static final String ACTIVITY = "android.app.Activity";

    // 包名前缀封装
    static final String BASE_PACKAGE = "com.jesen.component_arouter_api";
    // 路由组Group加载接口
    public static final String AROUTER_GROUP = BASE_PACKAGE + ".core.ComARouterLoadGroup";
    // 路由组Group对应的详细Path加载接口
    public static final String AROUTER_PATH = BASE_PACKAGE + ".core.ComARouterLoadPath";

    // 路由组Group，参数名
    public static final String GROUP_PARAMETER_NAME = "groupMap";
    // 路由组Group，方法名
    public static final String GROUP_METHOD_NAME = "loadGroup";
    // 路由组Group对应的详细Path，参数名
    public static final String PATH_PARAMETER_NAME = "pathMap";
    // 路由组Group对应的详细Path，方法名
    public static final String PATH_METHOD_NAME = "loadPath";

    // APT生成的路由组Group源文件名
    public static final String GROUP_FILE_NAME = "ComARouter$$Group$$";
    // APT生成的路由组Group对应的详细Path源文件名
    public static final String PATH_FILE_NAME = "ComARouter$$Path$$";
}
