package com.tomtom.tom.domain.usecases

import com.tomtom.tom.domain.boundaries.DownloadMoviesUseCase
import com.tomtom.tom.domain.boundaries.Interactor

class DownloadMoviesUseCaseImpl : DownloadMoviesUseCase {

    override fun run(api_key: String, page: Int, backendInteractor: Interactor.Backend, presentationInteractor: Interactor.Presentation) {
        val nextPage: String = (page + 1).toString()
        backendInteractor.downloadMovies(api_key, nextPage)
                .doOnError { presentationInteractor.onMoviesPageDownloadFailed(it) }
                .doOnSuccess {
                    Thread.sleep(1000)
                    presentationInteractor.onMoviesPageDownloaded(it)
                }
                .subscribe()
    }
}