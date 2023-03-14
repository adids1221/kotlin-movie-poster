package com.example.movieposter

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
            val detailsDialog = Dialog(holder.itemView.context)
            detailsDialog.setContentView(R.layout.movie_dialog)

            val ticketPrice = 35
            var totalTickets = 1
            val movieReleaseDate = carouselDataList[position].release_date
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val datePickerMinDate = dateFormat.parse(movieReleaseDate)

            val movieTitle = detailsDialog.findViewById<TextView>(R.id.dialog_movie_title)
            val movieImage = detailsDialog.findViewById<ImageView>(R.id.dialog_movie_image)
            val numberOfTickets =
                detailsDialog.findViewById<TextView>(R.id.dialog_number_picker_title)
            val numberPicker = detailsDialog.findViewById<NumberPicker>(R.id.dialog_number_picker)
            val selectDate = detailsDialog.findViewById<TextView>(R.id.dialog_date_picker_title)
            val totalPrice = detailsDialog.findViewById<TextView>(R.id.dialog_total_price)
            val paymentRadioGroup = detailsDialog.findViewById<RadioGroup>(R.id.payment_radio_group)
            val submitButton = detailsDialog.findViewById<Button>(R.id.submit_button)
            val calendar = Calendar.getInstance()
            var selectedPayment = ""

            fun verifyMessage(): String {
                return "You selected to watch:\n${movieTitle.text} on the ${selectDate.text} ${numberOfTickets.text}.\ntickets Total price: ${totalPrice.text}.\nPay by $selectedPayment."
            }

            fun toastMessage(): String {
                return "${movieTitle.text} on the ${selectDate.text}, $totalTickets tickets."
            }

            paymentRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                selectedPayment =
                    if (R.id.payment_credit_card == checkedId) "Credit Card" else "PayPal"
            }

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

            submitButton.setOnClickListener {
                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("Verify Purchase")
                builder.setMessage(verifyMessage())

                builder.setPositiveButton("Purchase") { dialog, which ->
                    detailsDialog.cancel()
                    Toast.makeText(holder.itemView.context, toastMessage(), Toast.LENGTH_LONG)
                        .show()
                }

                builder.setNegativeButton("Decline") { dialog, which ->
                }

                builder.show()
            }

            detailsDialog.show()
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