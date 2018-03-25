package com.tomtom.tom.tvshowslist.ui.detail

import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.base.ActivityLifeCyclePresenter


interface MovieDetailsContract {
    interface View {
        fun onDataUpdate(movies:List<Movie>)
    }

    interface Presenter : ActivityLifeCyclePresenter {
        fun onViewCreated()
        fun downloadNextPage()
        fun onItemClick(movie: Movie?)
        fun initializeDataset(movie: Movie)
        fun getBaseUrl():String
    }

}