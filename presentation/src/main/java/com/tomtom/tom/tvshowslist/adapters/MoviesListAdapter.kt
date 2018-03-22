package com.tomtom.tom.tvshowslist.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.tomtom.tom.data.backend.retrofit.RetrofitHelper
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.ui.list.MoviesListContract
import kotlinx.android.synthetic.main.item_show.view.*

class MoviesListAdapter(var movies: List<Movie>, val presenter:MoviesListContract.Presenter) : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    lateinit var context: Context
    val baseUrl = "https://image.tmdb.org/t/p/w200"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return ViewHolder(view, presenter)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.movie = movies[position]

        with(movies[position]) {
            if (holder != null) {
                Picasso.with(context)
                        .load(baseUrl + poster_path)
                        .into(holder.textView.show_image)

                holder.textView.show_title.text = original_name
                holder.textView.show_vote.text = vote_average.toString()
                holder.textView.dark_frame.visibility = View.VISIBLE
                holder.textView.info_plate.visibility = View.VISIBLE
                holder.textView.show_vote.text = vote_average.toString()

            }
        }
    }


    override fun getItemCount(): Int = movies.size

    class ViewHolder(val textView: View, val presenter:MoviesListContract.Presenter) : RecyclerView.ViewHolder(textView), View.OnClickListener {

        var movie:Movie? = null

        init {
            textView.setOnClickListener (this)
        }

        override fun onClick(p0: View?) {
            presenter.onItemClick(movie)
        }
    }

    fun updateList(newList: List<Movie>) {
        movies = newList
        notifyDataSetChanged()
    }

}