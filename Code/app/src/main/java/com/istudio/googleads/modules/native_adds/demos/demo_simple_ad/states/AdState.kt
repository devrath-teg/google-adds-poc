package com.istudio.googleads.modules.native_adds.demos.demo_simple_ad.states

import com.google.android.gms.ads.nativead.NativeAd

data class AdState(
    val isLoading: Boolean = true,
    val isSuccess :Boolean =  false,
    val errorMessage :String? = null,
    val nativeAd : NativeAd? = null
)
