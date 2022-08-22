package com.zar.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zar.core.tools.extensions.toSolarDate
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val s: LocalDateTime? = LocalDateTime.of(1988,8,22,0,0,0)
        Toast.makeText(this, "" + s.toSolarDate()?.getFullDate(), Toast.LENGTH_LONG).show()
    }
}