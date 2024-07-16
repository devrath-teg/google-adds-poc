@file:OptIn(ExperimentalCoroutinesApi::class)

package com.istudio.googleads.modules.banner_adds.demos.demo_list_ad.view

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.istudio.googleads.modules.banner_adds.demos.demo_list_ad.MockMenuItem
import com.istudio.googleads.modules.banner_adds.demos.demo_list_ad.states.BannerListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONException
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BannerListScreenViewModel @Inject constructor() : ViewModel() {

    private val tag: String = BannerListScreenViewModel::class.java.name
    private val listItems: MutableList<Any> = mutableStateListOf()

    private var _nativeBannerListState = MutableStateFlow(BannerListState())
    val nativeAdState = _nativeBannerListState.asStateFlow()


    fun initiate(context : Context) {
        // Get a bunch of items and add it into the list collection
        addDataFromDataSource()
        // Add new placeholders of AD views that we will load in the next step
        addBannerAdsToDataSet(context)
        // Initialize the loading of adds
        loadBannerAds()
        // Notify the view
        successState()
    }

    /**
     * ******************************** Setting states **********************************
     */
    private fun loadingState() {
        _nativeBannerListState.value = BannerListState(
            isSuccess = false,
            isLoading = true,
            data = null,
            errorMessage = null
        )
    }

    private fun successState() {
        _nativeBannerListState.value = BannerListState(
            isSuccess = true,
            isLoading = false,
            data = listItems
        )
    }

    private fun errorState(message : String) {
        _nativeBannerListState.value = BannerListState(
            isSuccess = false,
            isLoading = false,
            data = null,
            errorMessage = message
        )
    }
    /**
     * ******************************** Setting states **********************************
     */


    /**
     * ******************************** Main Functions **********************************
     */
    private fun addDataFromDataSource() {

        try {
            listItems.addAll(MockMenuItem.menuItems)
        } catch (exception: IOException) {
            Log.e(tag, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(tag, "Unable to parse JSON file.", exception)
        }
    }

    private fun addBannerAdsToDataSet(context: Context) {
        var i = 0
        while (i <= listItems.size) {
            // Prepare the add
            val adView = prepareAdd(context)
            // On every span of item duration, We add the add
            listItems.add(i, adView)
            i += ITEMS_PER_AD
        }
    }

    private fun loadBannerAds() {
        loadBannerAd(0)
    }

    /**
     * ******************************** Main Functions **********************************
     */


    /**
     * ******************************** Utilities **********************************
     */
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
                    tag,
                    "The previous banner ad failed to load with error: $error. Attempting to load the next banner ad in the items list."
                )
                loadBannerAd(index + ITEMS_PER_AD)
            }
        }

        item.loadAd(AdRequest.Builder().build())
    }

    private fun prepareAdd(context: Context): AdView {
        val adView = AdView(context)
        adView.setAdSize(
            AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(
                context,
                AdSize.FULL_WIDTH
            )
        )
        adView.adUnitId = AD_UNIT_ID
        return adView
    }
    /**
     * ******************************** Utilities **********************************
     */


    companion object {
        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
        const val ITEMS_PER_AD = 8
        const val TEST_DEVICE_HASHED_ID = "ABCDEF012345"
    }

}