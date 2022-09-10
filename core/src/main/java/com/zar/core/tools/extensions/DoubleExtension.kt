package com.zar.core.tools.extensions

/**
 * Create by Mehrdad Latifi on 8/6/2022
 * Create by Mehrdad Latifi on 8/6/2022
 * Create by Mehrdad Latifi on 8/6/2022
 * Create by Mehrdad Latifi on 8/6/2022
 */

import java.text.DecimalFormat

//-------------------------------------------------------------------------------------------------- split
fun Double?.split() = this?.let {
    val dec = DecimalFormat("#,###.########")
    dec.format(it)
} ?: ""
//-------------------------------------------------------------------------------------------------- split
