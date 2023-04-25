package com.zar.core.models


/**
 * create by m-latifi on 4/25/2023
 */

data class ProgressResponseModel(
    val bytesRead: Long,
    val contentLength: Long,
    val isDone: Boolean
)