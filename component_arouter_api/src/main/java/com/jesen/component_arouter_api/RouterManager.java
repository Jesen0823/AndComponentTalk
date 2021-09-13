package com.jesen.component_arouter_api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.jesen.component_annotation.model.RouterBean;
import com.jesen.component_arouter_api.core.Call;
import com.jesen.component_arouter_api.core.ComARouterLoadGroup;
import com.jesen.component_arouter_api.core.ComARouterLoadPath;

public class RouterManager {

    private static final int MAX_GROUP_CACHE_SIZE = 200;
    private static final int MAX_PATH_CACHE_SIZE = 200;
    private static final String TAG = "RouterManager";

    // 路由组名
    private String group;

    // 路由path路径
    private String path;

    private static RouterManager instance;

    // Lru缓存 key:组名 value: 路由组Group加载接口
    private LruCache<String, ComARouterLoadGroup> groupCache;

    // Lru缓存 key:路径 value: 路由path路径加载接口
    private LruCache<String, ComARouterLoadPath> pathCache;

    // apt生成的文件前缀名
    private static final String GROUP_FILE_PREFIX_NAME = "ComARouter$$Group$$";

    private RouterManager() {
        groupCache = new LruCache<>(MAX_GROUP_CACHE_SIZE);
        pathCache = new LruCache<>(MAX_PATH_CACHE_SIZE);
    }

    public static RouterManager getInstance() {
        if (instance == null) {
            synchronized (RouterManager.class) {
                if (instance == null) {
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }

    // 传递路由地址
    public BundleManager build(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("路由路径参数错误,应按照 /app/MainActivity 方式传参");
        }
        group = subPathGetGroup(path);
        this.path = path;
        return new BundleManager();
    }

    // 从路径截取字符串获得组名
    private String subPathGetGroup(String path) {
        if (path.lastIndexOf("/") == 0) {
            throw new IllegalArgumentException("路由路径参数错误,应按照 /app/MainActivity 方式传参");
        }
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        if (TextUtils.isEmpty(finalGroup)) {
            throw new IllegalArgumentException("路由路径参数错误,应按照 /app/MainActivity 方式传参");
        }
        return finalGroup;
    }

    /**
     * 开始跳转
     *
     * @param context       上下文
     * @param bundleManager 参数管理
     * @param code          可以是requestCode，也可以是resultCode，取决于isResult参数是否调用启动startActivityForResult
     * @return 普通跳转可忽略，夸模块返回CALL接口
     */
    public Object navigation(Context context, BundleManager bundleManager, int code) {

        StringBuilder sb = new StringBuilder();
        // ComARouter$$Group$$order
        String groupClassName = sb
                .append(context.getPackageName())
                .append(".apt.")
                .append(GROUP_FILE_PREFIX_NAME)
                .append(group)
                .toString();
        Log.d(TAG, "navigation, groupClassName: " + groupClassName);
        try {
            // 读取路由组group类文件，缓存，懒加载
            ComARouterLoadGroup groupLoad = groupCache.get(this.group);
            if (groupLoad == null) {
                // 加载apt路由组group的类文件 ComARouter$$Group$$order

                Class<?> clazz = Class.forName(groupClassName);
                // 初始化类, ComARouter$$Group$$order 实现了 ComARouterLoadGroup接口
                groupLoad = (ComARouterLoadGroup) clazz.newInstance();
                groupCache.put(group, groupLoad);
            }

            if (groupLoad.loadGroup().isEmpty()) {
                throw new RuntimeException("路由表Group加载失败...");
            }

            // 读取路由path路径文件缓存
            ComARouterLoadPath pathLoad = pathCache.get(path);
            if (pathLoad == null) {
                // 通过组Group加载接口，获取Path加载接口
                Class<? extends ComARouterLoadPath> clazz = groupLoad.loadGroup().get(group);
                if (clazz != null) {
                    pathLoad = clazz.newInstance();
                }
                if (pathLoad != null) {
                    pathCache.put(path, pathLoad);
                }
            }

            if (pathLoad != null) {
                // tempMap赋值
                pathLoad.loadPath();

                if (pathLoad.loadPath().isEmpty()) {
                    throw new RuntimeException("路由表path加载失败...");
                }
                RouterBean routerBean = pathLoad.loadPath().get(path);
                if (routerBean != null) {
                    // 类型判断，方便拓展
                    switch (routerBean.getType()) {
                        case ACTIVITY:

                            Intent intent = new Intent(context, routerBean.getClazz());
                            intent.putExtras(bundleManager.getBundle());
                            if (bundleManager.isResult()) {
                                ((Activity) context).setResult(code, intent);
                                ((Activity) context).finish();
                            }

                            // 此处code 是requestCode
                            if (code > 0) {
                                ((Activity) context).startActivityForResult(intent, code, bundleManager.getBundle());
                            } else {
                                ((Activity) context).startActivity(intent, bundleManager.getBundle());
                            }
                            break;
                        case CALL:
                            // getClazz()拿到的是接口的实现类
                            Class<?> clazz = routerBean.getClazz();
                            Call call = (Call) clazz.newInstance();
                            bundleManager.setCall(call);
                            return bundleManager.getCall();
                        default:
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
