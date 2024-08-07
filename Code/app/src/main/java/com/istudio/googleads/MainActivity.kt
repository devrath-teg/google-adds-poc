package com.istudio.googleads

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.istudio.googleads.modules.banner_adds.demos.demo_list_ad.view.BannerListScreen
import com.istudio.googleads.modules.banner_adds.demos.demo_simple_ad.BannerSimpleScreen
import com.istudio.googleads.modules.banner_adds.selection.BannerAddsSelectionScreen
import com.istudio.googleads.modules.interstitial_adds.InterstitialScreen
import com.istudio.googleads.modules.native_adds.demos.demo_multiple_ads.MultipleNativeAdScreen
import com.istudio.googleads.modules.native_adds.demos.demo_simple_ad.view.SimpleNativeAdScreen
import com.istudio.googleads.modules.native_adds.selection.NativeAddsSelectionScreen
import com.istudio.googleads.navigation.SimpleBannerBannerAdds
import com.istudio.googleads.navigation.BannerAddsSelectionScreen
import com.istudio.googleads.navigation.BannerListAdds
import com.istudio.googleads.navigation.SimpleNativeAdScreen
import com.istudio.googleads.navigation.MultipleNativeAdScreen
import com.istudio.googleads.navigation.NativeAddsSelectionScreen
import com.istudio.googleads.navigation.SelectionCategory
import com.istudio.googleads.navigation.SelectionScreen
import com.istudio.googleads.ui.theme.GoogleAdsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val density = getDisplayMetrics()

        setContent {
            GoogleAdsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrentScreen(
                        modifier = Modifier.padding(innerPadding),
                        displayMetrics = density
                    )
                }
            }
        }
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
            val bounds = windowMetrics.bounds
            displayMetrics.widthPixels = bounds.width()
            displayMetrics.heightPixels = bounds.height()
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        return displayMetrics
    }
}



@Composable
fun CurrentScreen(
    modifier: Modifier = Modifier,
    displayMetrics: DisplayMetrics
) {
    val cxt = LocalContext.current
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SelectionScreen
    ) {
        // --------> Main Selection Screen
        composable<SelectionScreen> {
            SelectionScreen(onClickButtonAction = { action ->
                when(action){
                    SelectionCategory.BannerAdds -> navController.navigate(BannerAddsSelectionScreen)
                    SelectionCategory.InterstitialAdds -> {
                        val intent = Intent(cxt, InterstitialScreen::class.java)
                        cxt.startActivity(intent)
                    }
                    SelectionCategory.NativeAdds -> navController.navigate(NativeAddsSelectionScreen)
                    else -> {

                    }
                }
            })
        }
        // --------> Main Selection Screen


        // --------> Main Screen
        composable<BannerAddsSelectionScreen> {
            BannerAddsSelectionScreen(onClickButtonAction = { action ->
                when(action){
                    SelectionCategory.SimpleBannerAdScreen ->  navController.navigate(SimpleBannerBannerAdds)
                    SelectionCategory.ListBannerAdScreen -> navController.navigate(BannerListAdds)
                    else -> {}
                }
            })
        }
        // --------> Native Add Demo Screens
        composable<SimpleBannerBannerAdds> {
            BannerSimpleScreen(displayMetrics = displayMetrics)
        }

        composable<BannerListAdds> {
            BannerListScreen()
        }
        // --------> Native Add Demo Screens



        composable<NativeAddsSelectionScreen> {
            NativeAddsSelectionScreen(onClickButtonAction = { action ->
                when(action){
                    SelectionCategory.SimpleNativeAdScreen ->  navController.navigate(SimpleNativeAdScreen)
                    SelectionCategory.MultipleNativeAdScreen -> navController.navigate(MultipleNativeAdScreen)
                    else -> {}
                }
            })
        }
        // --------> Main Screen

        // --------> Native Add Demo Screens
        composable<SimpleNativeAdScreen> {
            SimpleNativeAdScreen()
        }
        composable<MultipleNativeAdScreen> {
            MultipleNativeAdScreen()
        }
        // --------> Native Add Demo Screens

    }
}