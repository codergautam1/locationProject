package com.example.locationproject

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by King Coder On 01-01-2022
 **/
fun getLastSevenDate():String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date(System.currentTimeMillis() - 604800000L))
}

fun changeAadharNumber(aadharNumber:String):String {
    return  aadharNumber.replace("\\w(?=\\w{4})".toRegex(), "*")
}

fun main() {
    println(getLastSevenDate())
    println(changeAadharNumber("1201313032112"))
}