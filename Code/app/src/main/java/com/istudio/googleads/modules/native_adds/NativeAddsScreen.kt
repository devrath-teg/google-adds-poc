package com.istudio.googleads.modules.native_adds

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions


@Composable
fun NativeAddsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NativeAdView()
    }
}

@Composable
fun NativeAdView() {
    val context = LocalContext.current

    // Placeholder for the ad data
    var nativeAd: NativeAd? = null
    lateinit var adLoader: AdLoader
    // Load the native ad
    adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
        .forNativeAd { ad: NativeAd ->
            // Show the ad.
            nativeAd = ad
            if (adLoader.isLoading) {
                // The AdLoader is still loading ads.
                // Expect more adLoaded or onAdFailedToLoad callbacks.
            } else {
                // The AdLoader has finished loading ads.
            }
        }
        .withAdListener(object : com.google.android.gms.ads.AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                // Handle the failure by logging, altering the UI, and so on.
            }
        })
        .withNativeAdOptions(NativeAdOptions.Builder()
            // Methods in the NativeAdOptions.Builder class can be
            // used here to specify individual options settings.
            .build()
        )
        .build()

    //This method sends a request for a single ad.
    adLoader.loadAd(AdRequest.Builder().build())
    //This method sends a request for multiple ads (up to five):
   // adLoader.loadAds(AdRequest.Builder().build(), 3)

    // Display the ad
    if (nativeAd != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            BasicText(text = nativeAd!!.headline ?: "")
            BasicText(text = nativeAd!!.body ?: "")
            // Add other components (images, call-to-action button, etc.)
        }
    } else {
        // Placeholder view while ad is loading
        BasicText(text = "Loading ad...")
    }
}