package com.myapp.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var selectedDate:TextView? = null
    private var dobInMinutesDisp:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnToDatePicker = findViewById<Button>(R.id.btnToCalc)
        selectedDate = findViewById(R.id.head4)
        dobInMinutesDisp = findViewById(R.id.head6)
        btnToDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{_,year,month,day ->
            Toast.makeText(this, "$year ${month+1} $day", Toast.LENGTH_SHORT).show()
            var selectedDateFromPicker = "$day/${month+1}/$year"
            selectedDate?.text = selectedDateFromPicker

            val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDateFromPicker)
            theDate?.let{
                val selectedDateInMinutes = theDate.time / 60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let{
                    val currentDateInMinutes = currentDate.time / 60000
                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    dobInMinutesDisp?.text = differenceInMinutes.toString()
                }
            }
        },
            year,
            month,
            day,
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}