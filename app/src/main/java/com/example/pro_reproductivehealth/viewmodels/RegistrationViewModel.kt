package com.example.pro_reproductivehealth.viewmodels

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*

class RegistrationViewModel(private val myApplication: Application) :
    AndroidViewModel(myApplication) {

    val date: MutableLiveData<String> = MutableLiveData()
    val time: MutableLiveData<String> = MutableLiveData()
    val hospital: MutableLiveData<String> = MutableLiveData()
    val dateListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            date.value = "${dayOfMonth}.${monthOfYear}.${year}"
        }

    val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        time.value = "${hourOfDay}:${minute}"
    }

    init {

    }

    fun clearTime(){
        time.value = ""
    }

    fun clearDate(){
        date.value = ""
    }

    fun clearHospital(){
        hospital.value = ""
    }

}