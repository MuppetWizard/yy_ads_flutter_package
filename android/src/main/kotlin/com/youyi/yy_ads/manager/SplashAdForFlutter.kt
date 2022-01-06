package com.youyi.yy_ads.manager

import android.app.Activity
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import com.youyi.yesdk.ad.SplashAd
import com.youyi.yesdk.ad.YOUEAdConstants
import com.youyi.yesdk.business.AdPlacement
import com.youyi.yesdk.listener.SplashListener
import com.youyi.yy_ads.Const
import com.youyi.yy_ads.EventChannelManager
import com.youyi.yy_ads.factory.AndroidViewFactory
import com.youyi.yy_ads.factory.SdkViewPipe
import io.flutter.plugin.common.MethodCall

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/25
 */
class SplashAdForFlutter(
    private val context: Activity
) {
    private var splashAd: SplashAd? = null


    fun loadBSplash(call: MethodCall, result: EventChannelManager, factory: SdkViewPipe) {
        val placementId = call.argument<String>(Const.CallParams.placementId)
        val timeout = call.argument<Int>(Const.CallParams.timeout)
        val clickType = call.argument<Int>(Const.CallParams.splashClickType)
        val interactionType = call.argument<Int>(Const.CallParams.splashInteractionType)
        val config = AdPlacement.Builder().apply {
            setAdId(placementId!!)
            setSplashClickType(clickType ?: YOUEAdConstants.LIMIT_CLICK_AREA)
            setInteractionType(interactionType ?: YOUEAdConstants.SPLASH_NORMAL)
            setTimeOut(timeout!!)
        }.build()
        splashAd = SplashAd()
        splashAd?.setSplashConfig(context,config)
//        UELogger.d("visibility: ${viewPipe.getViewPipe().view.visibility} ")
        splashAd?.loadSplashAd(factory.view as ViewGroup ,
            bindAdListener(result))
    }

    private fun bindAdListener(result: EventChannelManager) = object :SplashListener {
        override fun onAdCanceled() {
            result.send("onAdCanceled")
            destroy()
        }

        override fun onAdClicked() {
            result.send("onAdClicked")
        }

        override fun onAdShow() {
            result.send("onAdShow")
        }

        override fun onError(code: Int?, msg: String?) {
            result.sendError(code.toString(), msg ?: "onError","onError")
            destroy()
        }

        override fun onTimeOut() {
            result.send("onTimeOut")
            destroy()
        }
    }

//    private fun createContainer(): ViewGroup {
//        val container = FrameLayout(context)
//
//        container.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        container.setBackgroundColor(Color.parseColor("#00FFFFFF"))
//        factory.getViewPipe?.addView(container)
//        return factory.getViewPipe?.view as ViewGroup
//    }

    private fun destroy() {
        splashAd?.destroy()
        splashAd = null
    }

}