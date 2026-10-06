package com.example.lab4intents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var tvSelectedDate: TextView

    // Регистрируем лаунчер для получения результата из SecondActivity
    private val secondActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val millis = result.data?.getLongExtra("SELECTED_DATE_MILLIS", -1L) ?: -1L
            if (millis != -1L) {
                // Переводим мс в понятную строку формата "dd.MM.yyyy HH:mm"
                val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(millis))
                tvSelectedDate.text = "Выбранные дата и время:\n$formattedDate"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUserName = findViewById<EditText>(R.id.etUserName)
        val btnOpenSecond = findViewById<Button>(R.id.btnOpenSecond)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        btnOpenSecond.setOnClickListener {
            val name = etUserName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, введите имя", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Передаем имя во вторую Activity
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("USER_NAME", name)
            }
            secondActivityLauncher.launch(intent)
        }
    }
}