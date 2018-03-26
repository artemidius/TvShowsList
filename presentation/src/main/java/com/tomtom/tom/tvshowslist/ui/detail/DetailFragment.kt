package com.tomtom.tom.tvshowslist.ui.detail


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.adapters.DetailsIndicatorAdapter
import com.tomtom.tom.tvshowslist.adapters.DetailsPagerAdapter
import com.tomtom.tom.tvshowslist.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list_content.*


class DetailFragment : BaseFragment(), MovieDetailsContract.View {

    var isLoading = false

    private lateinit var pagerRecycler: RecyclerView
    private lateinit var pagerPagerAdapter: DetailsPagerAdapter
    private lateinit var pagerLayoutManager: LinearLayoutManager

    private lateinit var indicatorRecycler: RecyclerView
    private lateinit var indicatorPagerAdapter: DetailsIndicatorAdapter
    private lateinit var indicatorLayoutManager: LinearLayoutManager
    val presenter: MovieDetailsContract.Presenter = MovieDetailsPresenter(this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager(view!!)
        initIndicator(view)
        presenter.onViewCreated()

        pagerRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                presenter.onPagerSnap(pagerLayoutManager.findFirstVisibleItemPosition())
                val totalItemCount = pagerLayoutManager.getItemCount()
                val lastVisibleItem: Int = pagerLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + 3) requestNextPage()
            }
        })

        indicatorRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = indicatorLayoutManager.getItemCount()
                val lastVisibleItem: Int = indicatorLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + 3) requestNextPage()
            }
        })
    }

    private fun requestNextPage() {
        isLoading = true
        presenter.downloadNextPage()
    }

    private fun initPager(view: View) {
        pagerRecycler = view.findViewById(R.id.detail_recycler)
        pagerPagerAdapter = DetailsPagerAdapter(emptyList(), presenter)
        pagerLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        pagerRecycler.layoutManager = pagerLayoutManager
        pagerRecycler.adapter = pagerPagerAdapter
        PagerSnapHelper().attachToRecyclerView(pagerRecycler)
    }

    private fun initIndicator(view: View) {
        indicatorRecycler = view.findViewById(R.id.detail_indication_list)
        indicatorPagerAdapter = DetailsIndicatorAdapter(emptyList(), presenter)
        indicatorLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        indicatorRecycler.layoutManager = indicatorLayoutManager
        indicatorRecycler.adapter = indicatorPagerAdapter
    }

    override fun onDataUpdate(movies: List<Movie>) {
        Log.d(tag, "Detail fragment data updated with ${movies.size} shows")
        isLoading = false
        activity.runOnUiThread {
            pagerPagerAdapter.updateList(movies)
            indicatorPagerAdapter.updateList(movies)
        }
    }

    override fun scrollPagerToPosition(position: Int) {
        pagerLayoutManager.scrollToPosition(position)
    }

    override fun onConnectionFailed() {
        isLoading = false
        Snackbar.make(list_container, getString(R.string.connection_failed), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    requestNextPage()
                }
                .show()
    }
}
