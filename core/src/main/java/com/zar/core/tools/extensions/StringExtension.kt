package com.zar.core.tools.extensions

/**
 * Create by Mehrdad Latifi on 8/6/2022
 */

import androidx.core.text.isDigitsOnly
import java.time.LocalDateTime

//-------------------------------------------------------------------------------------------------- persianNumberToEnglishNumber
fun String?.persianNumberToEnglishNumber() = this?.let {
    var stringNumber = ""
    for (c in it)
        when (c) {
            '۰' -> stringNumber += "0"
            '۱' -> stringNumber += "1"
            '۲' -> stringNumber += "2"
            '۳' -> stringNumber += "3"
            '۴' -> stringNumber += "4"
            '۵' -> stringNumber += "5"
            '۶' -> stringNumber += "6"
            '۷' -> stringNumber += "7"
            '۸' -> stringNumber += "8"
            '۹' -> stringNumber += "9"
            '٫' -> stringNumber += "."
            else -> stringNumber += c
        }
    return stringNumber
} ?: ""
//-------------------------------------------------------------------------------------------------- persianNumberToEnglishNumber


//-------------------------------------------------------------------------------------------------- split
fun String?.split() = this?.let {
    val number = it.replace(",", "", false).replace("٬", "", false)
    return if (number.isDigitsOnly()) {
        val d = number.toLong()
        d.split()
    } else {
        val d = number.toDoubleOrNull()
        if (d != null)
            return d.split()
        else
            return ""
    }
} ?: ""
//-------------------------------------------------------------------------------------------------- split


//-------------------------------------------------------------------------------------------------- solarDateToGregorianDate
fun String?.solarDateToGregorianDate() : LocalDateTime? = this?.let {
    if (it.length != 10)
        null
    else {
        var solarYear = it.subSequence(0, 4).toString().toInt()
        val solarMonth = it.subSequence(5, 7).toString().toInt()
        val solarDay = it.subSequence(8, 10).toString().toInt()

        solarYear += 1595
        var d =
            -355668 + (365 * solarYear) + ((solarYear / 33)* 8) + (((solarYear % 33) + 3) / 4) + solarDay
        d += if (solarMonth < 7)
            (solarMonth - 1) * 31
        else
            ((solarMonth - 7 ) * 30) + 186

        val out = arrayOf(0, 0, d)

        out[0] = 400 * (out[2] / 146097)
        out[2] %= 146097
        if (out[2] > 36524) {
            out[0] += 100 * (--out[2] / 36524)
            out[2] %= 36524
            if (out[2] >= 365)
                out[2]++
        }
        out[0] += 4 * (out[2] / 1461)
        out[2] %= 1461
        if (out[2] > 365) {
            out[0] += (out[2] - 1) / 365
            out[2] = (out[2] - 1) % 365
        }
        val sal = arrayOf(
            0,
            31,
            if (out[0] % 4 == 0 && out[0] % 100 != 0 || out[0] % 400 == 0) 29 else 28,
            31,
            30,
            31,
            30,
            31,
            31,
            30,
            31,
            30,
            31
        )
        out[2]++
        while (out[1] < 13 && out[2] > sal[out[1]]) {
            out[2] -= sal[out[1]]
            out[1]++
        }
        LocalDateTime.of(out[0], out[1], out[2], 0, 0)
    }
}
//-------------------------------------------------------------------------------------------------- solarDateToGregorianDate



