package com.github.mminng.banner.utils

import android.content.Context

/**
 * Created by zh on 1/26/21.
 */
internal fun dp2px(dpValue: Float, context: Context): Float {
    val density = context.resources.displayMetrics.density
    return dpValue * density + 0.5F
}

internal fun sp2px(spValue: Float, context: Context): Float {
    val scaledDensity = context.resources.displayMetrics.scaledDensity
    return spValue * scaledDensity + 0.5F
}