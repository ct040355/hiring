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
-renamesourcefileattribute SourceFile
-optimizationpasses 5
-printmapping mapping.txt
-printmapping build/outputs/mapping/release/mapping.txt
-verbose
-android
#-dontoptimize
#-dontshrink
-dontusemixedcaseclassnames
#-keepparameternames
#-dontpreverify
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-repackageclasses 'ax.bx.cx'
-flattenpackagehierarchy 'ax.bb.dd'
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-overloadaggressively
#-adaptresourcefilenames **.properties,**.gif,**.jpg,**.png,**.xml,**.xsd,**.wsdl
-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
-dontwarn android.arch.util.paging.CountedDataSource
-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}
-keep @kotlinx.android.parcel.Parcelize public class *
# Gson specific classes
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }

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

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type

-dontwarn androidx.databinding.**
-keep class androidx.databinding.** { *; }
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
}
-keep class com.google.common.* {*;}
-keep class com.google.common.** {*;}
-keep class com.google.common.base.CharMatcher { *; }
-keep class java.util.concurrent.** { *; }
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
# ACRA loads Plugins using reflection
-keep class * implements org.acra.plugins.Plugin {*;}
# ACRA uses enum fields in json
-keep class org.acra.* {*;}
-keep class org.acra.** {*;}

# autodsl accesses constructors using reflection
-keepclassmembers class * implements org.acra.config.Configuration { <init>(...); }

# ACRA creates a proxy for this interface
-keep interface org.acra.ErrorReporter
-dontwarn android.support.**

-dontwarn com.faendir.kotlin.autodsl.DslInspect
-dontwarn com.faendir.kotlin.autodsl.DslMandatory
-dontwarn com.google.auto.service.AutoService
-keep class com.crashlytics.** { *; }
-keep class java.util.concurrent.** { *; }
-keep class com.google.common.base.CharMatcher { *; }
-dontwarn com.crashlytics.**
-keep class com.mbridge.** {*; }
-keep interface com.mbridge.** {*; }
-keep class android.support.v4.** { *; }
-dontwarn com.mbridge.**
-keep class **.R$* { public static final int mbridge*; }
-keep public class com.mbridge.* extends androidx.** { *; }
-keep public class androidx.viewpager.widget.PagerAdapter{ *; }
-keep public class androidx.viewpager.widget.ViewPager.OnPageChangeListener{ *; }
-keep interface androidx.annotation.IntDef{ *; }
-keep interface androidx.annotation.Nullable{ *; }
-keep interface androidx.annotation.CheckResult{ *; }
-keep interface androidx.annotation.NonNull{ *; }
-keep public class androidx.fragment.app.Fragment{ *; }
-keep public class androidx.core.content.FileProvider{ *; }
-keep public class androidx.core.app.NotificationCompat{ *; }
-keep public class androidx.appcompat.widget.AppCompatImageView { *; }
-keep public class androidx.recyclerview.*{ *; }
-dontwarn android.content.pm.PackageManager$ResolveInfoFlags
-dontwarn com.bytedance.component.sdk.annotation.AnyThread
-dontwarn com.bytedance.component.sdk.annotation.ColorInt
-dontwarn com.bytedance.component.sdk.annotation.NonNull
-dontwarn com.bytedance.component.sdk.annotation.Nullable
-dontwarn com.bytedance.component.sdk.annotation.RequiresApi
-dontwarn com.bytedance.component.sdk.annotation.UiThread
-dontwarn com.bytedance.sdk.openadsdk.core.model.NetExtParams$RenderType
-dontwarn com.bytedance.sdk.openadsdk.core.settings.SdkSettingsHelper$FETCH_REQUEST_SOURCE
-dontwarn java.lang.reflect.AnnotatedType


# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.
-dontobfuscate
-optimizationpasses 5

#From here Apache Commons
-keep class org.apache.http.**
-keep interface org.apache.http.**

-dontwarn org.apache.commons.**

#From here Apache mira
-dontwarn javax.security.sasl.*
-dontwarn org.ietf.jgss.*
-dontwarn org.apache.mina.core.session.DefaultIoSessionDataStructureFactory$DefaultIoSessionAttributeMap #Java 8 not implememnted
-dontwarn org.apache.mina.util.ExpiringMap #Java 8 not implememnted
-keepclassmembers class * implements org.apache.mina.core.service.IoProcessor {
    public <init>(java.util.concurrent.ExecutorService);
    public <init>(java.util.concurrent.Executor);
    public <init>();
}

#From here jcifs
-dontwarn javax.servlet.**
-dontwarn jcifs.http.NetworkExplorer

-keep,allowoptimization,allowobfuscation class eu.masconsult.android_ntlm.* {*;}

#From here org.codehaus

-dontwarn org.codehaus.**

-keep class org.w3c.dom.bootstrap.** { *; }
-keep class org.joda.time.** { *; }

#From here Apache regexp
-dontwarn org.apache.regexp.REDemo
-dontwarn org.apache.regexp.REDemo$1

#From here Apache ftpsever
-dontwarn org.apache.ftpserver.**


#From here MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }

-dontwarn io.realm.**
#From here retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

#From here Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#From here AboutLibraries
-keepclasseswithmembers class **.R$* {
    public static final int define_*;
}

#From here CloudRail
-keep class com.cloudrail.** { *; }
-dontwarn roku.audio.**
#From here BouncyCastle
-keep class org.bouncycastle.crypto.* {*;}
-keep class org.bouncycastle.crypto.agreement.** {*;}
-keep class org.bouncycastle.crypto.digests.* {*;}
-keep class org.bouncycastle.crypto.ec.* {*;}
-keep class org.bouncycastle.crypto.encodings.* {*;}
-keep class org.bouncycastle.crypto.engines.* {*;}
-keep class org.bouncycastle.crypto.macs.* {*;}
-keep class org.bouncycastle.crypto.modes.* {*;}
-keep class org.bouncycastle.crypto.paddings.* {*;}
-keep class org.bouncycastle.crypto.params.* {*;}
-keep class org.bouncycastle.crypto.prng.* {*;}
-keep class org.bouncycastle.crypto.signers.* {*;}

-keep class org.bouncycastle.jcajce.provider.asymmetric.* {*;}
-keep class org.bouncycastle.jcajce.provider.asymmetric.util.* {*;}
-keep class org.bouncycastle.jcajce.provider.asymmetric.dh.* {*;}
-keep class org.bouncycastle.jcajce.provider.asymmetric.ec.* {*;}
-keep class org.bouncycastle.jcajce.provider.asymmetric.rsa.* {*;}

-keep class org.bouncycastle.jcajce.provider.digest.** {*;}
-keep class org.bouncycastle.jcajce.provider.keystore.** {*;}
-keep class org.bouncycastle.jcajce.provider.symmetric.** {*;}
-keep class org.bouncycastle.jcajce.spec.* {*;}
-keep class org.bouncycastle.jce.** {*;}
-keep class com.esasy.file_explorer.base.util.** {*;}
-keep class com.esasy.file_explorer.base.util.*
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
}
-keep class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
}
-keep public class * extends androidx.lifecycle.ViewModel {*;}
## Android architecture components: Lifecycle
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

#EventBus
-keep class org.greenrobot.eventbus.** {*;}
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe *;
}

-dontwarn javax.naming.**

#From here sshj. We are not using GSSAPI to connect to SSH
-dontwarn net.schmizz.sshj.userauth.method.AuthGssApiWithMic
#Warning was at SSHClient.authGssApiWithMic, referencing javax.security.auth.login.LoginContext.
#But we are not using it too
-dontwarn net.schmizz.sshj.SSHClient



#From here tests classes
#Ignore test classes see tests-proguard.cfg
-dontwarn android.test.**
-dontwarn org.junit.**



-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.** { *; }
-keep public class com.google.android.gms.** { public protected *; }
-keep class com.google.firebase.** { *; }
-keep class com.phonebooster.data.clean.model.dto.** { *; }
-keep class com.phonebooster.data.clean.model.dto.*
-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep public class com.google.android.gms.** { public protected *; }
-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# For Google Play Services
-keep public class com.google.android.gms.ads.**{
   public *;
}

# For old ads classes
-keep public class com.google.ads.**{
   public *;
}

# For mediation

# Other required classes for Google Play Services
# Read more at http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
   protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
   public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
   @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
   public static final ** CREATOR;
}

-keep class com.flurry.*{ *; }
-dontwarn com.flurry.**
-keepattributes *Annotation*,EnclosingMethod
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keep class com.bmik.android.sdk.model.* { *; }
-keep class com.bmik.android.sdk.model.** { *; }
-keep class com.android.vending.billing.**
-keep class com.android.vending.** { *; }
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.ads.* { *; }
-keep class com.google.android.gms.ads.MobileAdsInitProvider { *; }
-keep class com.google.android.gms.ads.AdActivity { *; }
-keepclassmembers class * extends android.app.AppComponentFactory {
    <init>(...);
}

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.begamob.global.remote.roku.ytextractor.** { *; }
-keep class com.naveed.ytextractor.** { *; }
-keepattributes Signature,InnerClasses,Exceptions,Annotation
-keep public class com.applovin.sdk.AppLovinSdk{ *; }
-keep public class com.applovin.sdk.AppLovin* { public protected *; }
-keep public class com.applovin.nativeAds.AppLovin* { public protected *; }
-keep public class com.applovin.adview.* { public protected *; }
-keep public class com.applovin.mediation.* { public protected *; }
-keep public class com.applovin.mediation.ads.* { public protected *; }
-keep public class com.applovin.impl.*.AppLovin { public protected *; }
-keep public class com.applovin.impl.**.*Impl { public protected *; }
-keepclassmembers class com.applovin.sdk.AppLovinSdkSettings { private java.util.Map localSettings; }
-keep class com.applovin.mediation.adapters.** { *; }
-keep class com.applovin.mediation.adapter.**{ *; }

# Keep filenames and line numbers for stack traces
-keepattributes SourceFile,LineNumberTable
# Keep JavascriptInterface for WebView bridge
-keepattributes JavascriptInterface
# Sometimes keepattributes is not enough to keep annotations
-keep class android.webkit.JavascriptInterface {
   *;
}
# Keep all classes in Unity Ads package
-keep class com.unity3d.ads.** {
   *;
}
# Keep all classes in Unity Services package
-keep class com.unity3d.services.** {
   *;
}
-dontwarn com.google.ar.core.**
-dontwarn com.unity3d.services.**
-dontwarn com.ironsource.adapters.unityads.**
-dontwarn com.naveed.ytextractor.**
-dontwarn com.thntech.remoteall.ytextractor.**

-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class com.adjust.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}
-keep public class com.android.installreferrer.** { *; }

-keep class org.parceler.Parceler$$Parcels
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keep class org.gradle.api.plugins { *; }
-keep class com.googlecode.mp4parser.authoring.tracks.mjpeg { *; }

-keepnames class * extends org.parceler.NonParcelRepository$ConverterParcelable {
    public static final ** CREATOR;
}
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
  public <init>(...);
}
-keep class * implements com.coremedia.iso.boxes.Box {* ; }

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-dontwarn com.coremedia.iso.boxes.*
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.ttml.*
-dontwarn android.support.**
-dontwarn javax.lang.**
-dontwarn java.lang.**
-dontwarn com.artifex.**
-dontwarn javax.annotation.**
-dontwarn javax.tools.**
-dontwarn com.squareup.**
-dontwarn io.github.**
-dontwarn com.github.**
-dontwarn com.theartofdev.edmodo.**
-dontwarn java.nio.**
-dontwarn org.codehaus.**
-dontwarn cn.pedant.**
-dontwarn com.google.gson.**
-dontwarn android.security.**
-dontwarn android.net.**
-dontwarn android.content.**
-dontwarn android.app.**
-dontwarn com.startapp.android.**
-dontwarn com.google.android.gms.**
-dontwarn org.apache.**
-dontwarn org.ietf.**
-dontwarn org.w3c.**
-dontwarn com.firebase.**
-dontwarn com.fasterxml.jackson.**
-dontwarn com.google.android.**
-dontwarn com.flurry.android.**
-dontwarn com.inmobi.**
-dontwarn com.facebook.ads.**
-dontwarn com.flurry.android.ads.FurryAdNativeListener.**
-dontwarn android.webkit.**
-dontwarn sun.misc.**
-dontwarn com.jirbo.adcolony.**
-dontwarn com.adcolony.**
-dontwarn com.vungle.**
-dontwarn me.everything.providers.android.browser.**
-dontwarn com.flurry.**
-dontwarn java.awt.**,java.beans.**
-dontwarn okhttp3.internal.platform.*
-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.**
-dontwarn com.viewpagerindicator.**
-dontwarn com.handmark.**
-dontwarn io.card.**
-dontwarn a.c.**
-dontwarn org.gradle.api.plugins.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.wxiwei.office.**
-dontwarn com.microsoft.schemas.office.**
-dontwarn com.wxiwei.office.**
-dontwarn org.etsi.uri.**
-dontwarn org.openxmlformats.schemas.**
-dontwarn schemasMicrosoftComOfficeOffice.**
-dontwarn org.w3.x2000.**
-dontwarn schemasMicrosoftComOfficeExcel.**
-dontwarn schemasMicrosoftComVml.**
-dontwarn com.connectsdk.**
-dontwarn com.getastudio.getacore.**
-dontwarn at.huber.youtubeExtractor.**
-dontwarn com.naveed.ytextractor.**
-dontwarn com.evgenii.jsevaluator.**


-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception

-keep public class * extends android.app.Application
-keep class javax.lang.** { *; }
-keep class com.connectsdk.** { *; }
-keep class com.getastudio.getacore.** { *; }
-keep class at.huber.youtubeExtractor.** { *; }
-keep class com.naveed.ytextractor.** { *; }
-keep class com.evgenii.jsevaluator.** { *; }
-keep class com.microsoft.schemas.office.** { *; }
-keep class org.etsi.uri.** { *; }
-keep class org.openxmlformats.schemas.** { *; }
-keep class schemasMicrosoftComOfficeOffice.** { *; }
-keep class schemasMicrosoftComOfficeExcel.** { *; }
-keep class schemasMicrosoftComVml.** { *; }
-keep class com.wxiwei.office.** { *; }
-keep class org.w3.x2000.** { *; }
-keep class java.lang.** { *; }
-keep class javax.annotation.** { *; }
-keep class javax.tools.** { *; }
-keep class com.squareup.** { *; }
-keep class io.github.** { *; }
-keep class com.github.** { *; }
-keep class cn.pedant.** { *; }
-keep class android.support.** { *; }
-keep class com.theartofdev.edmodo.** { *; }
-keep class java.nio.** { *; }
-keep class org.codehaus.** { *; }
-keep class com.google.gson.** { *; }
-keep class android.security.** { *; }
-keep class android.net.** { *; }
-keep class android.content.** { *; }
-keep class com.google.android.** { *; }
-keep class com.startapp.android.** { *; }
-keep interface android.support.** { *; }
-keep interface android.app.** { *; }
-keep class org.apache.** { *; }
-keep class org.ietf.** { *; }
-keep class org.w3c.** { *; }
-keep class com.firebase.** { *; }
-keep interface com.fasterxml.jackson.** {*; }
-keep class com.fasterxml.jackson.** { *; }
-keep class com.flurry.android.** {*;}
-keep class com.inmobi.** {*;}
-keep class com.facebook.ads.** {*;}
-keep class com.flurry.android.ads.FurryAdNativeListener.** { *; }
-keep class android.webkit.** {*;}
-keep class sun.misc.** {*;}
-keep class com.jirbo.adcolony.**{*;}
-keep class com.adcolony.**{*;}
-keep class com.vungle.**{*;}
-dontwarn com.google.ar.core.**
-dontwarn com.ironsource.mediationsdk.**
-keep class com.google.ar.core.* {*;}
-keep class com.ironsource.mediationsdk.* {*;}
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class com.flurry.** { *; }
-keep class com.mediaplayer.videoplayer.rest.model.** {*;}
-keep public class android.util.FloatMath
-keep class org.openudid.** { *; }
-keep class com.artifex.** { *; }
-keep class com.shockwave.pdfium.**{*;}
-keepattributes InnerClasses

 #sdk
-keep class a.** { *; }
-keep class b.** { *; }
-keep class com.unity3d.** { *;}
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
    public static int e(...);
    public static int wtf(...);
}

-keep class org.** { *; }
-keep class com.bea.xml.stream.**{*;}
-keep class org.apache.xmlbeans.** { *; }
-keep class com.microsoft.** { *; }
-keep class org.openxmlformats.**{*;}
-keep class com.apache.poi.** { *; }
-keep class schemaorg_apache_xmlbeans.** {*;}

# js-evaluator-for-android
-keep class com.android.vending.billing.**
-keep class com.android.vending.** { *; }
-keep class com.amazon.whisperplay.** { *; }
-keep class com.amazon.whisperplay.* { *; }
-keep class com.amazon.* { *; }
-keep class com.amazon.** { *; }
-renamesourcefileattribute SourceFile
-optimizationpasses 5
-printmapping mapping.txt
-printmapping build/outputs/mapping/release/mapping.txt
-verbose
-android
#-dontoptimize
#-dontshrink
-dontusemixedcaseclassnames
#-keepparameternames
#-dontpreverify
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-repackageclasses 'ax.bx.cx'
-flattenpackagehierarchy 'ax.bb.dd'
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-overloadaggressively
#-adaptresourcefilenames **.properties,**.gif,**.jpg,**.png,**.xml,**.xsd,**.wsdl
-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
-dontwarn android.arch.util.paging.CountedDataSource
-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}
-keep @kotlinx.android.parcel.Parcelize public class *
# Gson specific classes
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }

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

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type

-dontwarn androidx.databinding.**
-keep class androidx.databinding.** { *; }
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
}
-keep class com.google.common.* {*;}
-keep class com.google.common.** {*;}
-keep class com.google.common.base.CharMatcher { *; }
-keep class java.util.concurrent.** { *; }
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
# ACRA loads Plugins using reflection
-keep class * implements org.acra.plugins.Plugin {*;}
# ACRA uses enum fields in json
-keep class org.acra.* {*;}
-keep class org.acra.** {*;}

# autodsl accesses constructors using reflection
-keepclassmembers class * implements org.acra.config.Configuration { <init>(...); }

# ACRA creates a proxy for this interface
-keep interface org.acra.ErrorReporter
-dontwarn android.support.**

-keep class com.begamob.global.remote.roku.ui.screen.service.data.** { *; }
-keep class com.begamob.global.remote.roku.ui.screen.service.data.* { *; }

-dontwarn com.faendir.kotlin.autodsl.DslInspect
-dontwarn com.faendir.kotlin.autodsl.DslMandatory
-dontwarn com.google.auto.service.AutoService
-keep class com.crashlytics.** { *; }
-keep class java.util.concurrent.** { *; }
-keep class com.google.common.base.CharMatcher { *; }
-dontwarn com.crashlytics.**
-keep public class androidx.recyclerview.*{ *; }
-keep interface com.mbridge.** {*; }
-keep class android.support.v4.** { *; }
-dontwarn com.mbridge.**
-keep class **.R$* { public static final int mbridge*; }
-keep public class com.mbridge.* extends androidx.** { *; }
-keep public class androidx.viewpager.widget.PagerAdapter{ *; }
-keep public class androidx.viewpager.widget.ViewPager.OnPageChangeListener{ *; }
-keep interface androidx.annotation.IntDef{ *; }
-keep interface androidx.annotation.Nullable{ *; }
-keep interface androidx.annotation.CheckResult{ *; }
-keep interface androidx.annotation.NonNull{ *; }
-keep public class androidx.fragment.app.Fragment{ *; }
-keep public class androidx.core.content.FileProvider{ *; }
-keep public class androidx.core.app.NotificationCompat{ *; }
-keep public class androidx.appcompat.widget.AppCompatImageView { *; }
-keep public class androidx.recyclerview.*{ *; }
-keep public class com.begamob.global.remote.roku.model.*{ *; }
-dontwarn android.content.pm.PackageManager$ResolveInfoFlags
-dontwarn com.bytedance.component.sdk.annotation.AnyThread
-dontwarn com.bytedance.component.sdk.annotation.ColorInt
-dontwarn com.bytedance.component.sdk.annotation.NonNull
-dontwarn com.bytedance.component.sdk.annotation.Nullable
-dontwarn com.bytedance.component.sdk.annotation.Nullable
-dontwarn com.bytedance.component.sdk.annotation.RequiresApi
-dontwarn com.bytedance.component.sdk.annotation.UiThread
-dontwarn com.bytedance.sdk.openadsdk.core.model.NetExtParams$RenderType
-dontwarn com.bytedance.sdk.openadsdk.core.settings.SdkSettingsHelper$FETCH_REQUEST_SOURCE
-dontwarn java.lang.reflect.AnnotatedType
-keepnames class kotlinx.coroutines.** {*;}
-keepnames class kotlinx.coroutines.* {*;}
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn com.facebook.ads.internal.**
-keeppackagenames com.meta.*
-keep public class com.facebook.ads.** {*;}
-keep public class com.facebook.ads.**
{ public protected *; }
-keep public class com.android.installreferrer.** { *; }

-keep class org.parceler.Parceler$$Parcels
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keep class org.gradle.api.plugins { *; }
-keep class com.googlecode.mp4parser.authoring.tracks.mjpeg { *; }

-keepnames class * extends org.parceler.NonParcelRepository$ConverterParcelable {
    public static final ** CREATOR;
}
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
  public <init>(...);
}
-keep class * implements com.coremedia.iso.boxes.Box {* ; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
 **[] $VALUES;
 public *;
 }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
**[] $VALUES;
public *;
}


-keep class com.kma.movies.data.sources.local.model.** { *; }
-keep class com.kma.movies.data.sources.local.model.* { *; }
-keep class com.kma.movies.data.** { *; }
-keep class com.kma.movies.data.* { *; }