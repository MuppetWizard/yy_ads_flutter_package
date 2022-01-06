package com.youyi.yy_ads.factory

import android.content.Context
import com.youyi.yesdk.utils.UELogger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import java.net.URL

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/27
 */
class AndroidViewFactory(
    createArgsCodec: MessageCodec<Any>?
) : PlatformViewFactory(createArgsCodec) {

    private var viewPipe: SdkViewPipe? = null

    private var mListener: FactoryListener? = null

    val getViewPipe = viewPipe

    private var _isCreate = false
    val isCreate get() = _isCreate

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        viewPipe = SdkViewPipe(context)
        viewPipe?.container?.id = viewId
        mListener?.onReady(viewPipe)
        _isCreate = true
        UELogger.d("create $_isCreate")
        return viewPipe ?: SdkViewPipe(context)
    }

    fun setViewFactoryListener(listener: FactoryListener) {
        this.mListener = listener
    }

    interface FactoryListener{
        fun onReady(viewPipe: SdkViewPipe?)
    }

}