package com.example.untitled12;
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(FlutterEngine(this))
        MethodChannel(
            flutterEngine!!.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            val url = call.argument<String>("url")
            if (call.method == "openBrowser") {
                openBrowser(call, result, url)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun openBrowser(call: MethodCall, result: MethodChannel.Result, url: String?) {
        val activity: Activity = this
        if (activity == null) {
            result.error(
                "ACTIVITY_NOT_AVAILABLE",
                "Browser cannot be opened without foreground activity", null
            )
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        activity.startActivity(intent)
        result.success(true as Any)
    }
//https://truyenfull.vn/
    companion object {
        private const val CHANNEL = "com.example.untitled12"
    }
}