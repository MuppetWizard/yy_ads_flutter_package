package com.youyi.yy_ads.manager

import android.app.Activity
import com.youyi.yesdk.ad.FullVideoAd
import com.youyi.yesdk.business.AdPlacement
import com.youyi.yesdk.listener.FullVideoListener
import com.youyi.yy_ads.Const
import com.youyi.yy_ads.EventChannelManager
import io.flutter.plugin.common.MethodCall

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/31
 */
class FullscreenVideoForFlutter(
        private val context: Activity
) {
    private var mFullscreenVideo:FullVideoAd? = null

    fun loadFullscreenVideo(call: MethodCall,eventResult: EventChannelManager) {
        val placementId = call.argument<String>(Const.CallParams.placementId)
        val orientation = call.argument<Int>(Const.CallParams.orientation)
        val config = AdPlacement.Builder()
                .setAdId(placementId!!)
                //设置期望广告素材的宽高，单位dp, 大于0即可
                .setExpressViewAcceptedSize(500f,500f)
                //设置视频播放方向，请与平台设置保持一致
                .setOrientation(orientation!!)
                .setMinVideoDuration(5)//设置视频最小时长
                .setMaxVideoDuration(61)//设置视频最大时长
                .build()
        mFullscreenVideo = FullVideoAd()
        mFullscreenVideo?.setVideoConfig(context,config)
        mFullscreenVideo?.loadFullVideo(bindAdListener(eventResult))
    }

    private fun bindAdListener(eventResult: EventChannelManager) = object :FullVideoListener {
        override fun onAdCached() {
            eventResult.send("onAdCached")
            mFullscreenVideo?.show()
        }

        override fun onAdClicked() {
            eventResult.send("onAdClicked")
        }

        override fun onAdClosed() {
            eventResult.send("onAdClosed")
            eventResult.cancel()
            mFullscreenVideo?.destroy()
        }

        override fun onAdComplete() {
            eventResult.send("onAdComplete")
        }

        override fun onAdLoaded() {
            eventResult.send("onAdLoaded")
        }

        override fun onAdShow() {
            eventResult.send("onAdShow")
        }

        override fun onAdSkipped() {
            eventResult.send("onAdSkipped")
        }

        override fun onError(error: Int?, msg: String?) {
            eventResult.sendError(error.toString(),msg ?: "onError","onError")
            eventResult.cancel()
            mFullscreenVideo?.destroy()
        }

    }
}