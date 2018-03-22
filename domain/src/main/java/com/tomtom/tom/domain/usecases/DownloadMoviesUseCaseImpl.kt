package com.tomtom.tom.domain.usecases

import com.tomtom.tom.domain.boundaries.DownloadMoviesUseCase
import com.tomtom.tom.domain.boundaries.Interactor

class DownloadMoviesUseCaseImpl : DownloadMoviesUseCase {

    override fun run(api_key:String, page: String, backendInteractor: Interactor.Backend, presentationInteractor: Interactor.Presentation) {

        backendInteractor.downloadMovies(api_key, page)?.map {
            presentationInteractor.onMoviesDownloaded(it)
        }?.subscribe()
    }
}