package com.revakovsky.thenytimesbooks.presentation.screens.store

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.revakovsky.thenytimesbooks.presentation.widgets.SwipeRefreshContainer

@Composable
fun StoreScreen(
    url: String,
    backToBooksScreen: () -> Unit,
) {
    val context = LocalContext.current

    var isWebPageLoading by remember { mutableStateOf(true) }
    var webPageLoadingProgress by remember { mutableIntStateOf(0) }

    val webView = remember {

        WebView(context).apply {
            setUpWebView(this)

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    webPageLoadingProgress = newProgress
                    isWebPageLoading = newProgress != 100
                }
            }

            webViewClient = object : WebViewClient() {}
        }

    }

    LaunchedEffect(key1 = true) { webView.loadUrl(url) }

    SwipeRefreshContainer(
        onRefresh = { webView.reload() }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { webView },
            )

            if (isWebPageLoading) {

                LinearProgressIndicator(
                    progress = webPageLoadingProgress / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.TopCenter),
                    trackColor = MaterialTheme.colorScheme.surface,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }

        }

    }

    BackHandler {
        when (webView.canGoBack()) {
            true -> webView.goBack()
            false -> backToBooksScreen()
        }
    }

    DisposableEffect(Unit) {
        onDispose { webView.destroy() }
    }

}


@SuppressLint("SetJavaScriptEnabled")
private fun setUpWebView(webView: WebView) {
    webView.settings.apply {
        javaScriptEnabled = true
        domStorageEnabled = true
        useWideViewPort = true
        userAgentString.replace("wv", "")
    }
}
