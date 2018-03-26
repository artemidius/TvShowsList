package com.tomtom.tom.tvshowslist.application

import android.app.Application
import com.tomtom.tom.tvshowslist.R
import com.tomtom.tom.tvshowslist.dagger.AppComponent
import com.tomtom.tom.tvshowslist.dagger.AppModule
import com.tomtom.tom.tvshowslist.dagger.DaggerAppComponent

class TvShowsListApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var apiKey: String
        lateinit var baseUrl: String
    }

    override fun onCreate() {
        super.onCreate()

        /*
        OKAY,
        I know that hardcoding a secret string is totally illegal.
        I do it as an exception for the sake of a test work
        */

        apiKey = resources.getString(R.string.api_key)

        baseUrl = resources.getString(R.string.base_url)

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}