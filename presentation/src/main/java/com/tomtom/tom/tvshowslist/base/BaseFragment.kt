package com.tomtom.tom.tvshowslist.base


import android.location.Location
import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {
    lateinit var navigator: Navigator

    var userLocation : Location = Location("")

    init {
        userLocation.latitude = 0.0
        userLocation.longitude = 0.0
    }

    var userId : String? = null

    fun setInterface(nav: Navigator){
        navigator = nav
    }

}
