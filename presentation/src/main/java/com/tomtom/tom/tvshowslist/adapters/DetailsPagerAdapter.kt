package com.tomtom.tom.tvshowslist.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.ui.detail.MovieDetailsContract
import kotlinx.android.synthetic.main.item_details_pager.view.*

class DetailsPagerAdapter(var movies: List<Movie>, val presenter:MovieDetailsContract.Presenter) : RecyclerView.Adapter<DetailsPagerAdapter.ViewHolder>() {

    val tag = this.javaClass.simpleName
    lateinit var context: Context
    val baseUrl = presenter.getBaseUrl()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_details_pager, parent, false)
        return ViewHolder(view, presenter)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        Log.d(tag, "obBindViewHolder")
        holder?.movie = movies[position]

        with(movies[position]) {
            if (holder != null) {
                System.out.println(baseUrl + poster_path)
                Picasso.with(context)
                        .load(baseUrl + poster_path)
                        .placeholder(R.drawable.ic_movie)
                        .into(holder.textView.detail_image)

                holder.textView.detail_title.text = original_name
                holder.textView.detail_overview.text = overview

            }
        }
    }


    override fun getItemCount(): Int = movies.size

    class ViewHolder(val textView: View, val presenter:MovieDetailsContract.Presenter) : RecyclerView.ViewHolder(textView), View.OnClickListener {

        var movie:Movie? = null

        init {
            textView.setOnClickListener (this)
        }

        override fun onClick(p0: View?) {
        }
    }

    fun updateList(newList: List<Movie>) {
        Log.d(tag, "Details adapter data udpating with ${newList.size} shows")
        movies = newList
        notifyDataSetChanged()
    }

}