package com.zar.core.tools.extensions

/**
 * Create by Mehrdad Latifi on 8/6/2022
 */

import com.zar.core.models.SolarDateModel
import java.time.LocalDateTime


//-------------------------------------------------------------------------------------------------- toSolarDate
fun LocalDateTime?.toSolarDate(): SolarDateModel? = this?.let {
    var solarDay: Int
    val solarMonth: Int
    val solarYear: Int
    val ld: Int

    val gregorianYear = it.year
    val gregorianMonth = it.monthValue
    val gregorianDay = it.dayOfMonth
    val dayOfWeek = it.dayOfWeek

    val buf1 = arrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
    val buf2 = arrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335)

    if (gregorianYear % 4 != 0) {
        solarDay = buf1[gregorianMonth - 1] + gregorianDay
        if (solarDay > 79) {
            solarDay -= 79
            if (solarDay <= 186) {
                when (solarDay % 31) {
                    0 -> {
                        solarMonth = solarDay / 31
                        solarDay = 31
                    }
                    else -> {
                        solarMonth = (solarDay / 31) + 1
                        solarDay = (solarDay % 31)
                    }
                }
                solarYear = gregorianYear - 621
            } else {
                solarDay -= 186
                when (solarDay % 30) {
                    0 -> {
                        solarMonth = (solarDay / 30) + 6
                        solarDay = 30
                    }
                    else -> {
                        solarMonth = (solarDay / 30) + 7
                        solarDay = (solarDay % 30)
                    }
                }
                solarYear = gregorianYear - 621
            }
        } else {
            ld = if (gregorianYear > 1996 && gregorianYear % 4 == 1)
                11
            else
                10
            solarDay += ld
            when (solarDay % 30) {
                0 -> {
                    solarMonth = (solarDay / 30) + 9
                    solarDay = 30
                }
                else -> {
                    solarMonth = (solarDay / 30) + 10
                    solarDay %= 30
                }
            }
            solarYear = gregorianYear - 622
        }
    }
    else {
        solarDay = buf2[gregorianMonth - 1] + gregorianDay
        ld = if (gregorianYear >= 1996)
            79
        else
            80
        if (solarDay > ld) {
            solarDay -= ld
            if (solarDay <= 186) {
                when (solarDay % 31) {
                    0 -> {
                        solarMonth = (solarDay / 31)
                        solarDay = 31
                    }
                    else -> {
                        solarMonth = (solarDay / 31) + 1
                        solarDay %= 31
                    }
                }
                solarYear = gregorianYear - 621
            } else {
                solarDay -= 186
                when (solarDay % 30) {
                    0 -> {
                        solarMonth = (solarDay / 30) + 6
                        solarDay %= 30
                    }
                    else -> {
                        solarMonth = (solarDay / 30) + 7
                        solarDay %= 30
                    }
                }
                solarYear = gregorianYear - 621
            }
        }
        else {
            solarDay += 10
            when (solarDay % 30) {
                0 -> {
                    solarMonth = (solarDay / 30) + 10
                    solarDay %= 30
                }
                else -> {
                    solarMonth = (solarDay / 30) + 10
                    solarDay %= 30
                }
            }
            solarYear = gregorianYear - 622
        }

    }

    SolarDateModel(solarYear, solarMonth, solarDay, dayOfWeek)
}
//-------------------------------------------------------------------------------------------------- toSolarDate