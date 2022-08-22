package com.zar.core.tools.api

import android.os.Looper
import com.zar.core.tools.api.interfaces.UploadCallBack
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

/**
 * Create by Mehrdad Latifi on 8/21/2022
 */


class ProgressRequestBodyManager(
    private val file: File,
    private val contentType: String,
    private val uploadCallBack: UploadCallBack
) : RequestBody() {


    private val bufferSize = 2048


    //---------------------------------------------------------------------------------------------- contentType
    override fun contentType(): MediaType? {
        return "$contentType/*".toMediaTypeOrNull()
    }
    //---------------------------------------------------------------------------------------------- contentType


    //---------------------------------------------------------------------------------------------- contentLength
    override fun contentLength(): Long {
        return file.length()
    }
    //---------------------------------------------------------------------------------------------- contentLength


    //---------------------------------------------------------------------------------------------- writeTo
    override fun writeTo(sink: BufferedSink) {
        val buffer = ByteArray(bufferSize)
        val fin = FileInputStream(file)
        var upload: Long = 0
        var read: Int
        val handle = android.os.Handler(Looper.getMainLooper())
        read = fin.read(buffer)
        while (read != -1) {
            handle.post {
                uploadCallBack.onUploadPercent((100 * upload / file.length()).toInt())
            }
            upload += read
            sink.write(buffer, 0, read)
            read = fin.read(buffer)
        }
    }
    //---------------------------------------------------------------------------------------------- writeTo

}
