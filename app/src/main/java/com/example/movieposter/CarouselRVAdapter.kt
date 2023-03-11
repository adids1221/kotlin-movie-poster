package com.example.movieposter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CarouselRVAdapter(private val carouselDataList: ArrayList<CarouselData>) :
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.item_text)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.item_image)
        val selectBtn = holder.itemView.findViewById<Button>(R.id.select_movie_btn)
        textView.text = carouselDataList[position].title
        Picasso.get().load(carouselDataList[position].poster).into(imageView)
        selectBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}