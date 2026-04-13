package com.example.labo3


import android.content.Context
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ForFiles {

    private const val FILE_NAME = "saved.txt"

    fun dataSaver(context: Context, text: String): Boolean {
        return try {
            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            val currentTime = formatter.format(Date())

            context.openFileOutput(FILE_NAME, Context.MODE_APPEND).bufferedWriter().use { writer ->
                writer.append("Час додавання: $currentTime")
                writer.append("\n")
                writer.append("\n")
                writer.append(text)
                writer.append("\n")
                writer.append("____________________________________________________")
                writer.append("\n")
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun dataReader(context: Context): String? {
        return try {
            val text = context.openFileInput(FILE_NAME).bufferedReader().use { it.readText() }
            if (text.isBlank()) null else text
        } catch (e: FileNotFoundException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    fun dataCleaner(context: Context): Boolean {
        return try {
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { }
            true
        } catch (e: Exception) {
            false
        }
    }
}