package com.github.mminng.banner.layoutmanager

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

internal class BannerLayoutManager(
    context: Context,
    @RecyclerView.Orientation orientation: Int
) : LinearLayoutManager(context, orientation, false) {

    private val defaultSpeed: Float = 25F

    var speed: Float = defaultSpeed

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        super.smoothScrollToPosition(recyclerView, state, position)
        val scroller = object : LinearSmoothScroller(recyclerView?.context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return if (speed > defaultSpeed) {
                    speed / displayMetrics.densityDpi
                } else {
                    defaultSpeed / displayMetrics.densityDpi
                }
            }
        }
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

}