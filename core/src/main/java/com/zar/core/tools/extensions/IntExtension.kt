package com.zar.core.tools.extensions

/**
 * Create by Mehrdad Latifi on 8/6/2022
 */

import java.text.DecimalFormat


//-------------------------------------------------------------------------------------------------- split
fun Int?.split() = this?.let {
    val dec = DecimalFormat("#,###")
    dec.format(this)
} ?: ""
//-------------------------------------------------------------------------------------------------- split