package com.zar.app

import android.content.ContentValues
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  media = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "zarlog"
        )


        val config = LogConfiguration.Builder()
            .logLevel(
                if (BuildConfig.DEBUG) LogLevel.ALL // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                else LogLevel.NONE
            )
            .tag("X-LOG") // Specify TAG, default: "X-LOG"
            .enableThreadInfo() // Enable thread info, disabled by default
            .enableStackTrace(2) // Enable stack trace info with depth 2, disabled by default
            .enableBorder() // Enable border, disabled by default
            .build()

        val androidPrinter: Printer =
            AndroidPrinter(true) // Printer that print the log using android.util.Log

        val consolePrinter: Printer =
            ConsolePrinter() // Printer that print the log to console using System.out

        val filePrinter: Printer =
            FilePrinter.Builder(media.absolutePath) // Specify the directory path of log file(s)
                .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(NeverBackupStrategy()) // Default: FileSizeBackupStrategy(1024 * 1024)
                .build()


        XLog.init(                                                 // Initialize XLog
            config,                                                // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
            androidPrinter,                                        // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
            consolePrinter,
            filePrinter)



/*        Dexter.withContext(this)
            .withPermissions(
               Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }
            }).check()*/



        XLog.d("hello xlog")
        XLog.d("hello word")
        XLog.d("hello mehrdad")

//        val file = File(cacheDir, "xlog.txt")
//        if (file.exists()) {
//            val dir = File(cacheDir.toURI())
//            if (dir.exists()) {
//                for (f in dir.listFiles()) {
//                    Log.i("X-LOG", f.path)
//
//
//                }
//            }

//            Log.i("X-LOG", sp.toString())
//            val uri = FileProvider.getUriForFile(this, "com.zar.app.fileprovider", file)

//        }


    }
}