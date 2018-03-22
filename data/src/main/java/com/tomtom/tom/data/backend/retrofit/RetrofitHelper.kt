package com.tomtom.tom.data.backend.retrofit

import com.tomtom.tom.domain.model.MoviesResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
    private var moviesApi: MoviesApi
    private val BASE_URL = "https://api.themoviedb.org/3/"

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    fun getPopularMovies(api_key: String, page: String): Single<MoviesResponse> = moviesApi.getPopular(api_key, page)
}