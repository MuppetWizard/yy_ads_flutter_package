package com.youyi.yy_ads.factory

import android.content.Context
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/27
 */
class AndroidViewFactory(createArgsCodec: MessageCodec<Any>?) : PlatformViewFactory(createArgsCodec) {

    private var viewPipe: SdkViewPipe? = null

    fun getViewPipe() = viewPipe

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        viewPipe = SdkViewPipe(context)

        return viewPipe ?: SdkViewPipe(context)
    }


}