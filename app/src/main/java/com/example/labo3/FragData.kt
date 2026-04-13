package com.example.labo3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragData : Fragment(R.layout.fragment_data) {

    interface OnDataSubmitListener {
        fun dataProvided(resultText: String)
    }

    private var listener: OnDataSubmitListener? = null

    private lateinit var spinnerDishes: Spinner
    private lateinit var Krauff: CheckBox
    private lateinit var Maestro: CheckBox
    private lateinit var Ikea: CheckBox
    private lateinit var VilleroyBoch: CheckBox

    private lateinit var priceLow: CheckBox
    private lateinit var priceMedium: CheckBox
    private lateinit var priceHigh: CheckBox

    private lateinit var buttonOk: Button
    private lateinit var buttonOpen: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnDataSubmitListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerDishes = view.findViewById(R.id.spinnerDish)

        Krauff = view.findViewById(R.id.Krauff)
        Maestro = view.findViewById(R.id.Maestro)
        Ikea = view.findViewById(R.id.Ikea)
        VilleroyBoch = view.findViewById(R.id.VilleroyBoch)

        priceLow = view.findViewById(R.id.priceLow)
        priceMedium = view.findViewById(R.id.priceMedium)
        priceHigh = view.findViewById(R.id.priceHigh)

        buttonOk = view.findViewById(R.id.buttonOk)
        buttonOpen = view.findViewById(R.id.buttonOpen)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dishes_choice,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDishes.adapter = adapter

        buttonOk.setOnClickListener {
            showChoice()
        }

        buttonOpen.setOnClickListener {
            val intent = Intent(requireContext(), OpenVault::class.java)
            startActivity(intent)
        }
    }

    private fun showChoice() {
        val selectedDishPosition = spinnerDishes.selectedItemPosition
        val selectedDish = spinnerDishes.selectedItem.toString()

        val brands = mutableListOf<String>()
        val prices = mutableListOf<String>()

        if (Krauff.isChecked) brands.add(Krauff.text.toString())
        if (Maestro.isChecked) brands.add(Maestro.text.toString())
        if (Ikea.isChecked) brands.add(Ikea.text.toString())
        if (VilleroyBoch.isChecked) brands.add(VilleroyBoch.text.toString())

        if (priceLow.isChecked) prices.add(priceLow.text.toString())
        if (priceMedium.isChecked) prices.add(priceMedium.text.toString())
        if (priceHigh.isChecked) prices.add(priceHigh.text.toString())

        if (selectedDishPosition == 0 || brands.isEmpty() || prices.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Помилка! Завершіть введення всіх даних",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val result = """
            Обраний посуд: $selectedDish
            
            Обрані бренди:
            ${brands.joinToString(", ")}
            
            Обраний діапазон цін:
            ${prices.joinToString(", ")}
        """.trimIndent()

        listener?.dataProvided(result)

        val saved = ForFiles.dataSaver(requireContext(), result)

        if (saved) {
            Toast.makeText(
                requireContext(),
                "Дані успішно записані у сховище",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Помилка запису у сховище",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun clearForm() {
        spinnerDishes.setSelection(0)

        Krauff.isChecked = false
        Maestro.isChecked = false
        Ikea.isChecked = false
        VilleroyBoch.isChecked = false

        priceLow.isChecked = false
        priceMedium.isChecked = false
        priceHigh.isChecked = false
    }
}