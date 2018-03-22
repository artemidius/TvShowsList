package com.tomtom.tom.tvshowslist

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.tomtom.tom.tvshowslist.ui.main.MainActivityPresenter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ContextInPresenterTest {
    @Test
    @Throws(Exception::class)
    fun testContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val presenterContext = MainActivityPresenter(null).context
        assertEquals(presenterContext.packageName, appContext.packageName)
    }
}