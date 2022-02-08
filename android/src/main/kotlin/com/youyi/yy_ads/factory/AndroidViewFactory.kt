package com.youyi.yy_ads.factory

import android.content.Context
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

    private var mListener: FactoryListener? = null

    private var viewPipe: SdkViewPipe? = null
    val getViewPipe get()  = viewPipe

    private var _isCreate = false
    val isCreate get() = _isCreate

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        viewPipe = SdkViewPipe(context)
        viewPipe?.container?.id = viewId
        _isCreate = true
        mListener?.onReady(viewPipe)
        return viewPipe ?: SdkViewPipe(context)
    }

    fun setViewFactoryListener(listener: FactoryListener) {
        this.mListener = listener
    }

    interface FactoryListener{
        fun onReady(viewPipe: SdkViewPipe?)
    }

}