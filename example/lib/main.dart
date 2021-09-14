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
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void>  initPlatformState() async {
    YyAds.loadSplash(<String,dynamic>{
      'context':context
    });
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await YyAds.platformVersion ?? 'Unknown platform version';
//      YyAds.initSDK(<String,dynamic>{
//        'asdf':12
//      });
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Ad Test',
      home: new HomePage()
    );
  }
}
class HomePage extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    var width =  MediaQuery.of(context).size.width;
    var height = MediaQuery.of(context).size.height;


    final ButtonStyle style = ElevatedButton.styleFrom(textStyle: const TextStyle(fontSize: 20));
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('Ad Test'),
      ),
      body: new Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[

            ElevatedButton(
              onPressed:() {
                },
              child: const Text("加载开屏广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed:() {
                LoadAd().loadBanner(width);},
              child: const Text("加载Banner广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed:() {
                LoadAd().loadInterstitial();},
              child: const Text("加载插屏广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed:() {
                LoadAd().loadFullscreenVideo();},
              child: const Text("加载全屏视频广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed:() {
                LoadAd().loadReward();},
              child: const Text("加载激励视频广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed:() {
                LoadAd().loadStream(width);},
              child: const Text("加载信息流广告"),
              style: style,
            ),
            ElevatedButton(
              onPressed: () {
                LoadAd().loadDraw();},
              child: const Text("加载Draw信息流广告"),
              style: style,
            ),
            Container(
              width: width,
              height: height,
              child: AndroidView(
                viewType: YyAds.DRAW_STREAM_VIEW,
              ),
            ),
            AndroidView(
                viewType: YyAds.SPLASH_VIEW
            ),
           Container(
             width: width,
             height: 300,
             child: Row(
               mainAxisSize: MainAxisSize.min,
               children: [
                 Expanded(
                   child: AndroidView(
                     viewType: YyAds.NATIVE_STREAM_VIEW,
                   ),
                 ),
               ],
             ),
           )
          ],
        ),
      ),
    );
  }
}

class LoadAd{

void loadSplash() async{
  Stream stream;
  try {
    stream = await YyAds.loadSplash(<String, dynamic>{
      AdConfig.setPlacementId: "",
      AdConfig.setTimeout: 3500
    });
    stream.listen(_onToDart, onError: _onToDartError, onDone: _onDone);
  }  on PlatformException catch(s){
    print(s);
  }
}

  void loadBanner(double width) async {
    Stream stream;
    try{
      stream = await YyAds.loadBanner(<String,dynamic>{
        AdConfig.setPlacementId:"0000000040",
        AdConfig.isCarousel: false,
        AdConfig.setWidth: width.toInt(),
        AdConfig.setHeight: width * 58/375,
      });
      stream.listen(_onToDart, onError: _onToDartError, onDone: _onDone);
    } on PlatformException catch(s){
      print(s);
    }

  }

void loadInterstitial() async{
  Stream stream;
  try{
    stream = await YyAds.loadInterstitial(<String,dynamic>{
      AdConfig.setPlacementId:"0000000041",
      AdConfig.setOrientation :AdConfig.VERTICAl,
    });
    stream.listen(_onToDart, onError: _onToDartError, onDone: _onDone);
  } on PlatformException catch(s){
    print(s);
  }
}

void loadFullscreenVideo() async{
  Stream stream;
  try{
    stream = await YyAds.loadFullscreen(<String,dynamic>{
      AdConfig.setPlacementId :"0000000046",
      AdConfig.setOrientation :AdConfig.VERTICAl,
    });
    stream.listen(_onToDart, onError: _onToDartError, onDone: _onDone);
  } on PlatformException catch(s){
    print(s);
  }
}

  void loadReward() async {
    Stream stream;
    try{
      stream = await YyAds.loadReward(<String,dynamic>{
        AdConfig.setPlacementId:"0000000034",
        AdConfig.setUserId:"321345",
        AdConfig.setCustomData:"xxxxx",
        AdConfig.setOrientation:1,
        AdConfig.setScenes: RitScenes.CUSTOMIZE_SCENES.value,
        AdConfig.setScenesMsg:"customize_scenes"
      });
      stream.listen(_onToDart, onError: _onToDartError, onDone: _onDone);
    } on PlatformException catch(s){
      print(s);
    }
  }

void loadStream(double width) async {
  Stream stream;
  try{
    stream = await YyAds.loadNativeStream(<String,dynamic>{
      AdConfig.setPlacementId:"0000000058",
      AdConfig.setWidth: width.toInt(),
      AdConfig.setHeight: 0,
    });
    stream.listen(_onToDart, onError: _onToDartError, onDone: _onDone);
  } on PlatformException catch(s){
    print(s);
  }
}

void loadDraw() async {
  Stream stream;
  try{
    stream = await YyAds.loadDrawStream(<String,dynamic>{
      AdConfig.setPlacementId : "0000000063",
      AdConfig.setWidth: 500,
      AdConfig.setHeight:500,
    });
    stream.listen(_onToDart, onError: _onToDartError, onDone: _onDone);
  } on PlatformException catch(s){
    print(s);
  }
}

  // Native端发送正常数据回调方法，每一次发送都会调用
  static void _onToDart(message) {
    var verify = "";
    if(message is HashMap<dynamic,dynamic>) {
      verify = message["verify"];
      print(verify);
    }
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
