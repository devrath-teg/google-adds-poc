package com.istudio.googleads.modules.native_adds.demos.demo_simple_ad.utils

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.android.gms.ads.MediaContent
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.istudio.googleads.R

@Composable
fun NativeAdViewCompose(
    modifier: Modifier = Modifier,
    content: @Composable (nativeAdView: NativeAdView) -> Unit
) = AndroidView(modifier = modifier, factory = { context ->
    // Here we add the AD-View class that is based of XML
    NativeAdView(context)
}, update = { composableContent ->
    // Create the compose View that can host the xml content
    val composeView = ComposeView(composableContent.context)
    // Remove all the existing views if present
    composableContent.removeAllViews()
    // composableContent the newly created compose view
    composableContent.addView(composeView)
    // Set the content of the compose view
    composeView.setContent { content(composableContent) }
})

@Composable
fun NativeAdViewLayout(
    modifier: Modifier = Modifier,
    getView: (ComposeView) -> Unit, content: @Composable () -> Unit
) = AndroidView(factory = { context ->
    ComposeView(context)
}, update = { composableContent ->
    composableContent.setContent(content)
    getView(composableContent)
})

@Composable
fun NativeAdImageView(
    modifier: Modifier = Modifier,
    drawable: Drawable?,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) = Image(
    painter = rememberDrawablePainter(drawable = drawable),
    contentDescription = contentDescription,
    modifier = modifier,
    colorFilter = colorFilter,
    contentScale = contentScale,
    alignment = alignment,
    alpha = alpha
)

@Composable
fun NativeAdMediaView(
    modifier: Modifier = Modifier,
    mediaContent: MediaContent,
    nativeAd: NativeAd,
    scaleType: ImageView.ScaleType
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.layout_native_ad, null)
            view
        }){
            val mediaView = it.findViewById<MediaView>(R.id.mediaView)
            val nativeAdView = it.findViewById<NativeAdView>(R.id.nativeAdView)
            nativeAdView.mediaView =  mediaView
            nativeAdView.mediaView?.mediaContent = mediaContent
            nativeAdView.mediaView?.setImageScaleType(scaleType)
            nativeAdView.setNativeAd(nativeAd)
        }
    }
}