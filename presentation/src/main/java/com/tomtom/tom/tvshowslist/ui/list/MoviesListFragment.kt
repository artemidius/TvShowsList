package com.tomtom.tom.tvshowslist.ui.list


import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.adapters.CustomGridLayoutManager
import com.tomtom.tom.tvshowslist.adapters.MoviesListAdapter
import com.tomtom.tom.tvshowslist.base.BaseFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_list_content.*

class MoviesListFragment : BaseFragment(), MoviesListContract.View {

    var isLoading = false

    lateinit var bottomSheetBehavior:BottomSheetBehavior<View>

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesListAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val presenter: MoviesListContract.Presenter = MoviesListPresenter(this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater!!.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view!!)

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        presenter.onViewCreated()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem: Int = layoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + 3) {
                    isLoading = true
                    presenter.downloadNextPage()
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        })
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.movies_recycler)
        adapter = MoviesListAdapter(emptyList(), presenter)
        layoutManager = CustomGridLayoutManager(view.context, 2, 1f)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onDataUpdate(movies: List<Movie>) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        isLoading = false
        adapter.updateList(movies)
    }

    override fun onConnectionFailed() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        Snackbar.make(list_container, getString(R.string.connection_failed), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    presenter.downloadNextPage()
                }
                .show()
    }
}