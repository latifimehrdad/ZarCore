package com.zar.app

import android.os.Bundle
import android.widget.Button
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
import com.zar.core.BuildConfig
import com.zar.core.view.picker.date.customviews.DateRangeCalendarView
import com.zar.core.view.picker.date.dialog.DatePickerDialog
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn_Accept)
        btn.setOnClickListener {
            showDatePickerDialog()
        }

/*        val media = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "zarlog"
        )*/

//        val media = File(cacheDir,"zarlog")
/*        val files: Array<File> = media.listFiles() as Array<File>
        for (element in files) {
            Log.d("meri", "FileName:" + element.name)
            val temp = element.bufferedReader()
            val inputString = temp.use { it.readText() }
            Log.e("meri", inputString)
        }*/

/*        val config = LogConfiguration.Builder()
            .logLevel(
                if (BuildConfig.DEBUG) LogLevel.ALL // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                else LogLevel.ALL
            )
            .tag("X-LOG") // Specify TAG, default: "X-LOG"
            .enableStackTrace(1) // Enable stack trace info with depth 2, disabled by default
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
            filePrinter
        )

        XLog.d("this line is ok")*/

/*        XLog.d("hello xlog")
        XLog.d("hello word")
        XLog.d("hello mehrdad")*/
    }



    //---------------------------------------------------------------------------------------------- showDatePickerDialog
    private fun showDatePickerDialog() {

        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.selectionMode = DateRangeCalendarView.SelectionMode.Range
        datePickerDialog.isDisableDaysAgo = false
        datePickerDialog.acceptButtonColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, this.theme)
        datePickerDialog.headerBackgroundColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, this.theme)
        datePickerDialog.headerTextColor =
            resources.getColor(R.color.white, this.theme)
        datePickerDialog.weekColor =
            resources.getColor(R.color.dismiss, this.theme)
        datePickerDialog.disableDateColor =
            resources.getColor(R.color.dismiss, this.theme)
        datePickerDialog.defaultDateColor =
            resources.getColor(R.color.datePickerDateBackColor, this.theme)
        datePickerDialog.selectedDateCircleColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, this.theme)
        datePickerDialog.selectedDateColor =
            resources.getColor(R.color.white, this.theme)
        datePickerDialog.rangeDateColor =
            resources.getColor(R.color.datePickerConfirmButtonBackColor, this.theme)
        datePickerDialog.rangeStripColor =
            resources.getColor(R.color.datePickerRangeColor, this.theme)
        datePickerDialog.holidayColor =
            resources.getColor(R.color.rejectFactorText, this.theme)
        datePickerDialog.textSizeWeek = 12.0f
        datePickerDialog.textSizeDate = 14.0f
        datePickerDialog.textSizeTitle = 18.0f
        datePickerDialog.setCanceledOnTouchOutside(true)
        datePickerDialog.onSingleDateSelectedListener =
            DatePickerDialog.OnSingleDateSelectedListener { }
        datePickerDialog.onRangeDateSelectedListener =
            DatePickerDialog.OnRangeDateSelectedListener { startDate, endDate ->

                println("${startDate.persianLongDate} - ${endDate.persianLongDate}")
            }

        datePickerDialog.showDialog()

    }
    //---------------------------------------------------------------------------------------------- showDatePickerDialog

}