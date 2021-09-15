import 'dart:collection';
import 'dart:math';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:yy_ads/yy_ads.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() {
    return _MyAppState();
  }
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(title: 'Ad Test', home: new HomePage());
  }
}

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;
    var height = MediaQuery.of(context).size.height;

    final ButtonStyle style =
        ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 20));
    return new Scaffold(
      /* appBar: new AppBar(
        title: new Text('Ad Test'),
      ),*/
      body: new Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              onPressed: () {
                LoadAd().loadSplash();
              },
              child: const Text("加载开屏广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed: () {
                LoadAd().loadBanner(width);
              },
              child: const Text("加载Banner广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed: () {
                LoadAd().loadInterstitial();
              },
              child: const Text("加载插屏广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed: () {
                LoadAd().loadFullscreenVideo();
              },
              child: const Text("加载全屏视频广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed: () {
                LoadAd().loadReward();
              },
              child: const Text("加载激励视频广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed: () {
                LoadAd().loadStream(width);
              },
              child: const Text("加载信息流广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed: () {
                LoadAd().loadDraw();
              },
              child: const Text("加载Draw信息流广告"),
              style: style,
            ),
          ],
        ),
      ),
    );
  }
}

class LoadAd {
  void loadSplash() async {
    Stream stream;
    try {
      stream = await YyAds.loadSplash(<String, dynamic>{
        AdConfig.setPlacementId: "您的广告位id",
        AdConfig.setTimeout: 3500
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }
  }

  void loadBanner(double width) async {
    Stream stream;
    try {
      stream = await YyAds.loadBanner(<String, dynamic>{
        AdConfig.setPlacementId: "您的广告位id",
        AdConfig.isCarousel: false,
        AdConfig.setWidth: width.toInt(),
        AdConfig.setHeight: width * 58 ~/ 375,
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }
  }

  void loadInterstitial() async {
    Stream stream;
    try {
      stream = await YyAds.loadInterstitial(<String, dynamic>{
        AdConfig.setPlacementId: "您的广告位id",
        AdConfig.setOrientation: AdConfig.VERTICAl,
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }
  }

  void loadFullscreenVideo() async {
    Stream stream;
    try {
      stream = await YyAds.loadFullscreen(<String, dynamic>{
        AdConfig.setPlacementId: "您的广告位id",
        AdConfig.setOrientation: AdConfig.VERTICAl,
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }
  }

  void loadReward() async {
    Stream stream;
    try {
      stream = await YyAds.loadReward(<String, dynamic>{
        AdConfig.setPlacementId: "您的广告位id",
        AdConfig.setUserId: "321345",
        AdConfig.setCustomData: "xxxxx",
        AdConfig.setOrientation: 1,
        AdConfig.setScenes: RitScenes.CUSTOMIZE_SCENES.value,
        AdConfig.setScenesMsg: "customize_scenes"
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }
  }

  void loadStream(double width) async {
    Stream stream;
    try {
      stream = await YyAds.loadNativeStream(<String, dynamic>{
        AdConfig.setPlacementId: "您的广告位id",
        AdConfig.setWidth: width.toInt(),
        AdConfig.setHeight: 0,
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }
  }

  void loadDraw() async {
    Stream stream;
    try {
      stream = await YyAds.loadDrawStream(<String, dynamic>{
        AdConfig.setPlacementId: "您的广告位id",
        AdConfig.setWidth: 500,
        AdConfig.setHeight: 500,
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }
  }

  // Native端发送正常数据回调方法，每一次发送都会调用
  static void _onData(message) {
    var verify = "";
    if (message is HashMap<dynamic, dynamic>) {
      verify = message["verify"];
      print(verify);
    }
    print('正常接收：$message');
  }

  // Native出错时回调方法
  static void _onErrorData(error) {
    print('错误接收：$error');
  }

  // 当native发送数据完成时调用的方法
  static void _onDone() {
    print("消息传递完毕");
  }
}
