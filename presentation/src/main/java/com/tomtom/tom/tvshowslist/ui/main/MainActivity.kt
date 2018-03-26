package com.tomtom.tom.tvshowslist.ui.main

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.base.ActivityLifeCyclePresenter
import com.tomtom.tom.tvshowslist.base.BaseActivity
import com.tomtom.tom.tvshowslist.base.BaseFragment
import com.tomtom.tom.tvshowslist.base.Dispatcher
import com.tomtom.tom.tvshowslist.base.Dispatcher.Companion.DETAILS_FRAGMENT
import com.tomtom.tom.tvshowslist.base.Dispatcher.Companion.LIST_FRAGMENT
import com.tomtom.tom.tvshowslist.ui.detail.DetailFragment
import com.tomtom.tom.tvshowslist.ui.list.MoviesListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_list_content.*

class MainActivity : BaseActivity(), MainActivityContract.View, Dispatcher {

    private var presenter:MainActivityContract.Presenter = MainActivityPresenter(this)
    lateinit var bottomSheetBehavior:BottomSheetBehavior<View>

    private val dispatcher: Dispatcher = this
    val listFragment = MoviesListFragment()
    val detailFragment = DetailFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        showLoadigProgress(false)

        listFragment.dispatcher = dispatcher
        detailFragment.dispatcher = dispatcher

        presenter.onCreate()

        navigateTo(LIST_FRAGMENT)

    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun navigateTo(fragment: String, movie: Movie?) {
        super.navigateTo(fragment, movie)

        when (fragment) {
            LIST_FRAGMENT -> addFragment(listFragment)
            DETAILS_FRAGMENT -> {
                if(movie != null) {
                    addFragment(detailFragment)
                    detailFragment.presenter.initializeDataset(movie)
                }
            }
            else -> Log.d(this.javaClass.simpleName, "Unknown fragment to navigate")
        }

    }

    private fun addFragment(fragment: BaseFragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragments_container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onDataUpdate(movies: List<Movie>) {    }

    override fun showLoadigProgress(visible: Boolean) {
        runOnUiThread {
            bottomSheetBehavior.state = if (visible) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HIDDEN
        }
    }

    override fun onConnectionFailed(presenter:ActivityLifeCyclePresenter) {
        Snackbar.make(main_host, getString(R.string.connection_failed), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    presenter.downloadNextPage()
                }
                .show()
    }

    override fun onBackPressed() {

        val count = fragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            fragmentManager.popBackStack()
        }

    }

}
