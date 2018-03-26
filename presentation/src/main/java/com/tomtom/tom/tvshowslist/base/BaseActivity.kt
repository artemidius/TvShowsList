package com.tomtom.tom.tvshowslist.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tomtom.tom.domain.model.Movie

open class BaseActivity : AppCompatActivity(), Dispatcher {

    override fun onConnectionFailed(presenter: ActivityLifeCyclePresenter) { }

    override fun showLoadigProgress(visible: Boolean) { }

    override fun navigateTo(fragment: String, movie: Movie?) { }

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }
}