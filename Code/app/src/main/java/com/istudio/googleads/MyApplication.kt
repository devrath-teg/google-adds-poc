package com.istudio.googleads

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeAdsSdk()
    }

    private fun initializeAdsSdk() {
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(this@MyApplication)
        }
    }
}