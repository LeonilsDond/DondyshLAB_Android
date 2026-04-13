package com.example.labo3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OpenVault : AppCompatActivity() {

    private lateinit var showData: TextView
    private lateinit var buttonBack: Button
    private lateinit var buttonClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        showData = findViewById(R.id.showData)
        buttonBack = findViewById(R.id.buttonReturn)
        buttonClear = findViewById(R.id.buttonClear)

        updateData()

        buttonClear.setOnClickListener {
            val cleared = ForFiles.dataCleaner(this)

            if (cleared) {
                showData.text = "Сховище порожнє"
                Toast.makeText(
                    this,
                    "Сховище очищено",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Помилка при очищенні сховища",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun updateData() {
        val savedData = ForFiles.dataReader(this)

        showData.text = if (savedData.isNullOrBlank()) {
            "Сховище порожнє"
        } else {
            savedData
        }
    }
}