package com.tomtom.tom.tvshowslist.ui.main

import android.util.Log
import com.tomtom.tom.data.backend.BackendHelper
import com.tomtom.tom.data.backend.retrofit.RetrofitHelper
import com.tomtom.tom.domain.boundaries.DownloadMoviesUseCase
import com.tomtom.tom.domain.boundaries.Interactor
import com.tomtom.tom.domain.model.MoviesResponse
import com.tomtom.tom.domain.usecases.DownloadMoviesUseCaseImpl
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.base.BasePresenter


class MainActivityPresenterImpl(private val mainActivity: MainActivity?) : BasePresenter(), MainActivityContract.Presenter, Interactor.Presentation {
    override fun onMoviesDownloaded(response: MoviesResponse) {
        Log.d(tag, "Downloaded ${response.results.size}")
        view?.onDataUpdate()
    }

    val tag = this.javaClass.simpleName
    val view: MainActivityContract.View? = mainActivity

    val downloadMoviesUseCase: DownloadMoviesUseCase = DownloadMoviesUseCaseImpl()

    override fun onResume() {
        Log.d(tag, "Activity triggered onResume()")


    }

    override fun onCreate() {
        Log.d(tag, "Activity triggered onPause()")

        /*
        OKAY, OKAY, OKAY
        I know that hardcoding a secret string is totally illegal.
        I do it as an exception for the sake of a test work
        */

        val api_key = context.resources.getString(R.string.api_key)

        val backendInteractor:Interactor.Backend = BackendHelper()
        val presenter = this

        downloadMoviesUseCase.run(api_key,"1", backendInteractor, presenter)

    }

    override fun onPause() {
        Log.d(tag, "Activity triggered onPause()")
    }

    override fun onDestroy() {
        Log.d(tag, "Activity triggered onDestroy()")
    }

    override fun onStop() {
        Log.d(tag, "Activity triggered onStop()")
    }
}
