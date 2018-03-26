package com.tomtom.tom.tvshowslist.ui.detail

import android.util.Log
import com.tomtom.tom.data.backend.BackendHelper
import com.tomtom.tom.domain.boundaries.DownloadSimilarUseCase
import com.tomtom.tom.domain.boundaries.Interactor
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.domain.model.MoviesResponse
import com.tomtom.tom.domain.usecases.DownloadSimilarUseCaseImpl
import com.tomtom.tom.tvshowslist.application.TvShowsListApplication.Companion.apiKey
import com.tomtom.tom.tvshowslist.application.TvShowsListApplication.Companion.baseUrl
import com.tomtom.tom.tvshowslist.base.BasePresenter


class MovieDetailsPresenter(private val detailFragment: DetailFragment) : BasePresenter(), MovieDetailsContract.Presenter, Interactor.Presentation {

    private val tag = this.javaClass.simpleName
    private val view: MovieDetailsContract.View? = detailFragment
    private val downloadSimilarUseCase:DownloadSimilarUseCase = DownloadSimilarUseCaseImpl()
    private val backendInteractor:Interactor.Backend = BackendHelper()
    private val presenter = this
    private var movieId:String? = null
    private var isLoading = false

    companion object {
        var currentPage:Int = 0
        var moviesList = mutableListOf<Movie>()
        var downloadRetryCount = 0
        const val maximumDownloadAttemptNumber = 3
    }

    override fun initializeDataset(movie: Movie) {
        moviesList.clear()
        moviesList.add(movie)
        movieId = movie.id.toString()
    }

    override fun onMoviesPageDownloaded(response: MoviesResponse) {
        isLoading = false
        detailFragment.dispatcher.showLoadigProgress(false)
        downloadRetryCount = 0
        currentPage = response.page
        moviesList.addAll(response.results)
        view?.onDataUpdate(moviesList)
    }

    override fun onMoviesPageDownloadFailed(error: Throwable) {
        isLoading = false
        if (downloadRetryCount <= maximumDownloadAttemptNumber) {
            downloadRetryCount++
            downloadSimilarUseCase.run(apiKey, currentPage, movieId!!, backendInteractor, presenter)
        } else {
            detailFragment.dispatcher.showLoadigProgress(false)
            detailFragment.dispatcher.onConnectionFailed(this)
            view?.onDataUpdate(moviesList)
        }
    }

    override fun onItemClick(position: Int) {
        onPagerSnap(position)
        view?.scrollPagerToPosition(position)
    }

    override fun onPagerSnap(position: Int) {
        Log.i(tag, "List size: ${moviesList.size}, position: $position")
        detailFragment.activity.title = moviesList[position].original_name
        if(position > moviesList.size - 2 && !isLoading) downloadNextPage()
    }

    override fun downloadNextPage()  {
        isLoading = true
        detailFragment.dispatcher.showLoadigProgress(true)
        downloadSimilarUseCase.run(apiKey, currentPage, movieId!!, backendInteractor, presenter)
    }

    override fun onViewCreated()  {
        detailFragment.activity.title = moviesList[0].original_name
        downloadNextPage()
    }

    override fun getBaseUrl(): String = baseUrl

    override fun onCreate()       {  Log.d(tag, "Fragment triggered onResume()")    }
    override fun onResume()       {  Log.d(tag, "Fragment triggered onResume()")    }
    override fun onPause()        {  Log.d(tag, "Fragment triggered onPause()")     }
    override fun onDestroy()      {  Log.d(tag, "Fragment triggered onDestroy()")   }
    override fun onStop()         {  Log.d(tag, "Fragment triggered onStop()")      }
}

