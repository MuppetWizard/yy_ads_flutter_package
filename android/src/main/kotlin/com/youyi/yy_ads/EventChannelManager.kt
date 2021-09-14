package com.youyi.yy_ads

import android.app.Activity
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import java.util.*

/**
 * des：
 * @author: Muppet
 * @date: 2021/8/30
 */
class EventChannelManager : EventChannel.StreamHandler {

    private var eventSink:EventChannel.EventSink? = null

    // 1. 创建 & 注册EventChannel
    companion object{
        fun registerWith(binaryMessenger: BinaryMessenger,name: String) : EventChannelManager{
            val channel =  EventChannel(binaryMessenger, name)
            val plugin = EventChannelManager()
            channel.setStreamHandler(plugin)
            return plugin
        }
    }

    fun send(params: Any) {
        eventSink?.success(params)
    }

    fun sendError(errorCode: String,errorMsg: String,details: String) {
        eventSink?.error(errorCode,errorMsg,details)
    }

    fun cancel() {
        eventSink?.endOfStream()
    }


    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events

    }

    override fun onCancel(arguments: Any?) {
    }
}