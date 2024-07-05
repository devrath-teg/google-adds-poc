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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.istudio.googleads.modules.banner_adds.BannerScreen
import com.istudio.googleads.modules.interstitial_adds.InterstitialScreen
import com.istudio.googleads.modules.native_adds.NativeAddsScreen
import com.istudio.googleads.modules.native_adds.demos.NativeAddDemo1Screen
import com.istudio.googleads.modules.native_adds.demos.NativeAddDemo2Screen
import com.istudio.googleads.modules.native_adds.selection.NativeAddsSelectionScreen
import com.istudio.googleads.navigation.BannerAdds
import com.istudio.googleads.navigation.NativeAddDemo1
import com.istudio.googleads.navigation.NativeAddDemo2
import com.istudio.googleads.navigation.NativeAdds
import com.istudio.googleads.navigation.NativeAddsSelection
import com.istudio.googleads.navigation.SelectionCategory
import com.istudio.googleads.navigation.SelectionScreen
import com.istudio.googleads.ui.theme.GoogleAdsTheme

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
                    SelectionCategory.BannerAdds -> navController.navigate(BannerAdds)
                    SelectionCategory.InterstitialAdds -> {
                        val intent = Intent(cxt, InterstitialScreen::class.java)
                        cxt.startActivity(intent)
                    }
                    SelectionCategory.NativeAdds -> navController.navigate(NativeAddsSelection)
                    else -> {

                    }
                }
            })
        }
        // --------> Main Selection Screen


        // --------> Main Screen
        composable<BannerAdds> {
            BannerScreen(displayMetrics = displayMetrics)
        }
        composable<NativeAdds> {
            NativeAddsScreen()
        }
        composable<NativeAddsSelection> {
            NativeAddsSelectionScreen(onClickButtonAction = { action ->
                when(action){
                    SelectionCategory.NativeAddDemo1 ->  navController.navigate(NativeAddDemo1)
                    SelectionCategory.NativeAddDemo2 -> navController.navigate(NativeAddDemo2)
                    else -> {}
                }
            })
        }
        // --------> Main Screen


        // --------> Native Add Demo Screens
        composable<NativeAddDemo1> {
            NativeAddDemo1Screen()
        }
        composable<NativeAddDemo2> {
            NativeAddDemo2Screen()
        }
        // --------> Native Add Demo Screens

    }
}