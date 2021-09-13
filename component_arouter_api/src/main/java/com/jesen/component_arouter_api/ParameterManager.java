package com.jesen.component_arouter_api;

import android.app.Activity;
import android.util.LruCache;

import androidx.annotation.NonNull;

import com.jesen.component_arouter_api.core.ParameterLoad;

/**
 * 参数Parameter加载管理
 */
public class ParameterManager {

    private static final int MAX_CACHE_SIZE = 163;

    private static ParameterManager instance;

    // Lru缓存， key:类名 value:参数ComParameter加载接口
    private LruCache<String, ParameterLoad> cache;

    // apt生成的获取参数类文件，后缀名
    private static final String FILE_SUFFIX_NAME = "$$ComParameter";

    private ParameterManager() {
        // 初始化，赋值缓存最大值
        cache = new LruCache<>(MAX_CACHE_SIZE);
    }

    // 单例
    public static ParameterManager getInstance() {
        if (instance == null) {
            synchronized (ParameterManager.class) {
                if (instance == null) {
                    instance = new ParameterManager();
                }
            }
        }
        return instance;
    }

    public void loadParameter(@NonNull Activity activity) {
        String className = activity.getClass().getName();
        // ParameterLoad接口的实现类
        ParameterLoad iParemeter = cache.get(className);

        try {
            // 缓存中如果没有
            if (iParemeter == null) {
                Class<?> clazz = Class.forName(className + FILE_SUFFIX_NAME);
                iParemeter = (ParameterLoad) clazz.newInstance();
                cache.put(className, iParemeter);
            }

            iParemeter.loadParameter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
