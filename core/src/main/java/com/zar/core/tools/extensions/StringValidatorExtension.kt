package com.zar.core.tools.extensions

/**
 * Create by Mehrdad Latifi on 8/6/2022
 */

import androidx.core.text.isDigitsOnly
import java.math.BigInteger

//-------------------------------------------------------------------------------------------------- isMobile
fun String?.isMobile() = this?.matches(Regex("^(09)\\d{9}")) ?: false
//-------------------------------------------------------------------------------------------------- isMobile


//-------------------------------------------------------------------------------------------------- isPhone
fun String?.isPhone() = this?.matches(Regex("^(0)([1-8])\\d{9}")) ?: false
//-------------------------------------------------------------------------------------------------- isPhone


//-------------------------------------------------------------------------------------------------- isEmail
fun String?.isEmail() = this?.matches(Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})")) ?: false
//-------------------------------------------------------------------------------------------------- isEmail


//-------------------------------------------------------------------------------------------------- isStrongPassword
fun String?.isStrongPassword() = this?.matches(Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#\$%^&+=!]).{6,}\$")) ?: false
//-------------------------------------------------------------------------------------------------- isStrongPassword


//-------------------------------------------------------------------------------------------------- isSignsInPassword
fun String?.isSignsInPassword() = this?.matches(Regex("^(?=.*[@#\$%^&+=!]).+\$")) ?: false
//-------------------------------------------------------------------------------------------------- isSignsInPassword


//-------------------------------------------------------------------------------------------------- isUppercaseInPassword
fun String?.isUppercaseInPassword() = this?.matches(Regex("^(?=.*[A-Z]).+\$")) ?: false
//-------------------------------------------------------------------------------------------------- isUppercaseInPassword


//-------------------------------------------------------------------------------------------------- isNumberInPassword
fun String?.isNumberInPassword() = this?.matches(Regex("^(?=.*[0-9]).+\$")) ?: false
//-------------------------------------------------------------------------------------------------- isNumberInPassword


//-------------------------------------------------------------------------------------------------- isLowerInPassword
fun String?.isLowerInPassword() = this?.matches(Regex("^(?=.*[a-z]).+\$")) ?: false
//-------------------------------------------------------------------------------------------------- isLowerInPassword


//-------------------------------------------------------------------------------------------------- isPostalCode
fun String?.isPostalCode() = this?.matches(Regex("\\b(?!(\\d)\\1{3})[13-9]{4}[1346-9][013-9]{5}\\b")) ?: false
//-------------------------------------------------------------------------------------------------- isPostalCode


//-------------------------------------------------------------------------------------------------- isPersianName
fun String?.isPersianName() = this?.matches(Regex("^[آ-ی]*$")) ?: false
//-------------------------------------------------------------------------------------------------- isPersianName


//-------------------------------------------------------------------------------------------------- isPersianNumber
fun String?.isPersianNumber() = this?.matches(Regex("^(?=.*[۰-۹]).+\$")) ?: false
//-------------------------------------------------------------------------------------------------- isPersianNumber


//-------------------------------------------------------------------------------------------------- isNationalCode
fun String?.isNationalCode() = this?.let {
    if (!it.isDigitsOnly())
        return false

    if (it.length != 10)
        return false

    val revers = it.reversed()
    val arrayNationalCode: Array<Int> =
        revers.toCharArray().map {map -> map.toString().toInt() }.toTypedArray()

    var sum = 0
    for (i in 9 downTo 1)
        sum += arrayNationalCode[i] * (i + 1)
    val temp = sum % 11
    if (temp < 2)
        arrayNationalCode[0] == temp
    else
        arrayNationalCode[0] == 11 - temp
} ?: false
//-------------------------------------------------------------------------------------------------- isNationalCode


//-------------------------------------------------------------------------------------------------- isShabaWithoutIR
fun String?.isShabaWithoutIR() = this?.let {
    if (!it.isDigitsOnly())
        return false

    if (it.length != 24)
        return false

    var place1 = "IR" + it.subSequence(0, 2).toString()
    var place2 = it.subSequence(2, 24).toString()
    place1 = place1.replace("IR", "1827", true)
    place2 += place1
    val big = place2.toBigInteger()
    big.mod(BigInteger.valueOf(97)).toInt() == 1
} ?: false
//-------------------------------------------------------------------------------------------------- isShabaWithoutIR


//-------------------------------------------------------------------------------------------------- isCardNumber
fun String?.isCardNumber() = this?.let {
    if (!it.isDigitsOnly())
        return false

    if (it.length != 16)
        return false

    val arrayCard: Array<Int> =
        it.toCharArray().map { it.toString().toInt() }.toTypedArray()

    var cardTotal = 0

    for (i in arrayCard.indices)
        cardTotal += if (i % 2 == 0)
            if (arrayCard[i] * 2 > 9) arrayCard[i] * 2 - 9 else arrayCard[i] * 2
        else
            arrayCard[i]
    cardTotal % 10 == 0
} ?: false
//-------------------------------------------------------------------------------------------------- isCardNumber







