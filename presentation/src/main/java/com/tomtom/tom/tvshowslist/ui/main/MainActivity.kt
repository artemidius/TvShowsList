package com.tomtom.tom.tvshowslist.ui.main

import android.os.Bundle
import android.util.Log
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.base.BaseActivity
import com.tomtom.tom.tvshowslist.base.BaseFragment
import com.tomtom.tom.tvshowslist.base.Navigator
import com.tomtom.tom.tvshowslist.base.Navigator.Companion.DETAILS_FRAGMENT
import com.tomtom.tom.tvshowslist.base.Navigator.Companion.LIST_FRAGMENT
import com.tomtom.tom.tvshowslist.ui.detail.DetailFragment
import com.tomtom.tom.tvshowslist.ui.list.MoviesListFragment

class MainActivity : BaseActivity(), MainActivityContract.View, Navigator {

    private var presenter:MainActivityContract.Presenter = MainActivityPresenter(this)

    private val navigator: Navigator = this
    val listFragment = MoviesListFragment()
    val detailFragment = DetailFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        listFragment.navigator = navigator
        detailFragment.navigator = navigator

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

    override fun onBackPressed() {

        val count = fragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            fragmentManager.popBackStack()
        }

    }

}
