package com.tomtom.tom.domain.boundaries

import com.tomtom.tom.domain.model.MoviesResponse
import io.reactivex.Single

interface Interactor {

    interface Presentation {
        fun onMoviesPageDownloaded(response: MoviesResponse)
        fun onMoviesPageDownloadFailed(error:Throwable)
    }

    interface Backend {
        fun downloadMovies(api_key:String, page:String = "1"):Single<MoviesResponse>
    }

}