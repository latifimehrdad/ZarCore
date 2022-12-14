package com.zar.core.models

/**
 * Create by Mehrdad Latifi on 8/6/2022
 * Create by Mehrdad Latifi on 8/6/2022
 *
 */


import java.time.DayOfWeek

class SolarDateModel(
    private val year: Int,
    private val month: Int,
    private val day: Int,
    private val dayOfWeek: DayOfWeek
) {

    //---------------------------------------------------------------------------------------------- getMonthTitle
    fun getMonthTitle(): String {
        return when (month) {
            1 -> "فروردین"
            2 -> "ارديبهشت"
            3 -> "خرداد"
            4 -> "تير"
            5 -> "مرداد"
            6 -> "شهريور"
            7 -> "مهر"
            8 -> "آبان"
            9 -> "آذر"
            10 -> "دي"
            11 -> "بهمن"
            12 -> "اسفند"
            else -> ""
        }
    }
    //---------------------------------------------------------------------------------------------- getMonthTitle


    //---------------------------------------------------------------------------------------------- getDayOfWeekTitle
    fun getDayOfWeekTitle(): String {
        return when (dayOfWeek) {
            DayOfWeek.SUNDAY -> "یکشنبه"
            DayOfWeek.MONDAY -> "دوشنبه"
            DayOfWeek.TUESDAY -> "سه شنبه"
            DayOfWeek.WEDNESDAY -> "چهارشنبه"
            DayOfWeek.THURSDAY -> "پنج شنبه"
            DayOfWeek.FRIDAY -> "جمعه"
            DayOfWeek.SATURDAY -> "شنبه"
            else -> ""
        }
    }
    //---------------------------------------------------------------------------------------------- getDayOfWeekTitle


    //---------------------------------------------------------------------------------------------- getYearString
    fun getYearString(): String {
        return year.toString()
    }
    //---------------------------------------------------------------------------------------------- getYearString


    //---------------------------------------------------------------------------------------------- getMonthString
    fun getMonthString(): String {
        return month.toString().padStart(2, '0')
    }
    //---------------------------------------------------------------------------------------------- getMonthString


    //---------------------------------------------------------------------------------------------- getDayString
    fun getDayString(): String {
        return day.toString().padStart(2, '0')
    }
    //---------------------------------------------------------------------------------------------- getDayString


    //---------------------------------------------------------------------------------------------- getSolarDate
    fun getSolarDate(): String {
        return "${getYearString()}/${getMonthString()}/${getDayString()}"
    }
    //---------------------------------------------------------------------------------------------- getSolarDate


    //---------------------------------------------------------------------------------------------- getFullDate
    fun getFullDate(): String {
        return getDayOfWeekTitle() + " " + getDayString() + " " + getMonthTitle() + " " + getYearString()
    }
    //---------------------------------------------------------------------------------------------- getFullDate


    //---------------------------------------------------------------------------------------------- getYear
    fun getYear(): Int {
        return year
    }
    //---------------------------------------------------------------------------------------------- getYear



    //---------------------------------------------------------------------------------------------- getMonth
    fun getMonth(): Int {
        return month
    }
    //---------------------------------------------------------------------------------------------- getMonth


    //---------------------------------------------------------------------------------------------- getDay
    fun getDay(): Int {
        return day
    }
    //---------------------------------------------------------------------------------------------- getDay



}
