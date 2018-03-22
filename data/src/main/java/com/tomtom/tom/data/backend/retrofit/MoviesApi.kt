package com.tomtom.tom.data.backend.retrofit

import com.tomtom.tom.domain.model.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("tv/popular")
    fun getPopular(
            @Query("api_key") api_key: String,
            @Query("page") page: String
    ): Single<MoviesResponse>
}