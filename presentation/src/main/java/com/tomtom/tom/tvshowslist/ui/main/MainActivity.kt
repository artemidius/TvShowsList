package com.tomtom.tom.tvshowslist.ui.main

import android.os.Bundle
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.base.BaseActivity

class MainActivity : BaseActivity(), MainActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDataUpdate() {    }

}
