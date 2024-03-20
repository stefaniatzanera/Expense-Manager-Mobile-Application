package com.example.expensemanagermobileapplication.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.expensemanagermobileapplication.R
class AddBtnActivity : AppCompatActivity() {
    private val title by lazy { findViewById<TextView>(R.id.addbtntitle) }
    private val nameofdata by lazy { findViewById<TextView>(R.id.nameofdata) }
    val qrbtn by lazy { findViewById<ImageButton>(R.id.qrbtn) }
    val addbtn by lazy { findViewById<ImageButton>(R.id.addbtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_btn)

        val name = intent.getStringExtra("name")
        title.text = getString(R.string.addbtntitle, name)
        val amount = intent.getStringExtra("amount")
        val currency = intent.getStringExtra("currency")
        val keyString = intent.getStringExtra("key")!!


        val dialog = Dialog(this)
        addbtn.setOnClickListener {
            dialog.setContentView(R.layout.layout_for_alert_dialog_add_manual_amount)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val amountofdata = dialog.findViewById<TextView>(R.id.availableamount)
            val currencydata = dialog.findViewById<TextView>(R.id.cur)
            amountofdata.text = getString(R.string.availableamount, amount)
            currencydata.text = getString(R.string.currency,currency)

            val okaybtn by lazy { dialog.findViewById<Button>(R.id.okbtn) }
            val cancelbtn by lazy { dialog.findViewById<Button>(R.id.cancelbtn) }

            addbtn.setOnClickListener {
                val enteranamount = dialog.findViewById<EditText>(R.id.enteranamount)
                val enteredamount = enteranamount.text.toString() // the amount which user entered
                val availableamount = dialog.findViewById<TextView>(R.id.availableamount)
                availableamount.text = getString(R.string.availableamount, amount)
                val nameofcurrency = dialog.findViewById<TextView>(R.id.cur)
                nameofcurrency.text = getString(R.string.currency,currencydata)

            }
            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }
    }

    private fun addtheamount(enteredamount : Float, existedamount : Float): Float {
        val result = existedamount + enteredamount
        return if (result >= 0) {
            result // Update existedAmount only if result is non-negative
        } else {
            existedamount // Keep existedAmount unchanged if result is negative
        }
    }
}

