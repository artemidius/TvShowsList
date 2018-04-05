package com.tomtom.tom.domain.usecases

import com.tomtom.tom.domain.boundaries.DownloadSimilarUseCase
import com.tomtom.tom.domain.boundaries.Interactor
import com.tomtom.tom.domain.model.MoviesResponse
import java.util.*
import java.util.concurrent.TimeUnit

class DownloadSimilarUseCaseImpl : DownloadSimilarUseCase {

    override fun run(api_key: String, page: Int, movieId:String, backendInteractor: Interactor.Backend, presentationInteractor: Interactor.Presentation) {
        val nextPage: String = (page + 1).toString()
        val randomDelay = Random().nextInt(1000).toLong() + 500
        backendInteractor.downloadSimilar(movieId, api_key, nextPage)
                .timeout(7000, TimeUnit.MILLISECONDS)
                .delay(randomDelay, TimeUnit.MILLISECONDS)
                .retry(3)
                .subscribe { response: MoviesResponse?, error: Throwable? ->
                    when {
                        error != null -> presentationInteractor.onMoviesPageDownloadFailed(error)
                        response == null -> presentationInteractor.onMoviesPageDownloadFailed(Throwable("Response was null"))
                        else -> presentationInteractor.onMoviesPageDownloaded(response)
                    }
                }
    }
}