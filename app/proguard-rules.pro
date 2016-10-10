# project
-keep class net.kibotu.android.recyclerviewpresenter.** { *; }
-keep interface net.kibotu.android.recyclerviewpresenter.** { *; }

# project
-keep class net.kibotu.android.recyclerviewpresenter.app** { *; }
-keep interface net.kibotu.android.recyclerviewpresenter.app** { *; }
-keepattributes Exceptions, InnerClasses

-printconfiguration config.txt
-dontwarn java.nio.file.**
-keep class **$Properties

-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
## Retrolambda specific rules ##

# as per official recommendation: https://github.com/evant/gradle-retrolambda#proguard
-dontwarn java.lang.invoke.*

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Butterknife
-keep public class * implements butterknife.Unbinder { public <init>(...); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }