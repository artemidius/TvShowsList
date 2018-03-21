package com.tomtom.tom.tvshowslist.dagger

import android.content.Context
import com.tomtom.tom.tvshowslist.application.TvShowsListApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: TvShowsListApplication) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): TvShowsListApplication = app
}