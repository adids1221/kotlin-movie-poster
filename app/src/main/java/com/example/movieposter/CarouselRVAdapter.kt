package com.example.movieposter

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val dialogContext = holder.itemView.context
        //Animations
        val alphaAnim = AnimationUtils.loadAnimation(dialogContext, R.anim.fade)
        val zoomInAnim = AnimationUtils.loadAnimation(dialogContext, R.anim.zoom_in)
        val bounceAnim = AnimationUtils.loadAnimation(dialogContext, R.anim.bounce)
        val fadeInAnim = AnimationUtils.loadAnimation(dialogContext, R.anim.fade)

        //Movie card
        val cardMovieName = holder.itemView.findViewById<TextView>(R.id.item_text)
        val cardMoviePoster = holder.itemView.findViewById<ImageView>(R.id.item_image)
        val selectBtn = holder.itemView.findViewById<Button>(R.id.select_movie_btn)
        cardMovieName.text = carouselDataList[position].title
        Picasso.get().load(carouselDataList[position].poster).into(cardMoviePoster)
        cardMoviePoster.startAnimation(alphaAnim)
        cardMoviePoster.setOnClickListener { cardMoviePoster.startAnimation(bounceAnim) }
        cardMovieName.startAnimation(fadeInAnim)

        //Select Btn - open detailsDialog
        selectBtn.setOnClickListener {
            val detailsDialog = Dialog(dialogContext)
            detailsDialog.setContentView(R.layout.movie_dialog)

            val cinemas =
                arrayOf(
                    dialogContext.getString(R.string.yp_rishon),
                    dialogContext.getString(R.string.yp_ayalon),
                    dialogContext.getString(R.string.yp_haifa)
                )
            var selectedCinema = ""
            var isDateSelected = false
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
            val spinner = detailsDialog.findViewById<Spinner>(R.id.cinema_spinner)
            val paymentRadioGroup = detailsDialog.findViewById<RadioGroup>(R.id.payment_radio_group)
            val submitButton = detailsDialog.findViewById<Button>(R.id.submit_button)
            val calendar = Calendar.getInstance()
            var selectedPayment = "Credit Card"

            //Functions
            fun verifyMessage(): String {
                return "${dialogContext.getString(R.string.verify_part_1)}:\n${movieTitle.text} ${
                    dialogContext.getString(
                        R.string.verify_part_2
                    )
                } ${selectDate.text} ${dialogContext.getString(R.string.verify_part_3)} $selectedCinema. \r\n${
                    dialogContext.getString(
                        R.string.verify_part_4
                    )
                } ${numberOfTickets.text} ${dialogContext.getString(R.string.verify_part_5)}.\r\n${
                    dialogContext.getString(
                        R.string.verify_part_6
                    )
                }: ${totalPrice.text}.\n${dialogContext.getString(R.string.verify_part_7)} $selectedPayment."
            }

            fun toastMessage(): String {
                return "${movieTitle.text} ${
                    dialogContext.getString(
                        R.string.verify_part_2
                    )
                } ${selectDate.text} ${dialogContext.getString(R.string.verify_part_3)} $selectedCinema, $totalTickets ${
                    dialogContext.getString(
                        R.string.verify_part_5
                    )
                }."
            }

            movieTitle.text = carouselDataList[position].title
            Picasso.get().load(carouselDataList[position].poster).into(movieImage)
            numberOfTickets.text =
                "${dialogContext.getString(R.string.number_of_tickets)}: $totalTickets"
            movieImage.setOnClickListener { movieImage.startAnimation(bounceAnim) }
            movieTitle.setOnClickListener { movieTitle.startAnimation(zoomInAnim) }

            val translationDownTitle: Animator =
                ObjectAnimator.ofFloat(movieTitle, "translationY", -100f, 0f).setDuration(3000)
            val rotateTitle: Animator =
                ObjectAnimator.ofFloat(movieTitle, "rotationY", 0f, 360f).setDuration(3000)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(translationDownTitle, rotateTitle)
            animatorSet.start()

            //Cinema Spinner
            if (spinner != null) {
                val adapter = ArrayAdapter(
                    dialogContext,
                    android.R.layout.simple_spinner_item, cinemas
                )
                spinner.adapter = adapter
            }

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    selectedCinema = cinemas[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    selectedCinema = cinemas[0]
                }
            }

            //Payment RadioGroup
            paymentRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                selectedPayment =
                    if (R.id.payment_credit_card == checkedId) dialogContext.getString(R.string.payment_method_1) else dialogContext.getString(
                        R.string.payment_method_2
                    )
            }

            //DatePickerDialog
            val listener = DatePickerDialog.OnDateSetListener { p0, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                selectDate!!.text = sdf.format(calendar.time)
                isDateSelected = true
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
                dialogContext,
                listener,
                cYear,
                cMonth,
                cDay
            )

            if (datePickerMinDate != null) {
                datePickerDialog.datePicker.minDate = datePickerMinDate.time
            }

            selectDate.setOnClickListener {
                datePickerDialog.show()
            }

            //Number Picker
            numberPicker.minValue = 1
            numberPicker.maxValue = 10
            numberPicker.wrapSelectorWheel = true
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                totalTickets = newVal
                numberOfTickets.text =
                    "${dialogContext.getString(R.string.number_of_tickets)}: $totalTickets"
                if (selectDate.text != dialogContext.getString(R.string.select_date)) {
                    totalPrice.text = "${
                        calcTotalPrice(
                            totalTickets,
                            ticketPrice
                        )
                    }₪"
                }
            }

            //Submit
            submitButton.setOnClickListener {
                val builder = AlertDialog.Builder(dialogContext)
                if (isDateSelected) {
                    builder.setTitle(dialogContext.getString(R.string.verify_dialog_title))
                    builder.setMessage(verifyMessage())

                    builder.setPositiveButton(dialogContext.getString(R.string.verify_dialog_accept_btn)) { dialog, which ->
                        detailsDialog.cancel()
                        Toast.makeText(dialogContext, toastMessage(), Toast.LENGTH_LONG)
                            .show()
                    }

                    builder.setNegativeButton(dialogContext.getString(R.string.verify_dialog_decline_btn)) { dialog, which ->
                    }

                    builder.show()
                } else {
                    Toast.makeText(
                        dialogContext,
                        "${dialogContext.getString(R.string.select_date_error)} ${movieTitle.text}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

            detailsDialog.window?.attributes?.windowAnimations = R.style.dialogAnimation
            detailsDialog.window?.setGravity(Gravity.BOTTOM)
            detailsDialog.show()
        }
    }

    private fun calcTotalPrice(numOfTickets: Int, Price: Int): String {
        return try {
            (numOfTickets * Price).toString()
        } catch (e: NumberFormatException) {
            e.toString()
        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}
