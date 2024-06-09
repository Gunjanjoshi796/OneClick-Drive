package com.example.oneclickdrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.oneclickdrive.helper.CalculateListener
import com.example.oneclickdrive.helper.Util
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var listOneIL: TextInputLayout
    private lateinit var listTwoIL: TextInputLayout
    private lateinit var listThreeIL: TextInputLayout

    private lateinit var intersectingTV: TextView
    private lateinit var unionTV: TextView
    private lateinit var maxTV: TextView

    private lateinit var infoCard: CardView

    private lateinit var calculateBTN: Button

    private var SHOULD_REFRESH = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inItWidgets()

        setTextAndFocusListener(listOneIL)
        setTextAndFocusListener(listTwoIL)
        setTextAndFocusListener(listThreeIL)

        calculateBTN.setOnClickListener {
            if (SHOULD_REFRESH){
                listOneIL.editText?.setText("")
                listTwoIL.editText?.setText("")
                listThreeIL.editText?.setText("")

                calculateBTN.text = "Calculate"
                infoCard.visibility = View.GONE

                SHOULD_REFRESH = false
                return@setOnClickListener
            }

            var hasError = false
            listOneIL.editText!!.clearFocus()
            listTwoIL.editText!!.clearFocus()
            listThreeIL.editText!!.clearFocus()

            if (listOneIL.editText!!.text.toString().isEmpty()){
                listOneIL.error = "Please enter numbers here"
               hasError = true
            }
            if (listTwoIL.editText!!.text.toString().isEmpty()){
                listTwoIL.error = "Please enter numbers here"
                hasError = true
            }
            if (listThreeIL.editText!!.text.toString().isEmpty()){
                listThreeIL.error = "Please enter numbers here"
                hasError = true
            }

            if (hasError){
                return@setOnClickListener
            }

            SHOULD_REFRESH = true
            infoCard.visibility = View.VISIBLE
            calculateBTN.text = "Refresh"
            Util().calculateList(listOneIL.editText!!.text.toString(), listTwoIL.editText!!.text.toString(), listThreeIL.editText!!.text.toString(), object : CalculateListener{
                override fun getIntersectNumbers(intersectNumbers: String) {
                    intersectingTV.text = intersectNumbers
                    Log.e("largestNumber", "getIntersectNumbers: $intersectNumbers")
                }

                override fun getUnionOfNumbers(unionOfNumbers: String) {
                    unionTV.text = unionOfNumbers
                    Log.e("largestNumber", "getUnionOfNumbers: $unionOfNumbers")
                }

                override fun getLargestNumber(largestNumber: String) {
                    maxTV.text = largestNumber
                    Log.e("largestNumber", "getLargestNumber: $largestNumber")
                }

            })
        }
    }

    private fun setTextAndFocusListener(inputLayout: TextInputLayout) {
        inputLayout.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputLayout.error = null
            }
        }

        inputLayout.editText?.addTextChangedListener(object : TextWatcher {
            var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                val input = s.toString()

                val newText = Util().getFiltereString(input)

                if (input != newText) {
                    isUpdating = true
                    inputLayout.editText?.setText(newText)
                    inputLayout.editText?.setSelection(newText.length)
                    isUpdating = false
                }
            }

        })
    }

    private fun inItWidgets() {
        listOneIL = findViewById(R.id.number_list_one_il)
        listTwoIL = findViewById(R.id.number_list_two_il)
        listThreeIL = findViewById(R.id.number_list_three_il)

        intersectingTV = findViewById(R.id.intersecting_tv)
        unionTV = findViewById(R.id.union_tv)
        maxTV = findViewById(R.id.max_tv)

        infoCard = findViewById(R.id.info_card)

        calculateBTN = findViewById(R.id.calculate_btn)
    }
}