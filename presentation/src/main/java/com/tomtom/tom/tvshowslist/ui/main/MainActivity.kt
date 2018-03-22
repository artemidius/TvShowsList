package com.tomtom.tom.tvshowslist.ui.main

import android.os.Bundle
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.base.BaseActivity

class MainActivity : BaseActivity(), MainActivityContract.View {

    var presenter:MainActivityContract.Presenter = MainActivityPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDataUpdate() {    }

}
