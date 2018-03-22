package com.tomtom.tom.tvshowslist.base


import android.location.Location
import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {
    lateinit var navigator: Navigator

    fun setInterface(nav: Navigator){
        navigator = nav
    }

}
