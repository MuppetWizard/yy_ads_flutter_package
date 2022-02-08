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
       appBar: new AppBar(
        title: new Text('Ad Test'),
      ),
      body: new Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            ElevatedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (context){
                  return SplashPage();
                }));
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
                Navigator.push(context, MaterialPageRoute(builder: (context){
                  return DrawStreamPage();
                }));
              },
              child: const Text("加载Draw信息流广告"),
              style: style,
            ),
            Container(
              width: width,
              height: 150,
              color: Colors.blue,
              child: AndroidView(
                viewType: YyAds.BANNER_VIEW,
              ),
            ),
            Container(
              width: width,
              height: 150,
              color: Colors.blue,
              child: AndroidView(
                viewType: YyAds.NATIVE_STREAM_VIEW,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class SplashPage extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    var width =  MediaQuery.of(context).size.width;
    var height = MediaQuery.of(context).size.height;
    LoadAd().loadSplash(context);
    return Scaffold(
      // appBar: AppBar(
      //   title: Text("Splash"),
      // ),
      body: Center(
          child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Container(
                  width: width,
                  height: height,
                  color: Colors.blue,
                  child: AndroidView(
                    viewType: YyAds.SPLASH_VIEW,
                  ),
                ),
              ]
          )
      ),
    );
  }
}

class DrawStreamPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var width =  MediaQuery.of(context).size.width;
    var height = MediaQuery.of(context).size.height;
    LoadAd().loadDraw();
    return Scaffold(
      // appBar: AppBar(
      //   title: Text("Splash"),
      // ),
      body: Center(
          child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Container(
                  width: width,
                  height: height,
                  color: Colors.blue,
                  child: AndroidView(
                    viewType: YyAds.DRAW_STREAM_VIEW,
                  ),
                ),
              ]
          )
      ),
    );
  }

}


class LoadAd {
  late BuildContext _mContext;
  void loadSplash(BuildContext context) async {
    Stream stream;
    try {
      stream = await YyAds.loadSplash(<String, dynamic>{
        AdConfig.setPlacementId: "your placement id",
        AdConfig.setTimeout: 3500,
        AdConfig.setSplashClickType: AdConfig.LIMIT_CLICK_AREA,
        AdConfig.setInteractionType: AdConfig.SPLASH_SLIP
      });
      stream.listen(_onSplashData, onError: _onSplashError, onDone: _onDone);
      _mContext = context;
    } on PlatformException catch (s) {
      print(s);
    }
  }

  void loadBanner(double width) async {
    Stream stream;
    try {
      stream = await YyAds.loadBanner(<String, dynamic>{
        AdConfig.setPlacementId: "your placement id",
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
        AdConfig.setPlacementId: "your placement id",
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
        AdConfig.setPlacementId: "your placement id",
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
        AdConfig.setPlacementId: "your placement id",
        AdConfig.setUserId: "321345",
        AdConfig.setCustomData: "xxxxx",
        AdConfig.setOrientation: AdConfig.VERTICAl,
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
        AdConfig.setPlacementId: "your placement id",
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
        AdConfig.setPlacementId: "your placement id",
        AdConfig.setWidth: 500,
        AdConfig.setHeight: 0,
      });
      stream.listen(_onData, onError: _onErrorData, onDone: _onDone);
    } on PlatformException catch (s) {
      print(s);
    }

  }

  void _onSplashData(message){
    print('正常接收，$message');
    var evet = message.toString();
    if(evet == "onAdCanceled" || evet == "onError" || evet == "onTimeOut"){
      Navigator.pop(_mContext);
    }
  }

  void _onSplashError(error){
    print('错误接收：$error');
    Navigator.pop(_mContext);
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
