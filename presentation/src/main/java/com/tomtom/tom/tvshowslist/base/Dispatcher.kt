package com.tomtom.tom.tvshowslist.base

import com.tomtom.tom.domain.model.Movie


interface Dispatcher {
    companion object {
        val LIST_FRAGMENT = "list"
        val DETAILS_FRAGMENT = "details"
    }

    fun navigateTo(fragment:String, movie: Movie? = null)
    fun showLoadigProgress(visible:Boolean)

}