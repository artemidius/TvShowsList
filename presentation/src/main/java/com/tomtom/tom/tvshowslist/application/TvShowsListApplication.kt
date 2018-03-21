package com.tomtom.tom.tvshowslist.application

import android.app.Application
import android.util.Log
import com.tomtom.tom.tvshowslist.dagger.AppComponent
import com.tomtom.tom.tvshowslist.dagger.AppModule
import com.tomtom.tom.tvshowslist.dagger.DaggerAppComponent

class TvShowsListApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(this.javaClass.simpleName, "App starts...")
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}