### 模块之间的跳转交互

* EventBus
EventBean非常多(一对一)，一对多就会混乱不堪、难以维护
* 反射
反射技术可以成功，维护成本较高且容易出现高版本@hide限制
* 隐式意图
比较麻烦，需要维护Manifest中的action
* BroadCastReceiver
需要动态注册(7.0后)，需求方发送广播
* 类加载
需要准确的全类名路径,维护成本较高且容易出现人为失误
* 维护一个JavaBean,保存路径和类名

### APT路由

* **APT**

apt是一种处理注释的工具，它对源代码文件进行检测找出其中的Annotation，根据注解自动生成代码,
如果想要自定义的注解处理器能够正常运行，必须要通过APT工具来进行处理。
也可以这样理解，只有通过声明APT工具后，程序在编译期间自定义注解解释器才能执行。

`通俗理解:根据规则，帮我们生成代码、生成类文件`


**相关属性**

|  -- 属性名 --                | -- 解释 --|
| --                 --  | --          --|
| getEnclosedElements() | 返回该元素直接包含的子元素 |
| getEnclosingElement() | 返回包含该element的父element，与上一个方法相反 |
| getKind()             | 返回element的类型，判断是哪种element |
| getModifiers()        | 获取修饰关键字,入public static final等关键字 |
| getSimpleName()       | 获取名字，不带包名 |
| getQualifiedName()    | 获取全名，如果是类的话，包含完整的包名路径 |
| getParameters()       | 获取方法的参数元素,每个元素是一个VariableElement |
| getReturnType()       | 获取方法元素的返回值 |
| getConstantValue()    | 如果属性变量被final修饰，则可以使用该方法获取它的值 |


