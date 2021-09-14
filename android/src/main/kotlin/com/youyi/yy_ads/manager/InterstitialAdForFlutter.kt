package com.youyi.yy_ads.manager

import android.app.Activity
import com.youyi.yesdk.ad.InterstitialAd
import com.youyi.yesdk.business.AdPlacement
import com.youyi.yesdk.listener.InterstitialAdListener
import com.youyi.yy_ads.Const
import com.youyi.yy_ads.EventChannelManager
import io.flutter.plugin.common.MethodCall

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/27
 */
class InterstitialAdForFlutter(
        private val context: Activity
) {

    private var mInterstitial:InterstitialAd? = null

    fun loadInterstitial(call: MethodCall, result: EventChannelManager) {
        val placementId = call.argument<String>(Const.CallParams.placementId)
        val isVertical = call.argument<Int>(Const.CallParams.orientation)
        val config = AdPlacement.Builder().apply {
            setAdId(placementId!!)
//                if (isVertical)
            if (isVertical == 1) isVertical(true) else isVertical(false)
            setMinVideoDuration(5)//设置视频广告最小时长
            setMaxVideoDuration(61)//设置视频广告最大时长
        }.build()
        mInterstitial = InterstitialAd()
        mInterstitial?.setInterstitialAdConfig(context,config)
        mInterstitial?.loadInterstitialAd(bindAdListener(result))
    }

    private fun bindAdListener(result: EventChannelManager) = object : InterstitialAdListener {
        override fun onAdCached() {
            result.send("onAdCached")
            mInterstitial?.show()
        }

        override fun onAdClicked() {
            result.send("onAdClicked")
        }

        override fun onAdClosed() {
            result.send("onAdClosed")
            result.cancel()
            mInterstitial?.destroy()
            
        }

        override fun onAdLoaded() {
            result.send("onAdLoaded")
        }

        override fun onAdShow() {
            result.send("onAdShow")
        }

        override fun onError(error: Int?, msg: String?) {
            result.sendError(error.toString(),msg ?: "onError","onError")
            result.cancel()
            mInterstitial?.destroy()
        }

        override fun onVideoComplete() {
            result.send("onVideoComplete")
        }
    }


}