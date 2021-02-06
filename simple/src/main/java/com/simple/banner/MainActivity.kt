package com.simple.banner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by zh on 2021/1/24.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.image_banner).setOnClickListener {
            startActivity(Intent(this@MainActivity, ImageBannerActivity::class.java))
        }
        findViewById<Button>(R.id.text_banner).setOnClickListener {
            startActivity(Intent(this@MainActivity, TextBannerActivity::class.java))
        }
    }

}

