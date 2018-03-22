package com.tomtom.tom.domain.model

class MoviesResponse (
        val page:Int,
        val total_pages:Int,
        val results: List<Movie>
)

class Movie (
        val original_name:String,
        val poster_path:String,
        val backdrop_path:String,
        val overview:String,
        val vote_average:Double
)