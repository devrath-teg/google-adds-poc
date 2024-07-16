package com.istudio.googleads.modules.banner_adds.demos.demo_list_ad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdView

@Composable
fun ListComposable(items: List<Any>) {
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
        factory = { context -> adView },
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