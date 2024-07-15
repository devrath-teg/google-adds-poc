package com.istudio.googleads.modules.banner_adds.demos.demo_list_ad

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowMetrics
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

class BannerListActivity : ComponentActivity() {
    private val recyclerViewItems: MutableList<Any> = mutableStateListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleAdsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RecyclerViewItemsList(recyclerViewItems)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            addMenuItemsFromJson()
            addBannerAds()
            loadBannerAds()
        }
    }

    private val adWidth: Int
        get() {
            val displayMetrics = resources.displayMetrics
            val adWidthPixels =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val windowMetrics: WindowMetrics = this.windowManager.currentWindowMetrics
                    windowMetrics.bounds.width()
                } else {
                    displayMetrics.widthPixels
                }
            val density = displayMetrics.density
            return (adWidthPixels / density).toInt()
        }

    private fun addBannerAds() {
        var i = 0
        while (i <= recyclerViewItems.size) {
            val adView = AdView(this@BannerListActivity)
            adView.setAdSize(AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(this, adWidth))
            adView.adUnitId = AD_UNIT_ID
            recyclerViewItems.add(i, adView)
            i += ITEMS_PER_AD
        }
    }

    private fun loadBannerAds() {
        loadBannerAd(0)
    }

    private fun loadBannerAd(index: Int) {
        if (index >= recyclerViewItems.size) {
            return
        }
        val item = recyclerViewItems[index] as? AdView
            ?: throw ClassCastException("Expected item at index $index to be a banner ad.")

        item.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                loadBannerAd(index + ITEMS_PER_AD)
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error = String.format(
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

    private fun addMenuItemsFromJson() {
        try {
            val jsonDataString = readJsonDataFromFile()
            val menuItemsJsonArray = JSONArray(jsonDataString)
            for (i in 0 until menuItemsJsonArray.length()) {
                val menuItemObject = menuItemsJsonArray.getJSONObject(i)
                val menuItem = MenuItem(
                    menuItemObject.getString("name"),
                    menuItemObject.getString("description"),
                    menuItemObject.getString("price"),
                    menuItemObject.getString("category"),
                    menuItemObject.getString("photo")
                )
                recyclerViewItems.add(menuItem)
            }
        } catch (exception: IOException) {
            Log.e(BannerListActivity::class.java.name, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(BannerListActivity::class.java.name, "Unable to parse JSON file.", exception)
        }
    }

    @Throws(IOException::class)
    private fun readJsonDataFromFile(): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            inputStream = resources.openRawResource(R.raw.menu_items_json)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            bufferedReader.forEachLine { builder.append(it) }
        } finally {
            inputStream?.close()
        }
        return builder.toString()
    }

    companion object {
        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
        const val ITEMS_PER_AD = 8
        const val TEST_DEVICE_HASHED_ID = "ABCDEF012345"
    }
}

@Composable
fun RecyclerViewItemsList(items: List<Any>) {
    LazyColumn {
        items(items) { item ->
            when (item) {
                is MenuItem -> MenuItemView(menuItem = item)
                is AdView -> AdViewContainer(adView = item)
            }
        }
    }
}

@Composable
fun MenuItemView(menuItem: MenuItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            ImageView(menuItem.imageName)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = menuItem.name, style = MaterialTheme.typography.titleLarge)
                Text(text = menuItem.price, style = MaterialTheme.typography.titleMedium)
                Text(text = menuItem.category, style = MaterialTheme.typography.titleSmall)
                Text(text = menuItem.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun AdViewContainer(adView: AdView) {
    AndroidView(
        factory = { context ->
            adView
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
fun ImageView(imageName: String) {
    val context = LocalContext.current
    val imageResId = remember { context.resources.getIdentifier(imageName, "drawable", context.packageName) }
    androidx.compose.foundation.Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

class MenuItem(
    val name: String,
    val description: String,
    val price: String,
    val category: String,
    val imageName: String,
)


