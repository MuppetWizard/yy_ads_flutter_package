package com.youyi.yy_ads

import com.youyi.yesdk.ad.YOUEAdConstants

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/25
 */
object Const {
    const val METHOD_CHANNEL = "com.youyi.yyAds_method"
    const val EVENT_CHANNEL = "com.youyi.yyAds_event"

    const val SPLASH_VIEW_ID = "yySplashAdView"
    const val BANNER_VIEW_ID = "yyBannerAdView"
    const val STREAM_VIEW_ID = "yyStreamAdView"
    const val DRAW_STREAM_VIEW_ID = "yyDrawStreamView"

    class ChannelMethod{
        companion object{
            const val LOAD_SPLASH = "loadSplash"
            const val LOAD_BANNER = "loadBanner"
            const val LOAD_INTERSTITIAL = "loadInterstitial"
            const val LOAD_FULLSCREEN_VIDEO = "loadFullscreenVideo"
            const val LOAD_REWARD_VIDEO = "loadRewardVideo"
            const val LOAD_NATIVE_STREAM = "loadNativeStream"
            const val LOAD_NATIVE_DRAW_STREAM = "loadDrawStream"
        }
    }

    class CallParams{
        companion object{
            const val placementId = "placementId"
            const val width = "width"
            const val height = "height"
            const val timeout = "timeout"
            const val isCarousel = "isCarousel"
            const val orientation = "orientation"

            const val splashClickType = "splashClickType"
            const val splashInteractionType = "splashInteractionType"

            const val userId = "userId"
            const val customData = "customData"
            const val scenes = "scenes"
            const val scenesMsg = "scenesMsg"

        }
    }

    class Scenes{
        companion object{
            private const val CUSTOMIZE_SCENES="CUSTOMIZE_SCENES"
            private const val HOME_OPEN_BONUS = "HOME_OPEN_BONUS"
            private const val HOME_SVIP_BONUS = "HOME_SVIP_BONUS"
            private const val HOME_GET_PROPS = "HOME_GET_PROPS"
            private const val HOME_TRY_PROPS = "HOME_TRY_PROPS"
            private const val HOME_GET_BONUS = "HOME_GET_BONUS"
            private const val HOME_GIFT_BONUS = "HOME_GIFT_BONUS"
            private const val GAME_START_BONUS = "GAME_START_BONUS"
            private const val GAME_REDUCE_WAITING = "GAME_REDUCE_WAITING"
            private const val GAME_MORE_KLLKRTUNITIES = "GAME_MORE_KLLKRTUNITIES"
            private const val GAME_FINISH_REWARDS = "GAME_FINISH_REWARDS"
            private const val GAME_GIFT_BONUS = "GAME_GIFT_BONUS"

            fun getScenes(mScenes: String) : YOUEAdConstants.RitScenes {
                return when (mScenes) {
                    CUSTOMIZE_SCENES -> YOUEAdConstants.RitScenes.CUSTOMIZE_SCENES
                    HOME_OPEN_BONUS -> YOUEAdConstants.RitScenes.HOME_OPEN_BONUS
                    HOME_SVIP_BONUS -> YOUEAdConstants.RitScenes.HOME_SVIP_BONUS
                    HOME_GET_PROPS -> YOUEAdConstants.RitScenes.HOME_GET_PROPS
                    HOME_TRY_PROPS -> YOUEAdConstants.RitScenes.HOME_TRY_PROPS
                    HOME_GET_BONUS -> YOUEAdConstants.RitScenes.HOME_GET_BONUS
                    HOME_GIFT_BONUS -> YOUEAdConstants.RitScenes.HOME_GIFT_BONUS
                    GAME_START_BONUS -> YOUEAdConstants.RitScenes.GAME_START_BONUS
                    GAME_REDUCE_WAITING -> YOUEAdConstants.RitScenes.GAME_REDUCE_WAITING
                    GAME_MORE_KLLKRTUNITIES -> YOUEAdConstants.RitScenes.GAME_MORE_KLLKRTUNITIES
                    GAME_FINISH_REWARDS -> YOUEAdConstants.RitScenes.GAME_FINISH_REWARDS
                    GAME_GIFT_BONUS -> YOUEAdConstants.RitScenes.GAME_GIFT_BONUS
                    else -> YOUEAdConstants.RitScenes.CUSTOMIZE_SCENES
                }
            }
        }
    }
}