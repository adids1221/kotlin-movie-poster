package com.example.movieposter

import android.app.DatePickerDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CarouselRVAdapter(private val carouselDataList: ArrayList<CarouselData>) :
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val cardMovieName = holder.itemView.findViewById<TextView>(R.id.item_text)
        val cardMoviePoster = holder.itemView.findViewById<ImageView>(R.id.item_image)
        val selectBtn = holder.itemView.findViewById<Button>(R.id.select_movie_btn)
        cardMovieName.text = carouselDataList[position].title
        Picasso.get().load(carouselDataList[position].poster).into(cardMoviePoster)
        selectBtn.setOnClickListener {
            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.movie_dialog)

            val ticketPrice = 35
            var totalTickets = 1
            val movieReleaseDate = carouselDataList[position].release_date
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val datePickerMinDate = dateFormat.parse(movieReleaseDate)

            val movieTitle = dialog.findViewById<TextView>(R.id.dialog_movie_title)
            val movieImage = dialog.findViewById<ImageView>(R.id.dialog_movie_image)
            val releaseDate = dialog.findViewById<TextView>(R.id.dialog_movie_release_date)
            val numberOfTickets = dialog.findViewById<TextView>(R.id.dialog_number_picker_title)
            val numberPicker = dialog.findViewById<NumberPicker>(R.id.dialog_number_picker)
            val selectDate = dialog.findViewById<TextView>(R.id.dialog_date_picker_title)
            val totalPrice = dialog.findViewById<TextView>(R.id.dialog_total_price)
            val calendar = Calendar.getInstance()

            val listener = DatePickerDialog.OnDateSetListener { p0, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                selectDate!!.text = sdf.format(calendar.time)
                totalPrice.text = "${
                    calcTotalPrice(
                        totalTickets,
                        ticketPrice
                    )
                }₪"

            }

            val cYear = calendar.get(Calendar.YEAR)
            val cMonth = calendar.get(Calendar.MONTH)
            val cDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                holder.itemView.context,
                listener,
                cYear,
                cMonth,
                cDay
            )

            datePickerDialog.datePicker.minDate = datePickerMinDate.time

            selectDate.setOnClickListener {
                datePickerDialog.show()
            }

            movieTitle.text = carouselDataList[position].title

            Picasso.get().load(carouselDataList[position].poster).into(movieImage)

            releaseDate.text = "Release Date: $movieReleaseDate"

            numberOfTickets.text = "Number of tickets: $totalTickets"

            numberPicker.minValue = 1
            numberPicker.maxValue = 10
            numberPicker.wrapSelectorWheel = true
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                totalTickets = newVal
                numberOfTickets.text = "Number of tickets: $totalTickets"
                if (selectDate.text != "Select date") {
                    totalPrice.text = "${
                        calcTotalPrice(
                            totalTickets,
                            ticketPrice
                        )
                    }₪"
                }
            }

            dialog.show()
        }
    }

    private fun calcTotalPrice(numOfTickets: Int, Price: Int): String {
        return try {
            println("Number of tickets $numOfTickets, price $Price")
            (numOfTickets * Price).toString()
        } catch (e: NumberFormatException) {
            e.toString()
        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}
//val alertDialog = AlertDialog.Builder(holder.itemView.context)
//    .setTitle("Title of the dialog")
//    .setMessage("Message to be displayed in the dialog")
//    .setPositiveButton("OK") { dialog, _ ->
//        // Do something when the user clicks the OK button
//        dialog.dismiss()
//    }
//    .setNegativeButton("Cancel") { dialog, _ ->
//        // Do something when the user clicks the Cancel button
//        dialog.dismiss()
//    }
//    .create()
//alertDialog.show()