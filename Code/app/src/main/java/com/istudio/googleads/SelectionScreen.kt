package com.istudio.googleads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.istudio.googleads.navigation.SelectionCategory
import com.istudio.googleads.ui.utils.SelectionRow

@Composable
fun SelectionScreen(
    modifier: Modifier = Modifier,
    onClickButtonAction: (selection: SelectionCategory) -> Unit
) {
    val context = LocalContext.current
    val rowSpacing = Modifier.height(16.dp)
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = rowSpacing)
        SelectionRow(
            drawable = R.drawable.banner_img,
            selectionName = "Banner Adds",
            buttonClick = { onClickButtonAction.invoke(SelectionCategory.BannerAdds) }
        )
        Spacer(modifier = rowSpacing)
        SelectionRow(
            drawable = R.drawable.interstitial_img,
            selectionName = "Interstitial Adds",
            buttonClick = { onClickButtonAction.invoke(SelectionCategory.InterstitialAdds) }
        )
        Spacer(modifier = rowSpacing)
        SelectionRow(
            drawable = R.drawable.native_img,
            selectionName = "Native Adds",
            buttonClick = { onClickButtonAction.invoke(SelectionCategory.NativeAdds) }
        )
        Spacer(modifier = rowSpacing)
        /*SelectionRow(
            drawable = R.drawable.rewarded_img,
            selectionName = "Rewarded Adds",
            buttonClick = { onClickButtonAction.invoke(SelectionCategory.RewardedAdds) }
        )
        Spacer(modifier = rowSpacing)
        SelectionRow(
            drawable = R.drawable.interstitial_reward_img,
            selectionName = "Rewarded Interstitial Adds",
            buttonClick = { onClickButtonAction.invoke(SelectionCategory.RewardedInterstitialAdds) }
        )
        Spacer(modifier = rowSpacing)
        SelectionRow(
            drawable = R.drawable.app_open_img,
            selectionName = "App Open Adds",
            buttonClick = { onClickButtonAction.invoke(SelectionCategory.AppOpenAdds) }
        )*/
    }
}