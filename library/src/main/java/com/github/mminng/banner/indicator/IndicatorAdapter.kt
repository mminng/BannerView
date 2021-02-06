package com.github.mminng.banner.indicator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.banner.R

/**
 * Created by zh on 1/25/21.
 */
internal class IndicatorAdapter : RecyclerView.Adapter<IndicatorAdapter.ViewHolder>() {

    private var _selectedRes = R.drawable.default_indicator_selected
    private var _unselectedRes = R.drawable.default_indicator_unselected

    private val data: MutableList<IndicatorSelector> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.banner_indicator_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data[position].selected) {
            holder.indicator.setImageResource(_selectedRes)
        } else {
            holder.indicator.setImageResource(_unselectedRes)
        }
    }

    internal fun submit(data: List<IndicatorSelector>) {
        if (data.isEmpty()) return
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    internal fun switchToCurrent(position: Int) {
        if (this.data.isEmpty()) return
        this.data.forEachIndexed { index, it ->
            it.selected = index == position
        }
        notifyDataSetChanged()
    }

    internal fun setIndicatorDrawable(@DrawableRes unselectedRes: Int) {
        _unselectedRes = unselectedRes
    }

    internal fun setIndicatorSelectedDrawable(@DrawableRes selectedRes: Int) {
        _selectedRes = selectedRes
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val indicator: ImageView = itemView.findViewById(R.id.default_banner_indicator)
    }

}