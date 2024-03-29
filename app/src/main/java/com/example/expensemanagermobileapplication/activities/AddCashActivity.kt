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
import com.example.expensemanagermobileapplication.databinding.ActivityAddCashBinding

class AddCashActivity : AppCompatActivity() {
    val nameofwallet by lazy { findViewById<EditText>(R.id.nameofwallet) }
    val amountofwallet by lazy { findViewById<EditText>(R.id.amounttxtplc) }
    val createbtn by lazy { findViewById<Button>(R.id.createbtn) }
    val spinner by lazy { findViewById<Spinner>(R.id.currenciesoptions) }
    lateinit var binding: ActivityAddCashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cash)

        val currencies = resources.getStringArray(R.array.Currencies)

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, currencies
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@AddCashActivity,
                        getString(R.string.currency_title) + " " +
                                "" + currencies[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(this@AddCashActivity, "Nothing selected!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        val sharedPref = getSharedPreferences("userOptions", MODE_PRIVATE)
        val editor = sharedPref.edit()

        createbtn.setOnClickListener {
            val nameOfWallet = nameofwallet.text.toString()
            val amountInWallet = amountofwallet.text.toString()
            val selectedCurrency = spinner.selectedItem.toString()

            if(nameOfWallet == "" || amountInWallet == "" || selectedCurrency == "Please Select") {
                Toast.makeText(this@AddCashActivity, "Nothing selected!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val id = sharedPref.getInt("walletID", 0) + 1

            editor.apply {
                putInt("walletID", id)
                putString("name_of_wallet_$id", nameOfWallet)
                putFloat("amount_in_wallet_$id", amountInWallet.toFloat())
                putString("currency_wallet_$id", selectedCurrency)
            }
            editor.apply()

            Toast.makeText(this@AddCashActivity, "Wallet added successfully!", Toast.LENGTH_LONG)
                .show()
            showCreateCashDialog()
        }
    }

    private fun showCreateCashDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_for_alert_dialog_cash_act)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btncreateanotherwallet : Button = dialog.findViewById(R.id.create_another_wallet)
        val btncreateacard : Button = dialog.findViewById(R.id.create_a_card)
        val btncontinuewithapp : Button = dialog.findViewById(R.id.continue_with_app)

        btncreateanotherwallet.setOnClickListener{
            nameofwallet.text.clear()
            amountofwallet.text.clear()
            dialog.dismiss()
        }

        btncreateacard.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
        }

        btncontinuewithapp.setOnClickListener {
            val intent = Intent(this, FirstPageActivity::class.java)
            startActivity(intent)
        }

        dialog.show()
    }
}