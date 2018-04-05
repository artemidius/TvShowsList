package com.tomtom.tom.domain.boundaries

interface DownloadSimilarUseCase {
    fun run(
            api_key: String,
            page: Int,
            movieId:String,
            backendInteractor: Interactor.Backend,
            presentationInteractor: Interactor.Presentation
    )
}