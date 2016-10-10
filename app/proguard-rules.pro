# project
-keep class net.kibotu.android.recyclerviewpresenter.** { *; }
-keep interface net.kibotu.android.recyclerviewpresenter.** { *; }

-printconfiguration config.txt
-dontwarn java.nio.file.**
-keep class **$Properties

-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
## Retrolambda specific rules ##

# as per official recommendation: https://github.com/evant/gradle-retrolambda#proguard
-dontwarn java.lang.invoke.*