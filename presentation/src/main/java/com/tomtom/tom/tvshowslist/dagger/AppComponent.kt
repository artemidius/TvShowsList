package com.tomtom.tom.tvshowslist.dagger

import com.tomtom.tom.tvshowslist.base.BasePresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        AppModule::class
        )
)

interface AppComponent {
    fun inject(basePresenter: BasePresenter)
}