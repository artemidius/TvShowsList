package com.tomtom.tom.tvshowslist.ui.detail


import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.adapters.MovieDetailsAdapter
import com.tomtom.tom.tvshowslist.base.BaseFragment


class DetailFragment : BaseFragment(), MovieDetailsContract.View {

    var isLoading = false

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieDetailsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    val presenter: MovieDetailsContract.Presenter = MovieDetailsPresenter(this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view!!)
        presenter.onViewCreated()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d(tag, layoutManager.findFirstVisibleItemPosition().toString())

                val totalItemCount = layoutManager.getItemCount()
                val lastVisibleItem:Int = layoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + 3) {
                    isLoading = true
                    presenter.downloadNextPage()
                }
            }
        })
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.detail_recycler)
        adapter = MovieDetailsAdapter(emptyList(), presenter)
        layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }

    override fun onDataUpdate(movies: List<Movie>) {
        Log.d(tag, "Detail fragment data updated with ${movies.size} shows")
        isLoading = false
        activity.runOnUiThread {
            adapter.updateList(movies)
        }


    }

}
