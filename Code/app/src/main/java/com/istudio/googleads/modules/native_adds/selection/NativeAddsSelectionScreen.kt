package com.istudio.googleads.modules.native_adds.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.istudio.googleads.navigation.SelectionCategory
import com.istudio.googleads.ui.utils.AppButton

@Composable
fun NativeAddsSelectionScreen(
    modifier: Modifier = Modifier,
    onClickButtonAction: (selection: SelectionCategory) -> Unit
) {
    val rowSpacing = Modifier.height(16.dp)
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = rowSpacing)
        AppButton(text = "Simple Native Add Demo", onClick = {
           onClickButtonAction.invoke(SelectionCategory.SimpleNativeAdScreen)
        })
        Spacer(modifier = rowSpacing)
        AppButton(text = "Multiple Native Adds Demo", onClick = {
            onClickButtonAction.invoke(SelectionCategory.MultipleNativeAdScreen)
        })
    }
}