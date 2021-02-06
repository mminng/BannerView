package com.simple.banner.custom

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.mminng.banner.adapter.BannerAdapter
import com.github.mminng.banner.adapter.BannerViewHolder
import com.simple.banner.R

/**
 * Created by zh on 2021/1/24.
 */
class CustomImageAdapter : BannerAdapter<CustomModel>(R.layout.item_image_custom) {

    override fun onBindViewHolder(holder: BannerViewHolder, item: CustomModel, position: Int) {
        val custom: ImageView = holder.findViewById(R.id.custom_banner_image)
        Glide.with(holder.context).load(item.url).into(custom)
    }

}