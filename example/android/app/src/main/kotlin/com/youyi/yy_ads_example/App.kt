package com.youyi.yy_ads_example

import com.youyi.yesdk.YOUEAdSdk
import com.youyi.yesdk.business.YOUEAdManager
import io.flutter.app.FlutterApplication

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/24
 */
class App: FlutterApplication() {

    override fun onCreate() {
        super.onCreate()
        val config = YOUEAdManager()
                .appId("000012")
                .appName("游易")
                .deBug(true)
                .setChannel(5)
                .supportMultiProcess(false)
                .build()
        YOUEAdSdk.initSDK(this,config)
    }
}