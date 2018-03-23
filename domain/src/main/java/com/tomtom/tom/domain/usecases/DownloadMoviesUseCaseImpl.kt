package com.tomtom.tom.domain.usecases

import com.tomtom.tom.domain.boundaries.DownloadMoviesUseCase
import com.tomtom.tom.domain.boundaries.Interactor
import java.util.*
import java.util.concurrent.TimeUnit

class DownloadMoviesUseCaseImpl : DownloadMoviesUseCase {

    override fun run(api_key: String, page: Int, backendInteractor: Interactor.Backend, presentationInteractor: Interactor.Presentation) {
        val nextPage: String = (page + 1).toString()
        val randomDelay = Random().nextInt(1000).toLong() + 500
        backendInteractor.downloadMovies(api_key, nextPage)
                .doOnError   { presentationInteractor.onMoviesPageDownloadFailed(it) }
                .doOnSuccess { presentationInteractor.onMoviesPageDownloaded(it)     }
                .delay(randomDelay, TimeUnit.MILLISECONDS)
                .subscribe()
    }
}