package com.zar.core.tools.api.interfaces

import com.zar.core.tools.api.enums.AuthorizationType
import com.zar.core.tools.api.enums.ErrorType

/**
 * Create by Mehrdad Latifi on 8/21/2022
 */

abstract class RemoteErrorEmitter {
    fun unAuthorization(type: AuthorizationType, message: String){}
    fun onError(errorType: ErrorType, message: String){}
}