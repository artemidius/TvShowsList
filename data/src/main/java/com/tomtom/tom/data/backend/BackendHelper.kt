package com.tomtom.tom.data.backend

import com.tomtom.tom.data.backend.retrofit.RetrofitHelper
import com.tomtom.tom.domain.boundaries.Interactor
import com.tomtom.tom.domain.model.MoviesResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BackendHelper:Interactor.Backend {
    override fun downloadMovies(api_key: String, page: String): Single<MoviesResponse> =
        RetrofitHelper()
                .getPopularMovies(api_key, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
}