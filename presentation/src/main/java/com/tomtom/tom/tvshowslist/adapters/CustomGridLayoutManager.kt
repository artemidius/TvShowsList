package com.tomtom.tom.tvshowslist.adapters

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics


class CustomGridLayoutManager(context: Context, spanNumber:Int, val millisPerInch: Float = 5f):GridLayoutManager(context, spanNumber) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {

        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return super.computeScrollVectorForPosition(targetPosition)
            }
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return millisPerInch / displayMetrics.densityDpi
            }
        }

        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}