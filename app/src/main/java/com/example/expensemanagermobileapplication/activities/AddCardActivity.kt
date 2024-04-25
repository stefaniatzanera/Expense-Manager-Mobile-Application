package com.example.expensemanagermobileapplication.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.databinding.ActivityAddCardBinding
import com.google.android.material.textfield.TextInputEditText


class AddCardActivity : AppCompatActivity() {
    val nameofbankcard by lazy { findViewById<TextInputEditText>(R.id.nameofbankcardtxtplc) }
    val amountofbankcard by lazy { findViewById<TextInputEditText>(R.id.amounttxtplc) }
    val createbtn by lazy { findViewById<Button>(R.id.createbtn) }
    val spinner by lazy { findViewById<Spinner>(R.id.currenciesoptions) }
    val placeholderTextView by lazy {findViewById<TextView>(R.id.placeholder) }
    lateinit var binding: ActivityAddCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        var userInteraction = false

        val currencies = resources.getStringArray(R.array.Currencies)

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, currencies
            )
            spinner.adapter = adapter
        }

        val sharedPref = getSharedPreferences("userOptions", MODE_PRIVATE)
        val editor = sharedPref.edit()

        createbtn.setOnClickListener {
            val nameOfBank = nameofbankcard.text.toString()
            val amountInBank = amountofbankcard.text.toString()
            val selectedCurrency = spinner.selectedItem.toString()

            if (nameOfBank == "" || amountInBank == "" || selectedCurrency == "Please Select") {
                Toast.makeText(this@AddCardActivity, "Nothing selected!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val id = sharedPref.getInt("bankID", 0) + 1

            editor.apply {
                putInt("bankID", id)
                putString("name_of_bank_$id", nameOfBank)
                putFloat("amount_in_bank_$id", amountInBank.toFloat())
                putString("currency_bank_$id", selectedCurrency)
            }
            editor.apply()

            Toast.makeText(this@AddCardActivity, "Card added successfully!", Toast.LENGTH_LONG).show()
            spinner.setSelection(0)
            placeholderTextView.text = getString(R.string.currency_title)
            showCreateCardDialog()
        }

        placeholderTextView.setOnClickListener {
            spinner.performClick()
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position != 0)
                    placeholderTextView.text = currencies[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun showCreateCardDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_for_alert_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btncreateanothercard : Button = dialog.findViewById(R.id.create_another_card)
        val btncreateawallet : Button = dialog.findViewById(R.id.create_a_wallet)
        val btncontinuewithapp : Button = dialog.findViewById(R.id.continue_with_app)

        btncreateanothercard.setOnClickListener{
            nameofbankcard.text?.clear()
                    amountofbankcard.text?.clear()
                    dialog.dismiss()
        }

        btncreateawallet.setOnClickListener {
            val intent = Intent(this, AddCashActivity::class.java)
            startActivity(intent)
        }

        btncontinuewithapp.setOnClickListener {
            val intent = Intent(this, FirstPageActivity::class.java)
            startActivity(intent)
        }

        dialog.show()
    }
}