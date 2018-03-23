package com.tomtom.tom.tvshowslist.base

import android.content.Context
import com.tomtom.tom.tvshowslist.application.TvShowsListApplication
import javax.inject.Inject

open class BasePresenter {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var application: TvShowsListApplication

    val TAG = this.javaClass.simpleName

    init {
        TvShowsListApplication.appComponent.inject(this)
    }
}