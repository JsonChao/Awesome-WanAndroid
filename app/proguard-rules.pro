# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 混淆语法的作用主要是定义出不需要混淆的源代码，那么编译时会自动将未定义的部分全都混淆。
# 以下是全面的混淆语法规则。

# -include {filename}    从给定的文件中读取配置参数
# -basedirectory {directoryname}    指定基础目录为以后相对的档案名称
# -injars {class_path}    指定要处理的应用程序jar,war,ear和目录
# -outjars {class_path}    指定处理完后要输出的jar,war,ear和目录的名称
# -libraryjars {classpath}    指定要处理的应用程序jar,war,ear和目录所需要的程序库文件
# -dontskipnonpubliclibraryclasses    指定不去忽略非公共的库类。
# -dontskipnonpubliclibraryclassmembers    指定不去忽略包可见的库类的成员。
#
# 保留选项
# -keep {Modifier} {class_specification}    保护指定的类文件和类的成员
# -keepclassmembers {modifier} {class_specification}    保护指定类的成员，如果此类受到保护他们会保护的更好
# -keepclasseswithmembers {class_specification}    保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在。
# -keepnames {class_specification}    保护指定的类和类的成员的名称（如果他们不会压缩步骤中删除）
# -keepclassmembernames {class_specification}    保护指定的类的成员的名称（如果他们不会压缩步骤中删除）
# -keepclasseswithmembernames {class_specification}    保护指定的类和类的成员的名称，如果所有指定的类成员出席（在压缩步骤之后）
# -printseeds {filename}    列出类和类的成员-keep选项的清单，标准输出到给定的文件
#
# 压缩
# -dontshrink    不压缩输入的类文件
# -printusage {filename}
# -whyareyoukeeping {class_specification}
#
# 优化
# -dontoptimize    不优化输入的类文件
# -assumenosideeffects {class_specification}    优化时假设指定的方法，没有任何副作用
# -allowaccessmodification    优化时允许访问并修改有修饰符的类和类的成员
#
# 混淆
# -dontobfuscate    不混淆输入的类文件
# -printmapping {filename}
# -applymapping {filename}    重用映射增加混淆
# -obfuscationdictionary {filename}    使用给定文件中的关键字作为要混淆方法的名称
# -overloadaggressively    混淆时应用侵入式重载
# -useuniqueclassmembernames    确定统一的混淆类的成员名称来增加混淆
# -flattenpackagehierarchy {package_name}    重新包装所有重命名的包并放在给定的单一包中
# -repackageclass {package_name}    重新包装所有重命名的类文件中放在给定的单一包中
# -dontusemixedcaseclassnames    混淆时不会产生形形色色的类名
# -keepattributes {attribute_name,...}    保护给定的可选属性，例如LineNumberTable, LocalVariableTable, SourceFile, Deprecated, Synthetic, Signature, and
#
# InnerClasses.
# -renamesourcefileattribute {string}    设置源文件中给定的字符串常量

# 混淆规则

# 输出 ProGuard 的最终配置
-printconfiguration configuration.txt

#############################################
#
# 对于一些基本指令的添加
#
#############################################
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，
# Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆时采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*


#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# AndroidMainfest中的类不混淆，所以四大组件和Application的子类和
# Framework层下所有的类默认不会进行混淆，并且自定义的View默认也不会被混淆。
# 保留我们使用的四大组件，自定义的Application、View等等这些类不被混淆
# 因为这些子类都有可能被外部调用
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Appliction
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承support包中的所有类
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆，jni方法不可混淆，因为这个方法需要和native方法保持一致。
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 使用enum类型时需要注意避免以下两个方法混淆，
# 因为enum类的特殊性，以下两个方法会被反射调用。
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
#-keep public class * extends android.view.View{
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}

# Parcelable的子类和Creator静态成员变量不混淆，
# 否则会产生Android.os.BadParcelableException异常。
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, java.lang.String);
}

# 移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用
# 记得proguard-android.txt中一定不要加-dontoptimize才起作用
# 另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}


#############################################
#
# 项目中特殊处理部分
#
#############################################

#-----------处理反射类，反射用到的类不混淆(否则反射可能出现问题)---------------
-keep class android.view.WindowManager.LayoutParams


#-----------处理js交互，有用到WebView的JS调用也需要保证写的接口方法不混淆，因为H5需要通过匹配方法名的方式进行调用。---------------



#-----------处理实体类---------------
# 在开发的时候我们可以将所有的实体类放在一个包内，这样我们写一次混淆就行了。
# 使用Gson时需要配置Gson的解析对象及变量都不混淆。不然Gson会找不到变量。
-keep class json.chao.com.wanandroid.core.bean.** { *; }
-keep class json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData
-keep class json.chao.com.wanandroid.ui.mainpager.adapter.ArticleListAdapter


#-----------处理第三方依赖库---------

# BRVAH
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers class $ extends com.chad.library.adapter.base.BaseViewHolder {
*;
}
-keepclassmembers class * extends com.chad.library.adapter.base.BaseViewHolder { *; }

-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn androidx.**


# Bugly
-dontwarn com.tencent.bugly.**
-keep class com.tencent.bugly.** {*;}


# ButterKnife
-keep public class * implements butterknife.Unbinder {
    public <init>(**, android.view.View);
}
-keep class butterknife.*
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}


# Dagger2
-dontwarn com.google.errorprone.annotations.*


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}



# greenDAO 3
-dontwarn org.greenrobot.greendao.**
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties {*;}

# If you do not use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do not use RxJava:
#-dontwarn rx.**


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

##---------------End: proguard configuration for Gson  ----------


# OkHttp
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform


# Okio
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*


# Retrofit
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>


# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*


# RxJava
-dontwarn com.jakewharton.rxbinding2.**
-dontwarn java.util.concurrent.Flow*
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent


# banner
-keep class com.youth.banner.** {
    *;
}


# agentweb
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**


# xupdate
-keep class com.xuexiang.xupdate.entity.** { *; }





