package com.github.mminng.banner.adapter

import android.widget.ImageView
import com.github.mminng.banner.R

/**
 * Created by zh on 2021/1/23.
 */
abstract class DefaultBannerAdapter : BannerAdapter<String>(R.layout.default_banner_item) {

    override fun onBindViewHolder(holder: BannerViewHolder, item: String, position: Int) {
        val imageView: ImageView = holder.findViewById(R.id.default_banner_image)
        onBind(item, imageView)
    }

    protected abstract fun onBind(data: String, view: ImageView)

}