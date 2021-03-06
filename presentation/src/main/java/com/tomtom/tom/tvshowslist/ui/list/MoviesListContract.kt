package com.tomtom.tom.tvshowslist.ui.list

import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.base.ActivityLifeCyclePresenter


interface MoviesListContract {
    interface View {
        fun onDataUpdate(movies:List<Movie>)
    }

    interface Presenter : ActivityLifeCyclePresenter {
        fun onViewCreated()
        fun onItemClick(movie: Movie?)
    }

}