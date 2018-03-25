package com.tomtom.tom.tvshowslist.ui.detail

import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.base.ActivityLifeCyclePresenter


interface MovieDetailsContract {
    interface View {
        fun onDataUpdate(movies:List<Movie>)
        fun scrollPagerToPosition(position: Int)

    }

    interface Presenter : ActivityLifeCyclePresenter {
        fun onViewCreated()
        fun downloadNextPage()
        fun onItemClick(position: Int)
        fun onPagerSnap(position: Int)
        fun initializeDataset(movie: Movie)
        fun getBaseUrl():String
    }

}