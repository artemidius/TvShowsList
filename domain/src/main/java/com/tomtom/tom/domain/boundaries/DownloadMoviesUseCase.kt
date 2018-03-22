package com.tomtom.tom.domain.boundaries

interface DownloadMoviesUseCase {
    fun run(
            api_key: String,
            page: Int,
            backendInteractor: Interactor.Backend,
            presentationInteractor: Interactor.Presentation
    )
}