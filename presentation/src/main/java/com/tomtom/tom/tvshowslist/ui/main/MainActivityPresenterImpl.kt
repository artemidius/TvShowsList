package com.tomtom.tom.tvshowslist.ui.main

import android.util.Log
import com.tomtom.tom.tvshowslist.base.BasePresenter


class MainActivityPresenterImpl(private val mainActivity: MainActivity) : BasePresenter(), MainActivityContract.Presenter {

    val tag = this.javaClass.simpleName

    val view: MainActivityContract.View = mainActivity


    override fun onResume()   {  Log.d(tag, "Activity triggered onPause()")     }
    override fun onCreate()   {  Log.d(tag, "Activity triggered onPause()")     }
    override fun onPause()    {  Log.d(tag, "Activity triggered onPause()")     }
    override fun onDestroy()  {  Log.d(tag, "Activity triggered onDestroy()")   }
    override fun onStop()     {  Log.d(tag, "Activity triggered onStop()")      }
}
