package com.jesen.javapoet_compiler_lib;

import com.google.auto.service.AutoService;
import com.jesen.javapoet_annotation_lib.PARouter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 注解处理器
 */

// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)
// 允许/支持的注解类型，让注解处理器处理（新增annotation module）
@SupportedAnnotationTypes("com.jesen.javapoet_annotation_lib.PARouter")
// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
// 注解处理器接收的参数
@SupportedOptions("content")
public class PARouterProcessor extends AbstractProcessor {

    // 操作Element工具类 (类、函数、属性都是Element)
    private Elements elementUtils;

    // type(类信息)工具类，包含用于操作TypeMirror的工具方法
    private Types typeUtils;

    // Messager用来报告错误，警告和其他提示信息
    private Messager messager;

    // 文件生成器 类/资源，Filter用来创建新的源文件，class文件以及辅助文件
    private Filer filer;

    /**
     * 该方法主要用于一些初始化的操作，通过该方法的参数ProcessingEnvironment可以获取一些列有用的工具类
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        // 通过ProcessingEnvironment去获取对应的参数
        String content = processingEnv.getOptions().get("content");
        // 如果用Diagnostic.Kind.ERROR，会异常自动结束
        messager.printMessage(Diagnostic.Kind.NOTE, content);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        // 获取所有带ARouter注解的 类节点,并遍历
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(PARouter.class);
        for (Element element : elements) {
            // 通过类节点获取包节点路径
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "---the class being annotation is: " + className);
            // 最终想生成的类文件名
            String finalClassName = className + "$$PARouter";

            // javapoet ,butterknife就是用了JavaPoet
            // 高级写法，javapoet构建工具，参考（https://github.com/JakeWharton/butterknife）
            try {
                // 获取类之上@ARouter注解的path值
                PARouter aRouter = element.getAnnotation(PARouter.class);

                // 构建方法体
                MethodSpec method = MethodSpec.methodBuilder("findTargetClass") // 方法名
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(Class.class) // 返回值Class<?>
                        .addParameter(String.class, "path") // 参数(String path)
                        // 方法内容拼接：
                        // return path.equals("/app/MainActivity") ? MainActivity.class : null
                        .addStatement("return path.equals($S) ? $T.class : null",
                                aRouter.path(), ClassName.get((TypeElement) element))
                        .build(); // 构建

                // 构建类
                TypeSpec type = TypeSpec.classBuilder(finalClassName)
                        .addModifiers(Modifier.PUBLIC) //, Modifier.FINAL)
                        .addMethod(method) // 添加方法体
                        .build(); // 构建

                // 在指定的包名下，生成Java类文件
                JavaFile javaFile = JavaFile.builder(packageName, type)
                        .build();
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
