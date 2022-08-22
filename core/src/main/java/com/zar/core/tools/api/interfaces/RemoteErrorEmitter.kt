package com.zar.core.tools.api.interfaces

import com.zar.core.enums.EnumAuthorizationType
import com.zar.core.enums.EnumErrorType

/**
 * Create by Mehrdad Latifi on 8/21/2022
 */

abstract class RemoteErrorEmitter {
    fun unAuthorization(type: EnumAuthorizationType, message: String){}
    fun onError(errorType: EnumErrorType, message: String){}
}