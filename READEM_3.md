# APT + JavaPoet


### JavaPoet

* APT + JavaPoet =超级利刃

JavaPoet是square推出的开源java代码生成框架，提供Java Api生成.java源文件这个框架功能非常实用，也是我们习惯的Java面向对象OOP语法
可以很方便的使用它根据注解生成对应代码
通过这种自动化生成代码的方式，
可以让我们用更加简洁优雅的方式要替代繁琐冗杂的重复工作
[JavaPoet项目主页及源码:] (https://github.com/square/javapoet)

```Groovy
// apt 需要的依赖
annotationProcessor 'com.google.auto.service:auto-service:1.0-rc4'
compileOnly 'com.google.auto.service:auto-service:1.0-rc4'

// JavaPoet 需要的依赖
// 依赖JavaPoet库,帮助我们通过类调用的形式来生成Java代码
implementation "com.squareup:javapoet: 1.9.0"
```

*  JavaPoet中常用的类

| 类对象             | 说明|
| :------: | :------: |
| MethodSpec         | 代表一个构造函数或方法声明 |
| TypeSpec           | 代表一个类，接口,或者枚举声明 |
| FieldSpec          | 代表一个成员变量,一个字段声明 |
| JavaFile           | 包含一个顶级类的Java文件 |
| ParameterSpec      | 用来创建参数 |
| AnnotationSpec     | 用来创建注解 |
| ClassName          | 用来包装一个类 |
| TypeName           | 类型，如在添加返回值类型使用TypeName.VOID |

* JavaPoet中字符串格式化

| 符号             | 用途|
| :------: | :------: |
| $L  | 字面量，如: "int value=$L", 8 |
| $S  | 字符串,如:$S, "position" |
| $T  | 类、接口，如:$T, MainActivity |
| $N  | 变量，如:user.$N, name |


