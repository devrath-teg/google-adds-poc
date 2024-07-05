package com.istudio.googleads.modules.banner_adds

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerScreen(
    modifier: Modifier = Modifier,
    displayMetrics: DisplayMetrics
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        AdmobBanner3(displayMetrics)
        //AdmobBanner2()
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun AdmobBanner3(displayMetrics: DisplayMetrics) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Cyan),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val size = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(LocalContext.current, AdSize.FULL_WIDTH)

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(size)
                    adUnitId = "ca-app-pub-3940256099942544/9214589741"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}


@Composable
fun AdmobBanner1(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.LARGE_BANNER)
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}


@Composable
fun AdmobBanner2() {
    Column(
        modifier = Modifier
            .background(color = Color.Cyan),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.MEDIUM_RECTANGLE)
                    adUnitId = "ca-app-pub-3940256099942544/9214589741"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}