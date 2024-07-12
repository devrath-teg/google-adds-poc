package com.istudio.googleads.modules.native_adds.demos.demo4

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NativeAddDemo4ScreenViewModel @Inject constructor(
    @ApplicationContext val context: Context
): ViewModel() {

    private var _nativeAdState = MutableStateFlow(AdState())
    val nativeAdState = _nativeAdState.asStateFlow()

    fun loadAd() = AdLoader.Builder(context, "/6499/example/native")
        .forNativeAd { ad: NativeAd ->
            //nativeAd = ad
            _nativeAdState.value = AdState(
                isSuccess = true,
                isLoading = false,
                nativeAd = ad
            )
        }
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                // Handle the failure by logging, altering the UI, and so on.
                Log.d("ERROR",p0.toString())
                _nativeAdState.value =AdState(
                    isSuccess = false,
                    isLoading = false,
                    errorMessage = p0.message
                )
            }
        })
        .withNativeAdOptions(NativeAdOptions.Builder().build())
        .build()


}