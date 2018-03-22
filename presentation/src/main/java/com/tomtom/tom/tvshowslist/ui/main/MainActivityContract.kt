package com.tomtom.tom.tvshowslist.ui.main

import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.base.ActivityLifeCyclePresenter


interface MainActivityContract {
    interface View {
        fun onDataUpdate(movies:List<Movie>)
    }

    interface Presenter : ActivityLifeCyclePresenter {
        fun downloadNextPage()
    }

}