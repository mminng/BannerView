package com.simple.banner

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mminng.banner.TextBannerView
import com.github.mminng.banner.adapter.DefaultTextBannerAdapter
import com.simple.banner.custom.CustomModel
import com.simple.banner.custom.CustomTextAdapter

/**
 * Created by zh on 2021/1/26.
 */
class TextBannerActivity : AppCompatActivity() {

    private lateinit var defaultTextBanner: TextBannerView
    private lateinit var customTextBanner: TextBannerView

    //simple
    private val defaultAdapter = DefaultTextBannerAdapter()
    private val defaultData: List<String> = listOf(
        "Android",
        "iOS",
        "Kotlin",
        "Swift",
        "Java"
    )

    //custom
    private val customAdapter = CustomTextAdapter()
    private val customData: List<CustomModel> = listOf(
        CustomModel(
            "",
            "孙燕姿词曲从心创作 新单曲《余额》全面惊喜上线",
            R.drawable.ic_baseline_audiotrack_24
        ),
        CustomModel(
            "",
            "进击的巨人final season",
            R.drawable.ic_baseline_book_24
        ),
        CustomModel(
            "",
            "我国成功发射遥感三十一号02组卫星",
            R.drawable.ic_baseline_loyalty_24
        ),
        CustomModel(
            "",
            "《阿年》全网首映，iPhone 带着新的传说来了。",
            R.drawable.ic_baseline_movie_filter_24
        ),
        CustomModel(
            "",
            "Nintendo Switch，随时随地畅玩任天堂出品的高品质游戏。",
            R.drawable.ic_baseline_sports_esports_24
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_banner)
        defaultTextBanner = findViewById(R.id.default_text_banner)
        customTextBanner = findViewById(R.id.custom_text_banner)

        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = "TextBanner"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        lifecycle.addObserver(defaultTextBanner)
        lifecycle.addObserver(customTextBanner)

        //simple
        defaultAdapter.setOnItemClickListener { data, _ ->
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
        }
        defaultAdapter.updateData(defaultData)
        defaultTextBanner.setAdapter(defaultAdapter)
        defaultTextBanner.play()

        //custom
        customAdapter.setOnItemClickListener { data, _ ->
            Toast.makeText(this, data.title, Toast.LENGTH_SHORT).show()
        }
        customAdapter.updateData(customData)
        customTextBanner.setAdapter(customAdapter)
        customTextBanner.play()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_text_banner, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.color -> {
                //DefaultTextBannerAdapter only
                defaultAdapter.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
                defaultAdapter.notifyDataSetChanged()
            }
            R.id.size -> {
                //DefaultTextBannerAdapter only
                defaultAdapter.setTextSize(18F)
                defaultAdapter.notifyDataSetChanged()
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

}