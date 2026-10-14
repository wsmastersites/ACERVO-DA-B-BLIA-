
package br.com.acervodabiblia.reader

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var webView: WebView
    private var fileCallback: ValueCallback<Array<Uri>>? = null
    private val pickBook = 4102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.loadsImagesAutomatically = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString() ?: return false

                return !url.startsWith("file:///android_asset/") &&
                       !url.startsWith("https://")
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {

                this@MainActivity.fileCallback?.onReceiveValue(null)
                this@MainActivity.fileCallback = filePathCallback

                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)

                    // Permite selecionar EPUB e PDF mesmo quando
                    // o gerenciador não reconhece corretamente o MIME.
                    type = "*/*"
                }

                return try {
                    startActivityForResult(intent, pickBook)
                    true
                } catch (e: Exception) {
                    this@MainActivity.fileCallback?.onReceiveValue(null)
                    this@MainActivity.fileCallback = null

                    Toast.makeText(
                        this@MainActivity,
                        "Não foi possível abrir os arquivos.",
                        Toast.LENGTH_LONG
                    ).show()

                    true
                }
            }
        }

        webView.loadUrl("file:///android_asset/index.html")
    }

    @Deprecated("Deprecated in Android API")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickBook) {
            val uri = if (resultCode == RESULT_OK) data?.data else null

            fileCallback?.onReceiveValue(
                if (uri != null) arrayOf(uri) else null
            )

            fileCallback = null

            if (uri == null) {
                Toast.makeText(
                    this,
                    "Nenhum arquivo selecionado.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onBackPressed() {
        webView.evaluateJavascript(
            "window.readerBack ? window.readerBack() : false"
        ) { handled ->
            if (handled != "true") super.onBackPressed()
        }
    }
}

