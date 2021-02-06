package com.github.mminng.banner.adapter

import android.content.Context
import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by zh on 2021/1/21.
 */
class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val views: SparseArray<View> = SparseArray()

    val context: Context = itemView.context

    fun <V : View> findViewById(@IdRes viewId: Int): V {
        val view = getView<V>(viewId)
        checkNotNull(view) { "BannerViewHolder:findViewById() must not be null" }
        return view
    }

    @Suppress("UNCHECKED_CAST")
    private fun <V : View> getView(viewId: Int): V? {
        val view = views.get(viewId)
        if (view == null) {
            itemView.findViewById<V>(viewId)?.let {
                views.put(viewId, it)
                return it
            }
        }
        return view as? V
    }

}