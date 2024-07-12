package com.istudio.googleads.modules.native_adds.demos.demo4

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

@Composable
fun NativeAddDemo4Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NativeAdView()
    }
}

@Composable
private fun NativeAdView(
    modifier: Modifier = Modifier,
    vm: NativeAddDemo4ScreenViewModel = viewModel(),
) {

    val state = vm.nativeAdState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.loadAd().loadAd(AdRequest.Builder().build())
    }

    if(state.value.isLoading){
        // Placeholder view while ad is loading
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }else{
        if(state.value.isSuccess){
            state.value.nativeAd?.let {
                CallNativeAd(it)
            }
        }else{
            state.value.errorMessage?.let {
                BasicText(text = it)
            }
        }
    }
}

@Composable
fun CallNativeAd(nativeAd: NativeAd) {
    LiskovAdView(
        modifier = Modifier.fillMaxSize(),
        nativeAd = nativeAd
    )
}
