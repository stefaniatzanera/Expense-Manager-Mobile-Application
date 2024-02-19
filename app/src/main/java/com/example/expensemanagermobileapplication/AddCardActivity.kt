package com.example.expensemanagermobileapplication

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanagermobileapplication.databinding.ActivityAddCardBinding

class AddCardActivity : AppCompatActivity() {
    val nameofbankcard by lazy { findViewById<EditText>(R.id.nameofbankcardtxtplc) }
    val amountofbankcard by lazy { findViewById<EditText>(R.id.amounttxtplc) }
    val createbtn by lazy { findViewById<Button>(R.id.createbtn) }
    val spinner by lazy { findViewById<Spinner>(R.id.arrow) }
    lateinit var binding: ActivityAddCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

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
                        this@AddCardActivity,
                        getString(R.string.currency_title) + " " +
                                "" + currencies[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(this@AddCardActivity, "Nothing selected!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        val sharedPref = getSharedPreferences("userOptions", MODE_PRIVATE)
        val editor = sharedPref.edit()

        createbtn.setOnClickListener {
            val nameOfBank = nameofbankcard.text.toString()
            val amountInBank = amountofbankcard.text.toString()
            val selectedCurrency = spinner.selectedItem.toString()

            if(nameOfBank == "" || amountInBank == "" || selectedCurrency == "Please Select") {
                Toast.makeText(this@AddCardActivity, "Nothing selected!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }



            editor.apply {
                putInt("bankID", sharedPref.getInt("bankID", 0) + 1)
                putString("name_of_bank_" + sharedPref.getInt("bankID", 0), nameOfBank)
                putFloat("amount_in_bank_" + sharedPref.getInt("bankID", 0), amountInBank.toFloat())
                putString("currency_bank_" + sharedPref.getInt("bankID", 0), selectedCurrency)
            }
            editor.apply()

            Toast.makeText(this@AddCardActivity, "Card added successfully!", Toast.LENGTH_LONG)
                .show()
            //this@AddCardActivity.finish()

            showCreateCardDialog()
        }
    }

    private fun showCreateCardDialog() {
        val dialog = Dialog(this)
        //val alertDialogBuilder = AlertDialog.Builder(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_for_alert_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btncreateanothercard : Button = dialog.findViewById(R.id.create_another_card)
        val btncreateawallet : Button = dialog.findViewById(R.id.create_a_wallet)
        val btncontinuewithapp : Button = dialog.findViewById(R.id.continue_with_app)

        btncreateanothercard.setOnClickListener{
            nameofbankcard.text.clear()
                    amountofbankcard.text.clear()

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

//        alertDialogBuilder.setItems(optionsArray) {
//            dialogInterface: DialogInterface, which: Int ->
//            when (which) {
//                0 -> {
//                    nameofbankcard.text.clear()
//                    amountofbankcard.text.clear()
//                    spinner.setPromptId(0)
//
//                    dialogInterface.dismiss()
//                }
//                1 -> {
//                    val intent = Intent(this, AddCashActivity::class.java)
//                    startActivity(intent)
//                }
//                2 -> {
//                    val intent = Intent(this, FirstPageActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//            dialogInterface.dismiss()
//        }
//
//        alertDialogBuilder.setCancelable(false)
//        alertDialogBuilder.create().show()
        }
}