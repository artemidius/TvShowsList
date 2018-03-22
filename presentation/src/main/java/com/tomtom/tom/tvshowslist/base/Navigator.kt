package com.tomtom.tom.tvshowslist.base


interface Navigator {
    companion object {
        val LIST_FRAGMENT = "list"
        val DETAILS_FRAGMENT = "details"
    }

    fun navigateTo(fragment:String)


}