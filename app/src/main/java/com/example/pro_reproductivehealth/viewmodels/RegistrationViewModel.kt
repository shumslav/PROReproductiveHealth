package com.example.pro_reproductivehealth.viewmodels

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pro_reproductivehealth.NODE_DOCTORS
import com.example.pro_reproductivehealth.models.Doctor
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class RegistrationViewModel(private val myApplication: Application) :
    AndroidViewModel(myApplication) {

    val date: MutableLiveData<String> = MutableLiveData()
    val time: MutableLiveData<String> = MutableLiveData()
    val hospital: MutableLiveData<String> = MutableLiveData()
    val doctorsList: MutableLiveData<MutableList<Doctor>> = MutableLiveData()
    val hospitalList = MutableLiveData(listOf("one", "two", "three", "four", "five"))
    val dateListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val monthString = if ((monthOfYear + 1).toString().length == 1)
                "0${monthOfYear + 1}"
            else
                (monthOfYear + 1).toString()
            val dayString = if (dayOfMonth.toString().length == 1)
                "0$dayOfMonth"
            else
                dayOfMonth.toString()
            date.value = "${dayString}.${monthString}.${year}"
        }

    val timeListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val hourString = if (hourOfDay.toString().length == 1)
            "0$hourOfDay"
        else
            hourOfDay.toString()
        val minuteString = if (minute.toString().length == 1)
            "0$minute"
        else
            minute.toString()
        time.value = "$hourString:$minuteString"
    }

    init {
        Firebase.database.getReference(NODE_DOCTORS).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Doctor>()
                snapshot.children.forEach{
                    val doctor = it.getValue(Doctor::class.java)
                    if (doctor!=null)
                        result.add(doctor)
                }
                doctorsList.value = result
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun clearTime() {
        time.value = ""
    }

    fun clearDate() {
        date.value = ""
    }

    fun clearHospital() {
        hospital.value = ""
    }

}