package com.github.mminng.banner.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.annotation.ColorInt
import com.github.mminng.banner.R

/**
 * Created by zh on 1/27/21.
 */
class DefaultTextBannerAdapter : BannerAdapter<String>(R.layout.default_text_banner_item) {

    private var _textColor: Int = Color.parseColor("#8A000000")
    private var _textSize: Float = 14F

    override fun onBindViewHolder(holder: BannerViewHolder, item: String, position: Int) {
        val text: TextView = holder.findViewById(R.id.default_banner_text)
        text.setTextColor(_textColor)
        text.textSize = _textSize
        text.text = item
    }

    fun setTextColor(@ColorInt color: Int) {
        _textColor = color
    }

    fun setTextSize(sp: Float) {
        _textSize = sp
    }

}