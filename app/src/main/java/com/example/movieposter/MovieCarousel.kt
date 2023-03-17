package com.example.movieposter

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

data class CarouselData(
    val title: String,
    val poster: String,
)

class MovieCarousel : AppCompatActivity() {

    fun onSelect(view: View) {
        MovieDialog().show(supportFragmentManager, "MovieFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_carousel)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        val imagePrefix = "https://image.tmdb.org/t/p/w500/"

        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        val demoData =
            arrayListOf<CarouselData>(
                CarouselData(
                    this.getString(R.string.puss_in_boots),
                    imagePrefix + "kuf6dutpsT0vSVehic3EZIqkOBt.jpg",
                ),
                CarouselData(
                    this.getString(R.string.scream_vi),
                    imagePrefix + "t2NEaFrNFRCrBIyAETlz5sqq15H.jpg",
                ),
                CarouselData(
                    this.getString(R.string.supre_mario),
                    imagePrefix + "qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg",
                ),
                CarouselData(
                    this.getString(R.string.jhon_wick),
                    imagePrefix + "vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",

                    ),
                CarouselData(
                    this.getString(R.string.shazam),
                    imagePrefix + "zpCCTtuQMHiHycpsrWnW2eCrBql.jpg",

                    ),
            )

        viewPager.adapter = CarouselRVAdapter(demoData)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        viewPager.setPageTransformer(compositePageTransformer)
    }
}