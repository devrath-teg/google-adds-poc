package com.istudio.googleads.modules.banner_adds.demos.demo_list_ad

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.*
import com.istudio.googleads.R
import com.istudio.googleads.ui.theme.GoogleAdsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Locale

class BannerListActivity : ComponentActivity() {

    private val listItems: MutableList<Any> = mutableStateListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleAdsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListComposable(listItems)
                    BannerListEffect()
                }
            }
        }
    }

    @Composable
    private fun BannerListEffect() {
        LaunchedEffect(Unit) {
            // Get a bunch of items and add it into the list collection
            addDataFromDataSource()
            // Add new placeholders of AD views that we will load in the next step
            addBannerAdsToDataSet()
            // Initialize the loading of adds
            loadBannerAds()
        }
    }

    private fun addDataFromDataSource() {
        try {
            listItems.addAll(MockMenuItem.menuItems)
        } catch (exception: IOException) {
            Log.e(BannerListActivity::class.java.name, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(BannerListActivity::class.java.name, "Unable to parse JSON file.", exception)
        }
    }

    private fun addBannerAdsToDataSet() {
        var i = 0
        while (i <= listItems.size) {
            // Prepare the add
            val adView = prepareAdd()
            // On every span of item duration, We add the add
            listItems.add(i, adView)
            i += ITEMS_PER_AD
        }
    }

    private fun loadBannerAds() {
        loadBannerAd(0)
    }

    private fun loadBannerAd(index: Int) {
        if (index >= listItems.size) {
            return
        }
        val item = listItems[index] as? AdView ?: throw ClassCastException("Expected item at index $index to be a banner ad.")

        item.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                loadBannerAd(index + ITEMS_PER_AD)
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error = String.format(
                    Locale.getDefault(),  // Specify the locale explicitly
                    "domain: %s, code: %d, message: %s",
                    loadAdError.domain,
                    loadAdError.code,
                    loadAdError.message
                )
                Log.e(
                    "MainActivity",
                    "The previous banner ad failed to load with error: $error. Attempting to load the next banner ad in the items list."
                )
                loadBannerAd(index + ITEMS_PER_AD)
            }
        }

        item.loadAd(AdRequest.Builder().build())
    }

    private fun prepareAdd(): AdView {
        val adView = AdView(this@BannerListActivity)
        adView.setAdSize(
            AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(
                this,
                AdSize.FULL_WIDTH
            )
        )
        adView.adUnitId = AD_UNIT_ID
        return adView
    }

    companion object {
        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
        const val ITEMS_PER_AD = 8
        const val TEST_DEVICE_HASHED_ID = "ABCDEF012345"
    }
}



