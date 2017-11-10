# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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



#==================Cklibrary=====================
#==================Cklibrary=====================
#==================Cklibrary=====================

-keep class com.common.cklibrary.entity.** { *; }        # 保持实体类不被混淆

#==================Cklibrary=====================
#==================Cklibrary=====================
#==================Cklibrary=====================


## ----------------------------------
## ----------------------------------
##      apk安装包 公共混淆
## ----------------------------------
## ----------------------------------

#代码混淆
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验 # 不做预检验,preverify是proguard的四个步骤之一,Android不需要preverify,去掉这一步可以加快混淆速度
-verbose                # 混淆时是否记录日志
# 不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers
# 混淆时采用的算法(google推荐，一般不改变)
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 把混淆类中的方法名也混淆了
-useuniqueclassmembernames
# 优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
# 将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
# 保留行号
-keepattributes SourceFile,LineNumberTable

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keep class com.ruitukeji.scc.entity.** { *; }        # 保持实体类不被混淆


-keepclasseswithmembernames class * {              # 保持 native 方法不被混淆
     native <methods>;
 }
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
     public <init>(android.content.Context, android.util.AttributeSet);
 }
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
     public void *(android.view.View);
 }
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
     public static final android.os.Parcelable$Creator *;
 }
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# 过滤R文件的混淆：
-keep class **.R$* {*;}
#-target 错误
-keepattributes InnerClasses
#-dontoptimize
-keepattributes EnclosingMethod

#-keep class com.newrelic.** { *; }
#
#-dontwarn com.newrelic.**