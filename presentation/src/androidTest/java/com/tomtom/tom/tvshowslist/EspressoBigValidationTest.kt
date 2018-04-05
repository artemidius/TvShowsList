package com.tomtom.tom.tvshowslist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.tomtom.tom.tvshowslist.adapters.DetailsPagerAdapter
import com.tomtom.tom.tvshowslist.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class EspressoBigValidationTest {

    private val numberOfSwipes = 10
    private val numberOfClicks = 10
    private val delay:Long = 500

    @Rule @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java)

    private fun getRandomItemOfTheList(listSize:Int):Int {
        return when {
            listSize > 0 -> Random().nextInt(listSize - 1)
            else -> 0
        }
    }

    @Test
    fun bigFunctionalityTest() {


        Thread.sleep(3000)

        //some swipes on movies list forward
        for (x in 0 .. numberOfSwipes) {
            onView(withId(R.id.movies_recycler)).perform(swipeUp())
            Thread.sleep(delay * 2)
        }

        //some swipes on movies list back
        for (x in 0 .. numberOfSwipes) {
            onView(withId(R.id.movies_recycler)).perform(swipeDown())
            Thread.sleep(delay)
        }

        //tap on movie
        onView(ViewMatchers.withId(R.id.movies_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<DetailsPagerAdapter.ViewHolder>(getRandomItemOfTheList(mActivityRule.activity.listFragment.adapter.movies.size), ViewActions.click()))

        //some swipes on pager forward
        for (x in 0 .. numberOfSwipes * 3) {
            onView(withId(R.id.detail_recycler)).perform(swipeLeft())
            Thread.sleep(delay)
        }

        //some swipes on pager back
        for (x in 0 .. numberOfSwipes / 2) {
            onView(withId(R.id.detail_recycler)).perform(swipeRight())
            Thread.sleep(delay)
        }

        //some swipes on indicator forward
        for (x in 0 .. numberOfSwipes / 2) {
            onView(withId(R.id.detail_indication_list)).perform(swipeLeft())
            Thread.sleep(delay)
        }

        //some swipes on indicator back
        for (x in 0 .. numberOfSwipes) {
            onView(withId(R.id.detail_indication_list)).perform(swipeRight())
            Thread.sleep(delay)
        }

        //tap on indicator 10 times
        for (x in 0 .. numberOfClicks * 2) {
            onView(ViewMatchers.withId(R.id.detail_indication_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<DetailsPagerAdapter.ViewHolder>(getRandomItemOfTheList(mActivityRule.activity.detailFragment.indicatorPagerAdapter.movies.size), ViewActions.click()))
        }

        Thread.sleep(1000)
        onView(isRoot()).perform(ViewActions.pressBack())

        Thread.sleep(4000)

        //check back press behaviour
        onView(withId(R.id.movies_recycler)).check(matches(isDisplayed()))

       //check that activity is alive
        Thread.sleep(1000)


    }
}