package com.youyi.yy_ads

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import com.youyi.yy_ads.factory.AndroidViewFactory
import com.youyi.yy_ads.factory.SdkViewPipe
import com.youyi.yy_ads.manager.*

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.*
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** YyAdsPlugin */
class YyAdsPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {

  private lateinit var methodChannel : MethodChannel
  private lateinit var eventChannel: EventChannelManager
  private lateinit var appContext: Context
  private lateinit var activity: Activity
  private val bannerViewFactory = AndroidViewFactory(StandardMessageCodec.INSTANCE)
  private val streamViewFactory = AndroidViewFactory(StandardMessageCodec.INSTANCE)
  private val drawStreamViewFactory = AndroidViewFactory(StandardMessageCodec.INSTANCE)
  private val splashViewFactory = AndroidViewFactory(StandardMessageCodec.INSTANCE)

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    methodChannel = MethodChannel(flutterPluginBinding.binaryMessenger, Const.METHOD_CHANNEL)
    eventChannel = EventChannelManager.registerWith(flutterPluginBinding.binaryMessenger,  Const.EVENT_CHANNEL)
    appContext = flutterPluginBinding.applicationContext

    flutterPluginBinding.platformViewRegistry.apply {
      registerViewFactory(Const.SPLASH_VIEW_ID,splashViewFactory)
      registerViewFactory(Const.BANNER_VIEW_ID, bannerViewFactory)
      registerViewFactory(Const.STREAM_VIEW_ID,streamViewFactory)
      registerViewFactory(Const.DRAW_STREAM_VIEW_ID,drawStreamViewFactory)
    }
    methodChannel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      
      Const.ChannelMethod.LOAD_SPLASH-> {
        val splash = SplashAdForFlutter(activity)
        splashViewFactory.setViewFactoryListener(object : AndroidViewFactory.FactoryListener{
          override fun onReady(viewPipe: SdkViewPipe?) {
            splash.loadBSplash(call,eventChannel,viewPipe!!)
          }
        })
      }
      
      Const.ChannelMethod.LOAD_BANNER -> {
        val banner = BannerAdForFlutter(activity)
        if (bannerViewFactory.isCreate){
          banner.loadBanner(call,eventChannel,bannerViewFactory)
        }
      }
      
      Const.ChannelMethod.LOAD_INTERSTITIAL -> {
        val interstitial = InterstitialAdForFlutter(activity)
        interstitial.loadInterstitial(call,eventChannel)
      }
      Const.ChannelMethod.LOAD_FULLSCREEN_VIDEO -> {
        val fullVideo = FullscreenVideoForFlutter(activity)
        fullVideo.loadFullscreenVideo(call,eventChannel)
      }
      Const.ChannelMethod.LOAD_REWARD_VIDEO -> {
        val rewardVideo = RewardVideoForFlutter(activity)
        rewardVideo.loadRewardAd(call,eventChannel)
      }
      Const.ChannelMethod.LOAD_NATIVE_STREAM -> {
        val streamAd = NativeStreamForFlutter(activity)
        if (streamViewFactory.isCreate) {
          streamAd.loadNativeStream(call,eventChannel,streamViewFactory)
        }
      }
      Const.ChannelMethod.LOAD_NATIVE_DRAW_STREAM -> {
        val drawStream = DrawStreamForFlutter(activity)
        drawStreamViewFactory.setViewFactoryListener(object : AndroidViewFactory.FactoryListener{
          override fun onReady(viewPipe: SdkViewPipe?) {
            drawStream.loadDrawStream(call,eventChannel,viewPipe)
          }
        })
      }
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    methodChannel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {

  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivity() {

  }




}
