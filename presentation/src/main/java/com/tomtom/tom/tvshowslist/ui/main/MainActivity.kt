package com.tomtom.tom.tvshowslist.ui.main

import android.os.Bundle
import android.util.Log
import com.tomtom.tom.domain.model.Movie
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.base.BaseActivity
import com.tomtom.tom.tvshowslist.base.BaseFragment
import com.tomtom.tom.tvshowslist.base.Navigator
import com.tomtom.tom.tvshowslist.base.Navigator.Companion.LIST_FRAGMENT
import com.tomtom.tom.tvshowslist.ui.list.MoviesListFragment

class MainActivity : BaseActivity(), MainActivityContract.View, Navigator {

    private var presenter:MainActivityContract.Presenter = MainActivityPresenter(this)

    private val navigator: Navigator = this
    private val listFragment = MoviesListFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listFragment.navigator = navigator

        presenter.onCreate()

        navigateTo(LIST_FRAGMENT)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun navigateTo(fragment: String) {
        super.navigateTo(fragment)

        when (fragment) {
            LIST_FRAGMENT -> addFragment(listFragment as BaseFragment)
            else -> Log.d(this.javaClass.simpleName, "Unknown fragment to navigate")
        }

    }

    private fun addFragment(fragment: BaseFragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragments_container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onDataUpdate(movies: List<Movie>) {    }

}
