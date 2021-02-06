package com.github.mminng.banner.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.github.mminng.banner.utils.dp2px

/**
 * Created by zh on 2021/1/22.
 */
internal class IndicatorDecoration : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val reverseLayout = (parent.layoutManager as LinearLayoutManager).reverseLayout
        val orientation = (parent.layoutManager as LinearLayoutManager).orientation
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (position > 0) {
                if (reverseLayout) {
                    outRect.right = dp2px(3F, parent.context).toInt()
                } else {
                    outRect.left = dp2px(3F, parent.context).toInt()
                }
            }
        } else {
            if (position > 0) {
                if (reverseLayout) {
                    outRect.bottom = dp2px(3F, parent.context).toInt()
                } else {
                    outRect.top = dp2px(3F, parent.context).toInt()
                }
            }
        }
    }

}