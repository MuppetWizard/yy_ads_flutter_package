package com.youyi.yy_ads.manager

import android.app.Activity
import com.youyi.yesdk.ad.DrawStreamAd
import com.youyi.yesdk.business.AdPlacement
import com.youyi.yesdk.listener.StreamAdExpress
import com.youyi.yesdk.listener.StreamAdInteractionListener
import com.youyi.yesdk.listener.StreamAdListener
import com.youyi.yesdk.listener.UEVideoListener
import com.youyi.yy_ads.Const
import com.youyi.yy_ads.EventChannelManager
import com.youyi.yy_ads.factory.SdkViewPipe
import io.flutter.plugin.common.MethodCall

/**
 * des：
 * @author: Muppet
 * @date: 2021/9/1
 */
class DrawStreamForFlutter(
        private val context: Activity
) {
    private var drawStreamAd: DrawStreamAd? = null
    private var streamView: StreamAdExpress? = null

    fun loadDrawStream(call: MethodCall, eventResult: EventChannelManager, viewPipe: SdkViewPipe?) {
        val placementId = call.argument<String>(Const.CallParams.placementId)
        val width = call.argument<Int>(Const.CallParams.width)
        val height = call.argument<Int>(Const.CallParams.height)
        val config = AdPlacement.Builder().apply {
            setAdId(placementId!!)
            setCanInterruptVideoPlay(true)
            setExpressViewAcceptedSize(width!!.toFloat() ,height?.toFloat() ?: 0F)
            setAdCount(1)
        }.build()
        drawStreamAd = DrawStreamAd()
        drawStreamAd?.setDrawStreamConfig(context,config)
        drawStreamAd?.loadDrawStreamAd(bindAdListener(eventResult,viewPipe))

    }

    private fun bindAdListener(eventResult: EventChannelManager, viewPipe: SdkViewPipe?) = object :StreamAdListener {
        override fun onAdLoaded(ads: ArrayList<StreamAdExpress>) {
            if (ads.size <= 0){
                eventResult.sendError(4401.toString(),"No Data","No Data")
                eventResult.cancel()
                return
            }
            eventResult.send("onAdLoaded")
            streamView = ads[0].apply {
                setStreamAdInteractionListener(bindInteractionListener(eventResult,viewPipe))
                setStreamVideoAdListener(bindVideoListener(eventResult))
                setDownloadConfirmListener()
                render()
            }
        }

        override fun onError(errorCode: Int?, msg: String?) {
            eventResult.sendError(errorCode.toString(),msg ?: "onError","onError")
            eventResult.cancel()
            streamView?.destroy()
        }
    }

    /** 交互监听 */
    private fun bindInteractionListener(eventResult: EventChannelManager, viewPipe: SdkViewPipe?) = object : StreamAdInteractionListener {
        override fun onAdClicked() {
            eventResult.send("onAdClicked")
        }

        override fun onAdClosed() {
            eventResult.send("onAdClosed")
            eventResult.cancel()
            streamView?.destroy()
        }

        override fun onAdShow() {
            eventResult.send("onAdShow")
        }

        override fun onRenderFailed(code: Int?, msg: String?) {
            eventResult.sendError(code.toString(),msg ?: "onRenderFailed","onRenderFailed")
            eventResult.cancel()
        }

        override fun onRenderSuccess() {
            streamView?.let {
                val mView = it.getStreamView()
                if (mView != null){
                    eventResult.send("onRenderSuccess")
                    viewPipe!!.addView(mView)
                }else{
                    eventResult.sendError("60004", "onRenderFailed","render view is Null")
                }

            }
        }
    }

    /** video listener */
    private fun bindVideoListener(eventResult: EventChannelManager) = object : UEVideoListener {
        override fun onVideoAdLoad() {
            eventResult.send("onVideoAdLoad")
        }

        override fun onVideoAdError(errorCode: Int, extraCode: Int) {
            eventResult.sendError(errorCode.toString(),"onVideoAdError","extraCode: $extraCode")
        }

        override fun onVideoAdStartPlay() {
            eventResult.send("onVideoAdStartPlay")
        }

        override fun onVideoAdPaused() {
            eventResult.send("onVideoAdPaused")
        }

        override fun onVideoAdComplete() {
            eventResult.send("onVideoAdComplete")
        }
    }

}