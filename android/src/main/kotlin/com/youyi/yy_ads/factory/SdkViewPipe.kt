package com.youyi.yy_ads.factory

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.youyi.yesdk.utils.UELogger
import com.youyi.yy_ads.R
import io.flutter.plugin.platform.PlatformView

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/27
 */
class SdkViewPipe(
    private val context: Context
): PlatformView {

    val container = FrameLayout(context)

    init {
        container.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        container.setBackgroundColor(Color.parseColor("#00FFFFFF"))
    }

    /**
     * add view to flutter widget
     */
    fun addView(view: View) {
        container.removeAllViews()
        container.addView(view)
    }

    fun removeView(view: View) {
        container.removeView(view)
    }

    fun removeAllViews() = container.removeAllViews()

    override fun getView(): View {
        return container
    }

    override fun dispose() {

    }
}