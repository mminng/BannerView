package com.simple.banner.custom

import android.widget.ImageView
import android.widget.TextView
import com.github.mminng.banner.adapter.BannerAdapter
import com.github.mminng.banner.adapter.BannerViewHolder
import com.simple.banner.R

/**
 * Created by zh on 1/29/21.
 */
class CustomTextAdapter : BannerAdapter<CustomModel>(R.layout.item_text_custom) {

    override fun onBindViewHolder(holder: BannerViewHolder, item: CustomModel, position: Int) {
        val text: TextView = holder.findViewById(R.id.item_message)
        val tag: ImageView = holder.findViewById(R.id.item_tag)
        tag.setImageResource(item.tag)
        text.text = item.title
    }

}