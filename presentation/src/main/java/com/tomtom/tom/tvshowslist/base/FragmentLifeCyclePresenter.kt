package com.tomtom.tom.tvshowslist.base

interface FragmentLifeCyclePresenter {
    fun onCreate()
    fun onViewCreated()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}