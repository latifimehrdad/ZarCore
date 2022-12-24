package com.zar.core.models

import com.zar.core.enums.EnumApiError

data class ErrorApiModel(
    val type : EnumApiError,
    val message : String
)
