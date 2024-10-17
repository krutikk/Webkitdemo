package com.krutik.webkitdemo.sample

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.webkit.WebViewAssetLoader
import com.krutik.webkitdemo.Bridge
import com.krutik.webkitdemo.JSFunctionWithArg
import com.krutik.webkitdemo.JSFunctionWithPromise
import com.krutik.webkitdemo.JSFunctionWithPromiseAndArg
import com.krutik.webkitdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var counter = 0;

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WebView.setWebContentsDebuggingEnabled(true)


        val bridge = Bridge(applicationContext, binding.webView)
        bridge.addJSInterface(AndroidNativeInterface(this@MainActivity))

        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this))
            .build()

        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                return assetLoader.shouldInterceptRequest(request.url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                bridge.init()
            }
        }

        binding.webView.loadUrl("https://appassets.androidplatform.net/assets/www/index.html")
    }

    fun registerFunctionToButton1(function: JSFunctionWithArg<Int>) {
        binding.button.setOnClickListener {
            ++counter
            function.call(counter)
        }
    }

    fun registerFunctionToButton2(function: JSFunctionWithPromise<String>) {
        binding.button2.setOnClickListener {
            function.call().then {
                runOnUiThread { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
            }.catch {
                runOnUiThread { Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    fun registerFunctionToButton3(function: JSFunctionWithPromiseAndArg<Add, String>) {
        binding.button3.setOnClickListener {
            function.call(Add((Math.random() * 10).toInt(), (Math.random() * 10).toInt())).then {
                runOnUiThread { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
            }.catch {
                runOnUiThread { Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    fun callApi(s : String) {
        Toast.makeText(this, "Calling API$s", Toast.LENGTH_SHORT).show()
    }
}
