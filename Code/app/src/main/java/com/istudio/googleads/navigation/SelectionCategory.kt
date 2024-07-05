package com.istudio.googleads.navigation

sealed interface SelectionCategory {
    data object BannerAdds : SelectionCategory
    data object InterstitialAdds : SelectionCategory
    data object NativeAdds : SelectionCategory
    data object RewardedAdds : SelectionCategory
    data object RewardedInterstitialAdds : SelectionCategory
    data object AppOpenAdds : SelectionCategory
    // Native Add Selection
    data object NativeAddDemo1 : SelectionCategory
    data object NativeAddDemo2 : SelectionCategory
}