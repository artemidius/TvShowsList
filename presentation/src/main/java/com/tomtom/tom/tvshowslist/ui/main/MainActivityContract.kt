package com.tomtom.tom.tvshowslist.ui.main

import com.tomtom.tom.tvshowslist.base.ActivityLifeCyclePresenter


interface MainActivityContract {
    interface View {
        fun onDataUpdate()
    }

    interface Presenter : ActivityLifeCyclePresenter

}