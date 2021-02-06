package com.github.mminng.banner.listener

/**
 * Created by zh on 1/24/21.
 */
interface OnItemClickListener<DATA> {

    fun onItemClick(data: DATA, /*real position*/position: Int)

}