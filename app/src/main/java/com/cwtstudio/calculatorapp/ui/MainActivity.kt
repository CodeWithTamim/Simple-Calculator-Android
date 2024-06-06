package com.cwtstudio.calculatorapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cwtstudio.calculatorapp.R
import com.cwtstudio.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var isParenthesisOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()

        binding.btnAc.setOnClickListener {
            binding.txtCalculations.text = ""
            binding.txtResult.text = getString(R.string.label_zero)
            isParenthesisOpen = false
        }


        binding.btnEquals.setOnClickListener {
            calculate()
        }


    }

    /**
     * here we setup the listeners that will use the
     * overridden onClick function
     */

    private fun setListeners() {
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnZero.setOnClickListener(this)
        binding.btnParenthesis.setOnClickListener(this)
        binding.btnPercent.setOnClickListener(this)
        binding.btnDevide.setOnClickListener(this)
        binding.btnMultiply.setOnClickListener(this)
        binding.btnMinus.setOnClickListener(this)
        binding.btnDot.setOnClickListener(this)
        binding.btnPlus.setOnClickListener(this)
    }

    /**
     * overridden function from the View.OnClickListener interface.
     * here we get a view if any button is clicked, and use a when
     * statement to match the action according to the view id
     */


    override fun onClick(v: View?) {

        when (v?.id) {

            binding.btn1.id -> appendCalculations(binding.btn1.text.toString())

            binding.btn2.id -> appendCalculations(binding.btn2.text.toString())

            binding.btn3.id -> appendCalculations(binding.btn3.text.toString())

            binding.btn4.id -> appendCalculations(binding.btn4.text.toString())

            binding.btn5.id -> appendCalculations(binding.btn5.text.toString())

            binding.btn6.id -> appendCalculations(binding.btn6.text.toString())

            binding.btn7.id -> appendCalculations(binding.btn7.text.toString())

            binding.btn8.id -> appendCalculations(binding.btn8.text.toString())

            binding.btn9.id -> appendCalculations(binding.btn9.text.toString())

            binding.btnZero.id -> appendCalculations(binding.btnZero.text.toString())

            binding.btnParenthesis.id -> {
                toggleParenthesis()
            }

            binding.btnPercent.id -> appendCalculations(binding.btnPercent.text.toString())

            binding.btnMultiply.id -> appendCalculations(binding.btnMultiply.text.toString())
            binding.btnMinus.id -> appendCalculations(binding.btnMinus.text.toString())

            binding.btnPlus.id -> appendCalculations(binding.btnPlus.text.toString())

            binding.btnDot.id -> appendCalculations(binding.btnDot.text.toString())

            binding.btnDevide.id -> appendCalculations(binding.btnDevide.text.toString())


        }

    }

    /**
     * function to calculate and set the result to the view.
     * so it works like this: we get the a string of calculations(eg. "1+1") from
     * the txtCalculations okay? so then we replace its signs with our kotlin operators.
     * then we pass the string inside the getresult function and set the result in the
     * view
     */


    private fun calculate() {
        var calculation: String = binding.txtCalculations.text.toString()
        if (calculation.contains("%")) {
            calculation = calculation.replace("%", "/100*")
        }
        if (calculation.contains("×")) {
            calculation = calculation.replace("×", "*")
        }
        if (calculation.contains("÷")) {
            calculation = calculation.replace("÷", "/")
        }

        val result: String = getResult(calculation)
        binding.txtResult.text = result


    }

    /**
     * function to get the calculations result.
     * so here we can pass the data in string format.
     * like "1+1" and it will return us 2 as string.
     */

    private fun getResult(data: String): String {
        try {
            if (data.isEmpty()) {
                binding.txtResult.text = ""
                throw Exception()
            }
            val context: org.mozilla.javascript.Context = org.mozilla.javascript.Context.enter()
            context.optimizationLevel = -1
            val scriptable = context.initStandardObjects()
            var finalResult =
                context.evaluateString(scriptable, data, "Javascript", 1, null).toString()
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "")
            }
            return finalResult
        } catch (e: Exception) {
            e.printStackTrace()
            binding.txtCalculations.text = ""
            return "Error"
        }
    }

    /**
     * function to manage the toggle of
     * the parenthesis. Means if its not used it will open
     * and if its opened it will be closed. Managed with simple
     * boolean flag value
     */

    private fun toggleParenthesis() {
        val text: String
        if (isParenthesisOpen) {
            text = ")"
            isParenthesisOpen = false
        } else {
            text = "("
            isParenthesisOpen = true
        }
        appendCalculations(text)
    }

    /**
     * function to append the calculations
     * on the text view when any button is clicked.
     * we will get the text from txtCalculations and concatenate
     * with the argument string called text and assign
     * to the txtCalculations or we can say setText to it.
     */

    @SuppressLint("SetTextI18n")
    private fun appendCalculations(text: String) {
        binding.txtCalculations.text = binding.txtCalculations.text.toString() + text
    }

}