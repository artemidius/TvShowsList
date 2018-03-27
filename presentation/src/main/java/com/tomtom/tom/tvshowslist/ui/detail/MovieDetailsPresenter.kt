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
    private val downloadSimilarUseCase: DownloadSimilarUseCase = DownloadSimilarUseCaseImpl()
    private val backendInteractor: Interactor.Backend = BackendHelper()
    private val presenter = this
    private var movieId: String? = null
    private var isLoading = false
    private var fragmentIsActive = false

    companion object {
        var currentPage: Int = 0
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
        downloadRetryCount = 0
        currentPage = response.page
        moviesList.addAll(response.results)
        updateUI()
    }

    private fun updateUI() {
        if (fragmentIsActive) {
            detailFragment.activity.runOnUiThread {
                detailFragment.dispatcher.showLoadigProgress(false)
                view?.onDataUpdate(moviesList)
            }
        }
    }

    override fun onMoviesPageDownloadFailed(error: Throwable) {
        isLoading = false
        if (downloadRetryCount <= maximumDownloadAttemptNumber) {
            downloadRetryCount++
            downloadSimilarUseCase.run(apiKey, currentPage, movieId!!, backendInteractor, presenter)
        } else {
            detailFragment.dispatcher.onConnectionFailed(this)
            updateUI()
        }
    }

    override fun onItemClick(position: Int) {
        onPagerSnap(position)
        view?.scrollPagerToPosition(position)
    }

    override fun onPagerSnap(position: Int) {
        detailFragment.activity.title = moviesList[position].original_name
        if (position > moviesList.size - 2 && !isLoading) downloadNextPage()
    }

    override fun downloadNextPage() {
        isLoading = true
        detailFragment.dispatcher.showLoadigProgress(true)
        downloadSimilarUseCase.run(apiKey, currentPage, movieId!!, backendInteractor, presenter)
    }

    override fun onViewCreated() {
        fragmentIsActive = true
        detailFragment.activity.title = moviesList[0].original_name
        downloadNextPage()
    }

    override fun getBaseUrl(): String = baseUrl

    override fun onStop() {
        fragmentIsActive = false
    }

    override fun onCreate() {
        Log.d(tag, "Fragment triggered onResume()")
    }

    override fun onResume() {
        Log.d(tag, "Fragment triggered onResume()")
    }

    override fun onPause() {
        Log.d(tag, "Fragment triggered onPause()")
    }

    override fun onDestroy() {
        Log.d(tag, "Fragment triggered onDestroy()")
    }

}

