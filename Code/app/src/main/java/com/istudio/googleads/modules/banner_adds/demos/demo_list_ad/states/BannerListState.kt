package com.istudio.googleads.modules.banner_adds.demos.demo_list_ad.states

data class BannerListState(
    val isLoading: Boolean = true,
    val isSuccess :Boolean =  false,
    val errorMessage :String? = null,
    val data : MutableList<Any>? = null
)
