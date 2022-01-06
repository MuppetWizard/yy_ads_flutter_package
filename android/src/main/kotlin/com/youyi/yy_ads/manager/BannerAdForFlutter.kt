package com.youyi.yy_ads.manager

import android.app.Activity
import android.view.View
import com.youyi.yesdk.ad.BannerAd
import com.youyi.yesdk.business.AdPlacement
import com.youyi.yesdk.listener.BannerAdListener
import com.youyi.yy_ads.Const
import com.youyi.yy_ads.EventChannelManager
import com.youyi.yy_ads.factory.AndroidViewFactory
import com.youyi.yy_ads.factory.SdkViewPipe
import io.flutter.plugin.common.MethodCall

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/27
 */
class BannerAdForFlutter(
        private val context: Activity
) {
    private var bannerAd:BannerAd? = null

    fun loadBanner(call: MethodCall, eventChannel: EventChannelManager, factory: AndroidViewFactory?) {
        val placementId = call.argument<String>(Const.CallParams.placementId)
        val isCarousel = call.argument<Boolean>(Const.CallParams.isCarousel)
        val w = call.argument<Int>(Const.CallParams.width)
        val h = call.argument<Int>(Const.CallParams.height)
        val config = AdPlacement.Builder().apply {
            setAdId(placementId!!)
            isCarousel(isCarousel!!)
            setExpressViewAcceptedSize(w!!.toFloat(),h!!.toFloat())
        }.build()
        bannerAd = BannerAd()
        bannerAd?.setBannerConfig(context,config)
        bannerAd?.loadAdBanner(bindAdListener(eventChannel,factory?.getViewPipe))
    }

    private fun bindAdListener(eventChannel: EventChannelManager,viewPipe: SdkViewPipe?) = object : BannerAdListener {
        override fun onAdCloseOverLay() {
            eventChannel.send("onAdCloseOverLay")
        }

        override fun onClicked() {
            eventChannel.send("onClicked")
        }

        override fun onClosed() {
            eventChannel.send("onClosed")
            eventChannel.cancel()
            viewPipe?.removeAllViews()
            destroy()
        }

        override fun onDislikeCanceled() {
            eventChannel.send("onDislikeCanceled")
        }

        override fun onDislikeSelected(code: Int, msg: String?) {
            eventChannel.send("onDislikeSelected")
            viewPipe?.removeAllViews()
        }

        override fun onDislikeShow() {
            eventChannel.send("onDislikeShow")
        }

        override fun onError(code: Int?, message: String?) {
            eventChannel.sendError(code.toString(),message ?: "onError","onError")
            eventChannel.cancel()
        }

        override fun onLoaded(view: View?) {
            eventChannel.send("onLoaded")
            view?.let { viewPipe?.addView(it) } ?: eventChannel.sendError("10000","The View is Null","onError")

        }

        override fun onShow() {
            eventChannel.send("onShow")
        }

        override fun onShowAdOverLay() {
            eventChannel.send("onShowAdOverLay")

        }


    }

    private fun destroy() {
        bannerAd?.destroy()
        bannerAd = null
    }

}