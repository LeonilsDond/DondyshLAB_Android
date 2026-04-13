package com.example.labo3

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragRes : Fragment(R.layout.fragment_res) {

    interface OnCancelListener {
        fun cancelRequest()
    }

    private var listener: OnCancelListener? = null

    companion object {
        private const val RESULT = "RESULT"

        fun newInstance(resultText: String): FragRes {
            val fragment = FragRes()
            val args = Bundle()
            args.putString(RESULT, resultText)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnCancelListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showResult: TextView = view.findViewById(R.id.showChoice)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)

        showResult.text = arguments?.getString(RESULT, "")

        buttonCancel.setOnClickListener {
            listener?.cancelRequest()
        }
    }
}