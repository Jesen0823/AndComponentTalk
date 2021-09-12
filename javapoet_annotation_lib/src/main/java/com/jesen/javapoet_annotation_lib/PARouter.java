package com.jesen.javapoet_annotation_lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 作用在类上面
@Target(ElementType.TYPE)
// 编译时
@Retention(RetentionPolicy.CLASS)
public @interface PARouter {
    String path();
}
