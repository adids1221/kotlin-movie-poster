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
    val overview: String,
    val poster: String,
    val release_date: String
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
                    "Puss in Boots: The Last Wish",
                    "Puss in Boots discovers that his passion for adventure has taken its toll: He has burned through eight of his nine lives, leaving him with only one life left. Puss sets out on an epic journey to find the mythical Last Wish and restore his nine lives.",
                    imagePrefix + "kuf6dutpsT0vSVehic3EZIqkOBt.jpg",
                    "2022-12-07",
                ),
                CarouselData(
                    "Scream VI",
                    "Following the latest Ghostface killings, the four survivors leave Woodsboro behind and start a fresh chapte",
                    imagePrefix + "t2NEaFrNFRCrBIyAETlz5sqq15H.jpg",
                    "2023-03-08",
                ),
                CarouselData(
                    "The Super Mario Bros. Movie",
                    "While working underground to fix a water main, Brooklyn plumbers—and brothers—Mario and Luigi are transported down a mysterious pipe and wander into a magical new world. But when the brothers are separated, Mario embarks on an epic quest to find Luigi.i",
                    imagePrefix + "qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg",
                    "2023-03-30",
                ),
                CarouselData(
                    "John Wick: Chapter 4",
                    "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                    imagePrefix + "vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                    "2023-03-22",
                ),
                CarouselData(
                    "Shazam! Fury of the Gods",
                    "Billy Batson and his foster siblings, who transform into superheroes by saying \\\"Shazam!\\\", are forced to get back into action and fight the Daughters of Atlas, who they must stop from using a weapon that could destroy the world.",
                    imagePrefix + "zpCCTtuQMHiHycpsrWnW2eCrBql.jpg",
                    "2023-03-15",
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