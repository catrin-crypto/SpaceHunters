package com.example.spacehunters.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.example.spacehunters.R

class SplashActivity : AppCompatActivity() {
    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        (findViewById<ImageView>(R.id.splash_screen_image)).animate().rotationBy(750f)
            .setInterpolator(LinearInterpolator()).duration = 15000

        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onDestroy(){
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}