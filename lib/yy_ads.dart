
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:yy_ads/ChannelMethod.dart';

class YyAds {

  static const String SPLASH_VIEW = "yySplashView";
  static const String BANNER_VIEW = "yyBannerAdView";
  static const String NATIVE_STREAM_VIEW = "yyStreamAdView";
  static const String DRAW_STREAM_VIEW = "yyDrawStreamView";

  static const MethodChannel _channel =
      const MethodChannel(ChannelName.METHOD_CHANNEL);
  static const EventChannel _eventChannel =
    const EventChannel(ChannelName.EVENT_CHANNEL);

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 开屏广告
  static Future<Stream> loadSplash([dynamic argument]) async {
    Stream _stream;
    _channel.invokeListMethod(ChannelName.LOAD_SPLASH,argument);
    _stream = _eventChannel.receiveBroadcastStream();
    return _stream;
  }

  /// 加载 Banner
   static Future<Stream> loadBanner([dynamic argument]) async {
     Stream _stream;
    _channel.invokeMethod(ChannelName.LOAD_BANNER,argument);
     _stream = _eventChannel.receiveBroadcastStream();
    return _stream;
  }

  /// 插屏广告
  static Future<Stream> loadInterstitial([dynamic argument]) async{
    Stream _stream;
    _channel.invokeMethod(ChannelName.LOAD_INTERSTITIAL,argument);
    _stream = _eventChannel.receiveBroadcastStream();
    return _stream;
  }

  /// 全屏视频广告
  static Future<Stream> loadFullscreen([dynamic argument]) async{
    Stream _stream;
    _channel.invokeMethod(ChannelName.LOAD_FULLSCREEN_VIDEO,argument);
    _stream = _eventChannel.receiveBroadcastStream();
    return _stream;
  }

  /// 激励视频广告
  static Future<Stream> loadReward([dynamic argument]) async{
    Stream _stream;
    _channel.invokeMethod(ChannelName.LOAD_REWARD_VIDEO,argument);
    _stream = _eventChannel.receiveBroadcastStream();
    return _stream;
  }

  /// 信息流广告
  static Future<Stream> loadNativeStream([dynamic argument]) async{
    Stream _stream;
    _channel.invokeMethod(ChannelName.LOAD_NATIVE_STREAM,argument);
    _stream = _eventChannel.receiveBroadcastStream();
    return _stream;
  }

  /// Draw信息流广告
  static Future<Stream> loadDrawStream([dynamic argument]) async{
    Stream _stream;
    _channel.invokeMethod(ChannelName.LOAD_DRAW_NATIVE_STREAM,argument);
    _stream = _eventChannel.receiveBroadcastStream();
    return _stream;
  }


// Native端发送正常数据回调方法，每一次发送都会调用
  static void _onToDart(message) {
    print('正常接收：$message');
  }
  // Native出错时回调方法
  static void _onToDartError(error) {
    print('错误接收：$error');
  }
  // 当native发送数据完成时调用的方法
  static void _onDone() {
    print("消息传递完毕");
  }
}

class AdConfig{
  static const String setPlacementId = "placementId";
  static const String setTimeout = "timeout";
  static const String setWidth = "width";
  static const String setHeight = "height";
  static const String isCarousel = "isCarousel";
  static const String setOrientation = "orientation";
  static const String setUserId = "userId";
  static const String setCustomData = "customData";
  static const String setScenes = "scenes";
  static const String setScenesMsg = "scenesMsg";

  static const int VERTICAl = 1;
  static const int HORIZENTAL = 2;
}

enum RitScenes{
  CUSTOMIZE_SCENES,
  HOME_OPEN_BONUS,
  HOME_SVIP_BONUS,
  HOME_GET_PROPS,
  HOME_TRY_PROPS,
  HOME_GET_BONUS,
  HOME_GIFT_BONUS,
  GAME_START_BONUS,
  GAME_REDUCE_WAITING,
  GAME_MORE_KLLKRTUNITIES,
  GAME_FINISH_REWARDS,
  GAME_GIFT_BONUS
}
extension RitScenesExt on RitScenes{
  String get value => [
    "CUSTOMIZE_SCENES",
    "HOME_OPEN_BONUS",
    "HOME_SVIP_BONUS",
    "HOME_GET_PROPS",
    "HOME_TRY_PROPS",
    "HOME_GET_BONUS",
    "HOME_GIFT_BONUS",
    "GAME_START_BONUS",
    "GAME_REDUCE_WAITING",
    "GAME_MORE_KLLKRTUNITIES",
    "GAME_FINISH_REWARDS",
    "GAME_GIFT_BONUS"
  ][this.index];
}
