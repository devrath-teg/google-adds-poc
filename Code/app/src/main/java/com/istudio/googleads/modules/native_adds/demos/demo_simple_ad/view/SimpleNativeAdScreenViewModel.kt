@file:OptIn(ExperimentalCoroutinesApi::class)

package com.istudio.googleads.modules.native_adds.demos.demo_simple_ad.view

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.istudio.googleads.modules.native_adds.demos.demo_simple_ad.states.AdState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class SimpleNativeAdScreenViewModel @Inject constructor() : ViewModel() {

    private var _nativeAdState = MutableStateFlow(AdState())
    val nativeAdState = _nativeAdState.asStateFlow()

    suspend fun loadAd(context: Context) {
        _nativeAdState.value = AdState(isLoading = true)

        val adState = suspendCancellableCoroutine { continuation ->

            val adLoader = AdLoader.Builder(context, "/6499/example/native")
                .forNativeAd { ad: NativeAd ->
                    continuation.resume(
                        AdState(
                            isSuccess = true,
                            isLoading = false,
                            nativeAd = ad
                        )
                    )
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        continuation.resume(
                            AdState(
                                isSuccess = false,
                                isLoading = false,
                                errorMessage = loadAdError.message
                            )
                        )
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()

            adLoader.loadAd(AdRequest.Builder().build())
        }

        _nativeAdState.value = adState
    }

}