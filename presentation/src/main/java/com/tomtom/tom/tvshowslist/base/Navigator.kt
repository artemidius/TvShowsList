package com.tomtom.tom.tvshowslist.base


interface Navigator {
    companion object {
        val LIST_FRAGMENT = "list"
        val DETAIL_FRAGMENT = "detail"
    }

    fun navigateTo(fragment:String)


}