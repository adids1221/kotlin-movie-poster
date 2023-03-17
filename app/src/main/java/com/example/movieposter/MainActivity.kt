package com.example.movieposter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val browseMoviesBtn = findViewById<Button>(R.id.browse_movies_btn)
        val slideUpAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        browseMoviesBtn.startAnimation(slideUpAnim)
        browseMoviesBtn.setOnClickListener {
            val browseMoviesIntent = Intent(this, MovieCarousel::class.java)
            startActivity(browseMoviesIntent)
        }
    }
}