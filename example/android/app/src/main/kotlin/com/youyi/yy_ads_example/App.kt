package com.youyi.yy_ads_example

import android.text.format.Formatter
import com.youyi.yesdk.YOUEAdSdk
import com.youyi.yesdk.business.YOUEAdManager
import com.youyi.yesdk.business.YOUECustomController
import com.youyi.yesdk.utils.UELogger
import io.flutter.app.FlutterApplication

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/24
 */
class App: FlutterApplication() {

    override fun onCreate() {
        super.onCreate()
        val config = YOUEAdManager.Builder()
            .appId("000012")
            .appName("游易")
            .deBug(true)
            .setChannel(5)
            .supportMultiProcess(false)
            .setCustomController(createCustomController())
            .build()

        YOUEAdSdk.initSDK(this,config)
    }

    private fun createCustomController(): YOUECustomController {
        return object : YOUECustomController() {
            override fun getDevOaid(): String {
                return ""
            }

            override fun isAllowGetOaid(): Boolean {
                return true
            }

            override fun isAllowLocation(): Boolean {
                return true
            }
        }
    }
}