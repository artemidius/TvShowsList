package com.tomtom.tom.tvshowslist.base

interface ActivityLifeCyclePresenter {
    fun onCreate()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}