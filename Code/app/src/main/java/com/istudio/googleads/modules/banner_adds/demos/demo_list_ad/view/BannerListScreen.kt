package com.istudio.googleads.modules.banner_adds.demos.demo_list_ad.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BannerListScreen(
    modifier: Modifier = Modifier,
    vm: BannerListScreenViewModel = viewModel()
) {

    val context = LocalContext.current
    val state = vm.nativeAdState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.initiate(context)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        state.value.data?.let {
            ListComposable(it)
        }
    }
}

