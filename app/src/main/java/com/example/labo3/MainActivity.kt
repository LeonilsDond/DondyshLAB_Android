package com.example.labo3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    FragData.OnDataSubmitListener,
    FragRes.OnCancelListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.data, FragData())
                .commit()
        }
    }

    override fun dataProvided(resultText: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.result, FragRes.newInstance(resultText))
            .commit()
    }

    override fun cancelRequest() {
        val resultFragment = supportFragmentManager.findFragmentById(R.id.result)
        if (resultFragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(resultFragment)
                .commit()
        }

        val dataFragment =
            supportFragmentManager.findFragmentById(R.id.data) as? FragData
        dataFragment?.clearForm()
    }
}