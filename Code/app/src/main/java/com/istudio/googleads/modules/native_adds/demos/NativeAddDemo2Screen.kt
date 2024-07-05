package com.istudio.googleads.modules.native_adds.demos

import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun NativeAddDemo2Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(color = Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NativeAdView()
    }
}

@Composable
private fun NativeAdView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }

    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                nativeAd = ad
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    if (nativeAd != null) {
        DisplayNativeAd(nativeAd!!)
    } else {
        // Placeholder view while ad is loading
        BasicText(text = "Loading ad...")
    }
}

@Composable
fun DisplayNativeAd(nativeAd: NativeAd) {
    Column(
        modifier = Modifier.background(color = Color.Blue).padding(16.dp)
    ) {
        nativeAd.headline?.let {
            BasicText(text = it, modifier = Modifier.padding(bottom = 4.dp))
        }
        nativeAd.body?.let {
            BasicText(text = it, modifier = Modifier.padding(bottom = 4.dp))
        }
        // Add other components like media view, call-to-action button, etc.
        nativeAd.mediaContent?.let {
            val mediaView = MediaView(LocalContext.current)
            mediaView.mediaContent = it
            AndroidView(
                factory = { mediaView },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 4.dp)
            )
        }
        nativeAd.callToAction?.let {
            BasicText(text = it, modifier = Modifier.padding(bottom = 4.dp))
        }
    }
}