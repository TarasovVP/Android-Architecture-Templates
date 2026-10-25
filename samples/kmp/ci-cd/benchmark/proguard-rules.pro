
-dontwarn javax.lang.model.**

-dontwarn com.google.errorprone.annotations.**
-keep   class com.google.errorprone.annotations.** { *; }

-keep class androidx.benchmark.** { *; }

-keepclassmembers class androidx.benchmark.runner.AndroidBenchmarkRunner { *; }