package com.youyi.yy_ads.manager

import android.app.Activity
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import com.youyi.yesdk.ad.SplashAd
import com.youyi.yesdk.business.AdPlacement
import com.youyi.yesdk.listener.SplashListener
import com.youyi.yesdk.utils.DensityUtil
import com.youyi.yy_ads.Const
import com.youyi.yy_ads.EventChannelManager
import com.youyi.yy_ads.R
import com.youyi.yy_ads.factory.AndroidViewFactory
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/25
 */
class SplashAdForFlutter(
    private val context: Activity,
    private val factory: AndroidViewFactory
) {
    private var splashAd: SplashAd? = null


    fun loadBSplash(call: MethodCall, result: EventChannelManager) {
        val placementId = call.argument<String>(Const.CallParams.placementId)
        val timeout = call.argument<Int>(Const.CallParams.timeout)
        val config = AdPlacement.Builder().apply {
            setAdId(placementId!!)
            setTimeOut(timeout!!)
        }.build()
        splashAd = SplashAd()
        splashAd?.setSplashConfig(context,config)
        splashAd?.loadSplashAd(factory.getViewPipe()?.view as? ViewGroup ?: createContainer(),
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
            result.send("onAdCanceled")
            destroy()
        }
    }

    private fun createContainer(): ViewGroup {
        val container = FrameLayout(context)

        container.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        factory.getViewPipe()?.addView(container)
        return container
    }

    private fun destroy() {
        splashAd?.destroy()
        splashAd = null
    }

}