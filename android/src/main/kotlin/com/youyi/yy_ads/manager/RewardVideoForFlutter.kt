package com.youyi.yy_ads.manager

import android.app.Activity
import com.youyi.yesdk.ad.RewardVideoAd
import com.youyi.yesdk.ad.YOUEAdConstants
import com.youyi.yesdk.business.AdPlacement
import com.youyi.yesdk.listener.RewardListener
import com.youyi.yy_ads.Const
import com.youyi.yy_ads.EventChannelManager
import io.flutter.plugin.common.MethodCall

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/31
 */
class RewardVideoForFlutter(
        private val context: Activity
) {
    private var rewardAd: RewardVideoAd? = null

    fun loadRewardAd(call: MethodCall, eventResult: EventChannelManager) {
        rewardAd = RewardVideoAd()
        val mConfig = initParams(call)
        rewardAd?.setRewardConfig(context,mConfig)
        rewardAd?.loadRewardVideo(bindAdListener(eventResult))
    }

    private fun bindAdListener(eventResult: EventChannelManager) = object :RewardListener {
        override fun onADComplete() {
            eventResult.send("onADComplete")
        }

        override fun onADLoaded() {
            eventResult.send("onADLoaded")
        }

        override fun onVideoCached() {
            eventResult.send("onVideoCached")
            rewardAd?.show()
        }

        override fun onADShow() {
            eventResult.send("onADShow")
        }

        override fun onClosed() {
            eventResult.send("onClosed")
            eventResult.cancel()
        }

        override fun onError(errorCode: Int?, msg: String?) {
            eventResult.sendError(errorCode.toString(),msg ?: "onError","onError")
            eventResult.cancel()
        }

        override fun onReward(verify: Boolean?, rewardAmount: Int?, rewardName: String?, errorCode: Int?, errorMsg: String?, map: MutableMap<String, Any>?) {
            val paramsMap = mapOf(
                    "verify" to verify,
                    "rewardAmount" to rewardAmount,
                    "rewardName" to rewardName,
                    "extraCode" to errorCode,
                    "extraMsg" to errorMsg,
                    "map" to map
            )
            eventResult.send(paramsMap)
        }

        override fun onSKipVideo() {
            eventResult.send("onSKipVideo")
        }

        override fun onVideoBarClick() {
            eventResult.send("onVideoBarClick")
        }
    }

    private fun initParams(call: MethodCall) : AdPlacement {
        val placementId = call.argument<String>(Const.CallParams.placementId)
        val userId = call.argument<String>(Const.CallParams.userId)
        val customData = call.argument<String>(Const.CallParams.customData)
        val scenes = call.argument<String>(Const.CallParams.scenes)
        val scenesMsg = call.argument<String>(Const.CallParams.scenesMsg)
        val orientation = call.argument<Int>(Const.CallParams.orientation)
        
        return AdPlacement.Builder().apply {
            setAdId(placementId!!)
            userId?.let{ setUserID(it) }
            customData?.let{ setCustomData(it) }
            scenes?.let{
                setScenes(Const.Scenes.getScenes(it),scenesMsg ?: "message")
            }
            setExpressViewAcceptedSize(500f,500f)
            if (orientation == 1) 
                setOrientation(YOUEAdConstants.VERTICAL) 
            else 
                setOrientation(YOUEAdConstants.HORIZONTAL)
            
        }.build()
    }

}