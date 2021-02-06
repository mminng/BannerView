package com.simple.banner

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.mminng.banner.BannerView
import com.github.mminng.banner.adapter.DefaultBannerAdapter
import com.simple.banner.custom.CustomImageAdapter
import com.simple.banner.custom.CustomModel

/**
 * Created by zh on 2021/1/24.
 */
class ImageBannerActivity : AppCompatActivity() {

    private lateinit var defaultBanner: BannerView
    private lateinit var customBanner: BannerView

    //simple
    private val defaultData: List<String> = listOf(
        "https://i0.hdslb.com/bfs/archive/a5ae0e7c7697240d1b9e721f3c3a06bb40e71baa.jpg@880w_388h_1c_95q",
        "https://i0.hdslb.com/bfs/archive/c444f70120f9cb351186d578e77cdb2ef99a382c.jpg@880w_388h_1c_95q",
        "https://i0.hdslb.com/bfs/archive/b05a71e40eadc6b2cbadc382f04561d360d8f312.jpg@880w_388h_1c_95q",
        "https://i0.hdslb.com/bfs/archive/5bc974ef21bf870844a6a4a23348f7307f2eda73.jpg@880w_388h_1c_95q",
        "https://i0.hdslb.com/bfs/archive/366cc589f6b326c5585d7bfbc0fd77cab874a11a.jpg@880w_388h_1c_95q",
        "https://i0.hdslb.com/bfs/archive/bbe9bfa6b9cdde990de1a7f96be932c7590c08ab.png@880w_388h_1c_95q"
    )
    private val defaultAdapter: DefaultBannerAdapter = object : DefaultBannerAdapter() {
        override fun onBind(data: String, view: ImageView) {
            view.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(this@ImageBannerActivity).load(data).into(view)
        }
    }

    //custom
    private val customData: List<CustomModel> = listOf(
        CustomModel(
            "https://i0.hdslb.com/bfs/archive/a5ae0e7c7697240d1b9e721f3c3a06bb40e71baa.jpg@880w_388h_1c_95q",
            "国创年终盘点",
            0
        ),
        CustomModel(
            "https://i0.hdslb.com/bfs/archive/c444f70120f9cb351186d578e77cdb2ef99a382c.jpg@880w_388h_1c_95q",
            "重生细胞",
            0
        ),
        CustomModel(
            "https://i0.hdslb.com/bfs/archive/b05a71e40eadc6b2cbadc382f04561d360d8f312.jpg@880w_388h_1c_95q",
            "音乐大盘点",
            0
        ),
        CustomModel(
            "https://i0.hdslb.com/bfs/archive/5bc974ef21bf870844a6a4a23348f7307f2eda73.jpg@880w_388h_1c_95q",
            "寒假逆袭",
            0
        ),
        CustomModel(
            "https://i0.hdslb.com/bfs/archive/366cc589f6b326c5585d7bfbc0fd77cab874a11a.jpg@880w_388h_1c_95q",
            "工作细胞",
            0
        ),
        CustomModel(
            "https://i0.hdslb.com/bfs/archive/bbe9bfa6b9cdde990de1a7f96be932c7590c08ab.png@880w_388h_1c_95q",
            "拜年纪",
            0
        ),
    )
    private val customAdapter = CustomImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_banner)
        defaultBanner = findViewById(R.id.default_banner)
        customBanner = findViewById(R.id.custom_banner)

        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = "ImageBanner"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        lifecycle.addObserver(defaultBanner)
        lifecycle.addObserver(customBanner)

        //simple
        defaultAdapter.updateData(defaultData)
        defaultAdapter.setOnItemClickListener { _, position ->
            Toast.makeText(this, "position:$position", Toast.LENGTH_SHORT).show()
        }
        defaultBanner.setAdapter(defaultAdapter)
        defaultBanner.play()

        //custom
        customAdapter.updateData(customData)
        customAdapter.setOnItemClickListener { data, _ ->
            Toast.makeText(this, data.title, Toast.LENGTH_SHORT).show()
        }
        customBanner.setAdapter(customAdapter)
        customBanner.play()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

}