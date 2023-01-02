package com.example.pro_reproductivehealth.models

import com.example.pro_reproductivehealth.enums.DaysOfWeek

data class Doctor(
    val fio: String,
    val workingDays: List<DaysOfWeek>,
    val startWorkingTime: String,
    val endWorkingTime: String,
    val durationOfReception: Int
){
    fun getSecondName():String{
        return fio.split(" ")[0]
    }

    fun getFirstNameAndSurname():String{
        val fioList = fio.split(" ")
        return "${fioList[1]} ${fioList[2]}"
    }
}
