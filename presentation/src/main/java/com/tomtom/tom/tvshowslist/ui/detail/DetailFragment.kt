package com.tomtom.tom.tvshowslist.ui.detail


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.adapters.CustomGridLayoutManager
import com.tomtom.tom.tvshowslist.adapters.MovieDetailsAdapter
import com.tomtom.tom.tvshowslist.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*


class DetailFragment : BaseFragment(), MovieDetailsContract.View {

    val tagg = this.javaClass.simpleName
    var isLoading = false


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieDetailsAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val presenter: MovieDetailsContract.Presenter = MovieDetailsPresenter(this)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater!!.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view!!)

        presenter.onViewCreated()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.getItemCount()
                val lastVisibleItem:Int = layoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + 3) {
                    Log.d(tag, "MORe")
                    isLoading = true
                    presenter.downloadNextPage()

                }
            }
        })
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.detail_recycler)
        adapter = MovieDetailsAdapter(emptyList(), presenter)
        layoutManager = CustomGridLayoutManager(view.context,2, 1f)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
    }

    override fun onDataUpdate(movies: List<Movie>) {
        Log.d(tag, "Fragment has: ${movies.size}")
        isLoading = false
        adapter.updateList(movies)
    }
}
