package com.istudio.googleads.ui.utils

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.istudio.googleads.R

//SelectionRow

@Composable
fun SelectionRow(
    modifier: Modifier = Modifier,
    drawable: Int,
    selectionName: String,
    buttonClick:() -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SvgImage(drawable)
        AppButton(text = selectionName, onClick = buttonClick)
    }
}

@Composable
private fun SvgImage(drawable: Int) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(drawable)
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.size(128.dp)
    )
}

@Preview
@Composable
private fun CurrentComposablePreview() {
    SelectionRow(
        drawable = R.drawable.banner_img,
        selectionName = "Banner Adds"
    ){

    }
}