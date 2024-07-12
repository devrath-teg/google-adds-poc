package com.istudio.googleads.modules.native_adds.demos.demo4

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun LiskovAdView(
    modifier: Modifier = Modifier,
    nativeAd: NativeAd?
) {
    val context = LocalContext.current
    if (nativeAd != null) {
        NativeAdViewCompose { nativeAdView ->
            nativeAdView.setNativeAd(nativeAd)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                // <-------------------- ROW-1 -------------------------------------------->
                NativeAdViewRowOne(nativeAdView, nativeAd)
                // <-------------------- ROW-1 -------------------------------------------->

                // <-------------------- ROW-2--------------------------------------------->
                NativeAdViewRowTwo(nativeAd)
                // <-------------------- ROW-2--------------------------------------------->

                // <-------------------- ROW-3--------------------------------------------->
                NativeAdViewRowThree(nativeAdView, nativeAd)
                // <-------------------- ROW-3--------------------------------------------->
            }
        }
    }
}

@Composable
private fun NativeAdViewRowOne(
    nativeAdView: NativeAdView,
    nativeAd: NativeAd
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(45.dp)
    ) {

        // <---------------- AD-ICON ------------------------->
        NativeAdViewLayout(getView = {
            nativeAdView.iconView = it
        }
        ) {
            val addIcon = nativeAd.icon?.drawable
            val iconContentDescription = "Icon"
            val imgSize = Modifier.size(45.dp)
            NativeAdImageView(
                drawable = addIcon,
                contentDescription = iconContentDescription,
                modifier = imgSize
            )
        }
        // <---------------- AD-ICON ------------------------->

        // <---------------- AD-HEADLINE --------------------->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textDisplay = nativeAd.headline ?: "-"
            val textSize = 15.sp
            val textColor = Color.Black
            NativeAdViewLayout(getView = {
                nativeAdView.headlineView = it
            }) {
                Text(
                    text = textDisplay,
                    fontSize = textSize,
                    color = textColor
                )
            }
        }
        // <---------------- AD-HEADLINE --------------------->

        // <---------------- AD-TEXT-VISUAL ------------------>
        Row(
            Modifier
                .wrapContentSize()
                .background(Color.Black)
                .clip(RoundedCornerShape(2.dp))
        ) {
            val textDisplay = "Ad"
            val textSize = 15.sp
            val textColor = Color.White
            val padding = Modifier.padding(3.dp)
            Text(
                text = textDisplay, fontSize = textSize,
                color = textColor, modifier = padding
            )
        }
        // <---------------- AD-TEXT-VISUAL ------------------>

    }
}

@Composable
private fun NativeAdViewRowTwo(nativeAd: NativeAd) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(5.dp)
    ) {
        nativeAd.mediaContent?.let { mediaContent ->
            NativeAdMediaView(
                nativeAd = nativeAd,
                mediaContent = mediaContent,
                scaleType = ImageView.ScaleType.FIT_CENTER
            )
        }
    }
}

@Composable
private fun NativeAdViewRowThree(
    nativeAdView: NativeAdView,
    nativeAd: NativeAd
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(top = 5.dp)
    ) {
        NativeAdViewLayout(getView = { nativeAdView.bodyView = it }) {
            Text(text = nativeAd.body ?: "-", color = Color.Black)
        }
    }
}