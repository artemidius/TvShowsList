package com.tomtom.tom.tvshowslist.ui.main

import android.util.Log
import com.tomtom.tom.data.backend.BackendHelper
import com.tomtom.tom.domain.boundaries.DownloadMoviesUseCase
import com.tomtom.tom.domain.boundaries.Interactor
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.domain.model.MoviesResponse
import com.tomtom.tom.domain.usecases.DownloadMoviesUseCaseImpl
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.base.BasePresenter


class MainActivityPresenterImpl(mainActivity: MainActivity?) : BasePresenter(), MainActivityContract.Presenter, Interactor.Presentation {


    private val tag = this.javaClass.simpleName
    private val view: MainActivityContract.View? = mainActivity
    private val downloadMoviesUseCase: DownloadMoviesUseCase = DownloadMoviesUseCaseImpl()
    private val backendInteractor:Interactor.Backend = BackendHelper()
    private val presenter = this

    /*
    OKAY,
    I know that hardcoding a secret string is totally illegal.
    I do it as an exception for the sake of a test work
    */

    private val apiKey = context.resources.getString(R.string.api_key)

    companion object {
        var currentPage:Int = 0
        var moviesList = mutableListOf<Movie>()
    }

    override fun onMoviesPageDownloaded(response: MoviesResponse) {
        currentPage = response.page
        moviesList.addAll(response.results)
        view?.onDataUpdate(moviesList)
    }

    override fun downloadNextPage() = downloadMoviesUseCase.run(apiKey, currentPage, backendInteractor, presenter)

    override fun onCreate() = downloadNextPage()

    override fun onResume()   {  Log.d(tag, "Activity triggered onResume()")    }
    override fun onPause()    {  Log.d(tag, "Activity triggered onPause()")     }
    override fun onDestroy()  {  Log.d(tag, "Activity triggered onDestroy()")   }
    override fun onStop()     {  Log.d(tag, "Activity triggered onStop()")      }
}

