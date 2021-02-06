package com.github.mminng.banner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.banner.R

/**
 * Created by zh on 2021/1/21.
 */
abstract class BannerAdapter<DATA> constructor(@LayoutRes private val layoutId: Int) :
    RecyclerView.Adapter<BannerViewHolder>() {

    private var _itemClick: ((data: DATA, /*real position*/position: Int) -> Unit)? = null
    private var _dataChanged: ((dataSize: Int) -> Unit)? = null

    private val data: MutableList<DATA> = arrayListOf()
    private var _rawDataSize: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val viewHolder = BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
        bindItemClick(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item = this.data[position]
        holder.itemView.setTag(R.id.item_banner_data, item)
        onBindViewHolder(holder, item, position)
    }

    override fun getItemCount() = this.data.size

    fun updateData(data: List<DATA>) {
        if (data.isEmpty()) return
        this.data.clear()
        if (data.size > 1) {
            this.data.add(data[data.size - 1])
            this.data.addAll(data)
            this.data.add(data[0])
        } else {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
        _rawDataSize = data.size
        _dataChanged?.invoke(data.size)
    }

    fun setOnItemClickListener(listener: (data: DATA, /*real position*/position: Int) -> Unit) {
        this._itemClick = listener
    }

    internal fun getRwaDataSize(): Int = _rawDataSize

    internal fun setOnDataChangedListener(listener: (dataSize: Int) -> Unit) {
        this._dataChanged = listener
    }

    protected abstract fun onBindViewHolder(holder: BannerViewHolder, item: DATA, position: Int)

    @Suppress("UNCHECKED_CAST")
    private fun bindItemClick(viewHolder: BannerViewHolder) {
        viewHolder.itemView.setOnClickListener {
            _itemClick?.let {
                val position = viewHolder.adapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                val item: DATA = viewHolder.itemView.getTag(R.id.item_banner_data) as DATA
                it.invoke(
                    item,
                    /*real position*/
                    if (_rawDataSize <= 1) position else position - 1
                )
            }
        }
    }

}