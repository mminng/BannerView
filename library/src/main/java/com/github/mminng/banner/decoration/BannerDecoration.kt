package com.github.mminng.banner.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.github.mminng.banner.utils.dp2px

/**
 * Created by zh on 2021/1/25.
 */
internal class BannerDecoration constructor(
    private val padding: Float,
    private val convert: Boolean
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = if (convert) dp2px(padding, parent.context).toInt() else padding.toInt()
    }

}