package com.tomtom.tom.tvshowslist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.action.ViewActions.swipeUp
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.tomtom.tom.tvshowslist.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EspressoScrollTest {

    @Rule @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun swipeList() {

        Thread.sleep(3000)

        for (x in 0 .. 10) {
            onView(withId(R.id.movies_recycler)).perform(swipeUp())
            Thread.sleep(1000)
        }

        onView(withId(R.id.movies_recycler)).check(matches(isDisplayed()))

    }
}