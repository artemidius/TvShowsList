package com.tomtom.tom.tvshowslist.ui.main

import android.util.Log
import com.tomtom.tom.domain.boundaries.Interactor
import com.tomtom.tom.domain.model.MoviesResponse
import com.tomtom.tom.tvshowslist.base.BasePresenter


class MainActivityPresenter(mainActivity: MainActivity?) : BasePresenter(), MainActivityContract.Presenter, Interactor.Presentation {

    val tag = this.javaClass.simpleName

    override fun onMoviesPageDownloaded(response: MoviesResponse) { }

    override fun onMoviesPageDownloadFailed(error: Throwable) { }

    override fun downloadNextPage() { }

    override fun onCreate()   {  Log.d(tag, "Activity triggered onResume()")    }
    override fun onResume()   {  Log.d(tag, "Activity triggered onResume()")    }
    override fun onPause()    {  Log.d(tag, "Activity triggered onPause()")     }
    override fun onDestroy()  {  Log.d(tag, "Activity triggered onDestroy()")   }
    override fun onStop()     {  Log.d(tag, "Activity triggered onStop()")      }
}

