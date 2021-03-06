# AndComponentTalk
组件化项目总结

### **build.gradle配置总结**

* module配置

```Groovy
 plugins {
     id 'com.android.application'
 }

 // 自定义选项
 def Versions = rootProject.ext.versions
 def baseDepends = rootProject.ext.dependencies
 def AppId = rootProject.ext.appId
 def urls = rootProject.ext.host_url

 android {
     compileSdk 30

     defaultConfig {
         applicationId AppId.app
         minSdk Versions.minSdk
         targetSdk Versions.targetSdk
         versionCode Versions.versionCode
         versionName Versions.versionName

         testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
         consumerProguardFiles "consumer-rules.pro"

         // 启用分包
         multiDexEnabled true

         // 指定资源类型
         resConfigs('zh-rCN')

         //源集–设置源集的属性，更改源集的Java /录或者自山录等
         sourceSets {
             main {
                 if (isDebug) {
                     // 果是组件化模式.需要nen
                     manifest.srcFile 'src/main/AndroidManifest.xml'
                     java.srcDirs = ['src/main/java ']
                     res.srcDirs = ['src/main/res ']
                     resources.srcDirs = ['src/main/resources ']
                     aid1.srcDirs = [' src/main/aidl']
                     assets.srcDirs = ['src /main/ assets ']
                 } else {
                     //集成化模式．整个项打包
                     manifest.srcFile 'src/main/AndroidManifest.xml'
                 }
             }
         }
     }

     // 签名配置（隐形坑：必须写在buildTypes之前）
     signingConfigs {
         debug {
             storeFile file('C:/Users/X1 Carbon/.android/debug.keystore')
             storePassword "android"
             keyAlias "androiddebugkey"
             keyPassword "android"
         }
         release {
             // 签名证书文件
             storeFile file('C:/Users/X1 Carbon/as_11301131.jks')
             // 签名证书的类型
             storeType "11301131"
             // 签名证书文件的密码
             storePassword "11301131"
             // 签名证书中密钥别名
             keyAlias "11301131"
             // 签名证书中该密钥的密码
             keyPassword "11301131"
             // 是否开启V2打包
             v2SigningEnabled true
         }
     }

     buildTypes {
         // 会添加选项配置到 BuildConfig.java
         debug {
             // 对构建类型设置签名信息
             signingConfig signingConfigs.debug
             buildConfigField("String", "debug", "\"${urls.debug}\"")
         }
         release {
             minifyEnabled false
             proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
             buildConfigField("String", "debug", "\"${urls.release}\"")
             // 对构建类型设置签名信息
             signingConfig signingConfigs.release
         }
     }
     compileOptions {
         sourceCompatibility JavaVersion.VERSION_1_8
         targetCompatibility JavaVersion.VERSION_1_8
     }

     // AdbOptions 可以对 adb 操作选项添加配置
     adbOptions {
         // 配置操作超时时间，单位毫秒
         timeOutInMs = 5 * 1000_0

         // adb install 命令的选项配置
         installOptions '-r', '-s'
     }


     // 对 dx 操作的配置，接受一个 DexOptions 类型的闭包，配置由 DexOptions 提供
     dexOptions {
         // 配置执行 dx 命令是为其分配的最大堆内存
         javaMaxHeapSize "4g"
         // 配置是否预执行 dex Libraries 工程，开启后会提高增量构建速度，不过会影响 clean 构建的速度，默认 true
         preDexLibraries = false
         // 配置是否开启 jumbo 模式，代码方法是超过 65535 需要强制开启才能构建成功
         jumboMode true
         // 配置 Gradle 运行 dx 命令时使用的线程数量
         threadCount 8
         // 配置multidex参数
         additionalParameters = [
                 '--multi-dex', // 多dex分包
                 '--set-max-idx-number=50000', // 每个包内方法数上限
                 // '--main-dex-list=' + '/multidex-config.txt', // 打包到主classes.dex的文件列表
                 '--minimal-main-dex'
         ]
     }

     // 执行 gradle lint 命令即可运行 lint 检查，默认生成的报告在 outputs/lint-results.html 中
     lintOptions {
         // 遇到 lint 检查错误会终止构建，一般设置为 false
         abortOnError false
         // 将警告当作错误来处理（老版本：warningAsErros）
         warningsAsErrors false
         // 检查新 API
         check 'NewApi'

     }
 }

 dependencies {
     implementation project(path: ':util_library')

     // 简化公共依赖
     /*implementation baseDepends.appcompat
     implementation baseDepends.material
     implementation baseDepends.constraintlayout
     testImplementation baseDepends.junit
     androidTestImplementation baseDepends.extjunit
     androidTestImplementation baseDepends.espresso*/

     // 进一步简化，遍历baseDepends集合
     baseDepends.each { k, v -> implementation v }

 }
```

* 自定义公共参数config.gradle

```Groovy
 ext {

     isDebug = true

     versions = [
             minSdk     : 21,
             targetSdk  : 30,
             versionCode: 1,
             versionName: "1.0",
     ]

     appId = [
             applicationId: "com.jesen.andcomponenttalk",
             util_library : "com.android.library",
     ]

     host_url = [
             "release": "www.baidu.com",
             "debug"  : "www.baidu.com",
     ]

     dependencies = [
             'appcompat'       : 'androidx.appcompat:appcompat:1.2.0',
             'material'        : 'com.google.android.material:material:1.3.0',
             'constraintlayout': 'androidx.constraintlayout:constraintlayout:2.0.4',
             'junit'           : 'junit:junit:4.+',
             'extjunit'        : 'androidx.test.ext:junit:1.1.2',
             'espresso'        : 'androidx.test.espresso:espresso-core:3.3.0',
     ]
 }
```

* 在工程gradle引入config.gradle

```Groovy
 apply from: "config.gradle"
```