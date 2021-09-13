# 组件化项目的路由设计


### 注解配置

配置跟之前一样，都是两个java-library（注解annotation和注解器compiler）,不同之处有两点：

* 增加一个android-library: `component_arouter_api`

android-library需要增加两个接口：

```java
/**
 * 路由组Group加载数据接口
 */
public interface ComARouterLoadGroup {

    /**
     * 加载路由组Group数据
     * 比如："app", ARouter$$Path$$app.class（实现了ARouterLoadPath接口）
     *
     * @return key:"app", value:"app"分组对应的路由详细对象类
     */
    Map<String,Class<? extends ComARouterLoadPath>> loadGroup();
}

```
```java
/**
 * 路由组Group对应的详细Path加载数据接口
 * 比如：app分组对应有哪些类需要加载
 */
public interface ComARouterLoadPath {
    /**
     * 加载路由组Group中的Path详细数据
     * 比如："app"分组下有这些信息：
     *
     * @return key:"/app/MainActivity", value:MainActivity信息封装到RouterBean对象中
     */
    Map<String, RouterBean> loadPath();
}

```
* annotation 的java-library需要增加一个JavaBean作为Path的实体封装类：

```java

/**
 * 路由路径Path的最终实体封装类 + 构建者模式
 * 比如：app分组中的MainActivity对象，这个对象有更多的属性
 */

public class RouterBean {
    public enum Type {
        ACTIVITY

        // ... 扩展其他类型如Fragment
    }

    // 枚举类型：Activity
    private Type type;
    // 类节点
    private Element element;
    // 注解使用的类对象
    private Class<?> clazz;
    // 路由地址
    private String path;
    // 路由组
    private String group;

    private RouterBean(Builder builder) {
        this.type = builder.type;
        this.element = builder.element;
        this.clazz = builder.clazz;
        this.path = builder.path;
        this.group = builder.group;
    }

    private RouterBean(Type type, Class<?> clazz, String path, String group) {
        this.type = type;
        this.clazz = clazz;
        this.path = path;
        this.group = group;
    }

    // 对外提供简易版构造方法，主要是为了方便APT生成代码
    public static RouterBean create(Type type, Class<?> clazz, String path, String group) {
        return new RouterBean(type, clazz, path, group);
    }

    public Type getType() {
        return type;
    }

    // ...省略其他 getter() setter()

    /**
     * 构建者模式
     */
    public static class Builder {

        // 枚举类型：Activity
        private Type type;
        // 类节点
        private Element element;
        // 注解使用的类对象
        private Class<?> clazz;
        // 路由地址
        private String path;
        // 路由组
        private String group;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        // ...省略其他 getter() setter()

        // 最后的build或者create，往往是做参数的校验或者初始化赋值工作
        public RouterBean build() {
            if (path == null || path.length() == 0) {
                throw new IllegalArgumentException("path必填项为空，如：/app/MainActivity");
            }
            return new RouterBean(this);
        }
    }

    @Override
    public String toString() {
        return "RouterBean{" +
                "path='" + path + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}

```

### 生成什么样的代码

* **最终生成的代码样式：**
   * `ComARouter$$Group$$order.java` :

```java

/**
 * 模拟ComARouter路由器的组文件
 * 仅仅是样板代码，展示了预期的要生成的类的样式
 * */
public class ComARouter$$Group$$order implements ComARouterLoadGroup {

    @Override
    public Map<String, Class<? extends ComARouterLoadPath>> loadGroup() {
        Map<String, Class<? extends ComARouterLoadPath>> groupMap = new HashMap<>();
        groupMap.put("order",ComARouter$$Path$$order.class);
        return groupMap;
    }
}
```
  * `ComARouter$$Path$$order.java` :

```java
/**
 * 模拟ComARouter路由器的组文件对应的路径
 * 仅仅是样板代码，展示了预期的要生成的类的样式
 */
public class ComARouter$$Path$$order implements ComARouterLoadPath {

    @Override
    public Map<String, RouterBean> loadPath() {
        Map<String, RouterBean> pathMap = new HashMap<>();
        pathMap.put("/order/Order_MainActivity", RouterBean.create(
                RouterBean.Type.ACTIVITY,
                Order_MainActivity.class,
                "/order/Order_MainActivity",
                "order")
        );
        return pathMap;
    }
}

```

* **路由跳转使用**

```java

 public void startOrder(View view) {
//        Intent intent = new Intent(this, Order_MainActivity.class);
//        intent.putExtra("name", "simon");
//        startActivity(intent);

        // 最终集成化模式，所有子模块app/order/personal通过APT生成的类文件都会打包到apk里面，不用担心找不到
        ARouterLoadGroup group = new ARouter$$Group$$order();
        Map<String, Class<? extends ARouterLoadPath>> map = group.loadGroup();
        // 通过order组名获取对应路由路径对象
        Class<? extends ARouterLoadPath> clazz = map.get("order");

        try {
            // 类加载动态加载路由路径对象
            ARouter$$Path$$order path = (ARouter$$Path$$order) clazz.newInstance();
            Map<String, RouterBean> pathMap = path.loadPath();
            // 获取目标对象封装
            RouterBean bean = pathMap.get("/order/Order_MainActivity");

            if (bean != null) {
                startActivity(new Intent(this, bean.getClazz()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

```

###  生成代码的过程

1. app 模块build.gradle加入传参

```Groovy

        // 在gradle文件中配置选项参数值（用于APT传参接收）
        // 切记：必须写在defaultConfig节点下
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [moduleName: project.getName(), packageNameForAPT: packageNameForAPT]
            }
        }
```

2. 配置apt生成的路径，config.build下定义路径变量：

```Groovy
    // 包名，用于存放APT生成的类文件
    packageNameForAPT = "com.jesen.andcomponenttalk.apt"
```

3. 在compiler 注解处理器下定义的常量Constant.java中定义路径和组名变量：

```java

// 每个子模块的模块名
    public static final String MODULE_NAME = "moduleName";

    // 包名，用于存放APT生成的类文件
    public static final String APT_PACKAGE = "packageNameForAPT";
```
4. 在注解处理器 ComARouterProcessor.java中添加需要从app moudule中build.gradle传递过来的组名和路径
```java

// 接受application module的build.gradle传递过来的参数
@SupportedOptions({Constants.MODULE_NAME, Constants.APT_PACKAGE})
public class ComARouterProcessor extends AbstractProcessor {
    // ....
}
```