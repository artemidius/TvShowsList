package com.tomtom.tom.domain.model

class MoviesResponse (
        val page:Int,
        val results: List<Movie>
)

class Movie (
        val original_name:String,
        val poster_path:String,
        val overview:String,
        val id:Int,
        val vote_average:Double
)