package com.tomtom.tom.tvshowslist.ui.list

import android.util.Log
import com.tomtom.tom.data.backend.BackendHelper
import com.tomtom.tom.domain.boundaries.DownloadMoviesUseCase
import com.tomtom.tom.domain.boundaries.Interactor
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.domain.model.MoviesResponse
import com.tomtom.tom.domain.usecases.DownloadMoviesUseCaseImpl
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.application.TvShowsListApplication.Companion.apiKey
import com.tomtom.tom.tvshowslist.base.BasePresenter
import com.tomtom.tom.tvshowslist.base.Dispatcher


class MoviesListPresenter(val listFragment: MoviesListFragment) : BasePresenter(), MoviesListContract.Presenter, Interactor.Presentation {

    private val tag = this.javaClass.simpleName
    private val view: MoviesListContract.View? = listFragment
    private val downloadMoviesUseCase: DownloadMoviesUseCase = DownloadMoviesUseCaseImpl()
    private val backendInteractor:Interactor.Backend = BackendHelper()
    private val presenter = this

    companion object {
        var currentPage:Int = 0
        var moviesList = mutableListOf<Movie>()
        var downloadRetryCount = 0
        const val maximumDownloadAttemptNumber = 3
    }

    override fun onMoviesPageDownloaded(response: MoviesResponse) {
        Log.d(tag, "DOWNLOADED: ${response.results.size}")
        downloadRetryCount = 0
        currentPage = response.page
        moviesList.addAll(response.results)
        view?.onDataUpdate(moviesList)
        listFragment.dispatcher.showLoadigProgress(false)
    }

    override fun onMoviesPageDownloadFailed(error: Throwable) {
        Log.d(tag, "Page download failed with error: ${error.message}")
        if (downloadRetryCount <= maximumDownloadAttemptNumber) {
            downloadRetryCount++
            Log.d(tag, "Retry download. Attempt #$downloadRetryCount")
            downloadNextPage()
        } else {
            Log.d(tag, "We tried too many times. Download aborted")
            listFragment.dispatcher.showLoadigProgress(false)
            listFragment.dispatcher.onConnectionFailed(this)
            view?.onDataUpdate(moviesList)

        }
    }

    override fun onItemClick(movie: Movie?) {
        Log.d(tag, movie?.original_name)
        listFragment.dispatcher.showLoadigProgress(false)
        Thread.sleep(500)
        listFragment.dispatcher.navigateTo(Dispatcher.DETAILS_FRAGMENT, movie)
    }

    override fun downloadNextPage()  {
            listFragment.dispatcher.showLoadigProgress(true)
            downloadMoviesUseCase.run(apiKey, currentPage, backendInteractor, presenter)
    }

    override fun onViewCreated()  {
        Log.d(tag, "Fragment triggered onViewCreated()")
        listFragment.activity.title = context.getString(R.string.list_screen_title)
        downloadNextPage()
    }

    override fun onCreate()       {  Log.d(tag, "Fragment triggered onResume()")    }
    override fun onResume()       {  Log.d(tag, "Fragment triggered onResume()")    }
    override fun onPause()        {  Log.d(tag, "Fragment triggered onPause()")     }
    override fun onDestroy()      {  Log.d(tag, "Fragment triggered onDestroy()")   }
    override fun onStop()         {  Log.d(tag, "Fragment triggered onStop()")      }

}

