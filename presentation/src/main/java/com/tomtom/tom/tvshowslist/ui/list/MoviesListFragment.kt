package com.tomtom.tom.tvshowslist.ui.list


import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.adapters.CustomGridLayoutManager
import com.tomtom.tom.tvshowslist.adapters.MoviesListAdapter
import com.tomtom.tom.tvshowslist.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*

class MoviesListFragment : BaseFragment(), MoviesListContract.View {

    val tagg = this.javaClass.simpleName
    var isLoading = false

    lateinit var snackbar:Snackbar

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesListAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val presenter:MoviesListContract.Presenter = MoviesListPresenter(this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater!!.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view!!)

        snackbar = Snackbar.make(list_container, "Loading...", Snackbar.LENGTH_INDEFINITE)
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
                    snackbar.show()

                }
            }
        })
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.movies_recycler)
        adapter = MoviesListAdapter(emptyList(), presenter)
        layoutManager = CustomGridLayoutManager(view.context,2, 1f)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
    }

    override fun onDataUpdate(movies: List<Movie>) {
        Log.d(tag, "Fragment has: ${movies.size}")
        snackbar.dismiss()
        isLoading = false
        adapter.updateList(movies)
    }
}