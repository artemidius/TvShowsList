package com.tomtom.tom.tvshowslist.application

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
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

    fun hasInternetAccess(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

}