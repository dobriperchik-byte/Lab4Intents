package com.example.lab4intents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        val btnOk = findViewById<Button>(R.id.btnOk)

        // 1. Извлекаем имя, полученное из MainActivity
        val userName = intent.getStringExtra("USER_NAME") ?: "Пользователь"
        tvWelcome.text = "Привет, $userName!"

        // 2. Нажатие кнопки ОК
        btnOk.setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, datePicker.year)
                set(Calendar.MONTH, datePicker.month)
                set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                set(Calendar.HOUR_OF_DAY, timePicker.hour)
                set(Calendar.MINUTE, timePicker.minute)
                set(Calendar.SECOND, 0)
            }

            // Переводим дату в миллисекунды (Long)
            val selectedMillis = calendar.timeInMillis

            // Возвращаем результат в первую Activity
            val resultIntent = Intent().apply {
                putExtra("SELECTED_DATE_MILLIS", selectedMillis)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Закрываем экран
        }
    }
}