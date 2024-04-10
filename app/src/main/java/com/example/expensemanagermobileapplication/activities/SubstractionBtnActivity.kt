package com.example.expensemanagermobileapplication.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.expensemanagermobileapplication.R

class SubstractionBtnActivity : AppCompatActivity() {
    private val title by lazy { findViewById<TextView>(R.id.subbtntitle) }
    private val nameofdata by lazy { findViewById<TextView>(R.id.nameofdata) }
    val qrbtn by lazy { findViewById<ImageButton>(R.id.qrbtn) }
    val subbtn by lazy { findViewById<ImageButton>(R.id.subbtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_substraction_btn)

        val name = intent.getStringExtra("name")
        title.text = getString(R.string.substractionbtntitle, name)
        val amount = intent.getFloatExtra("amount", 0f)
        val currency = intent.getStringExtra("currency")
        val keyString = intent.getStringExtra("key")!!
        val feature = intent.getStringExtra("feature")

        val sp = getSharedPreferences("userOptions", MODE_PRIVATE)

        val dialog = Dialog(this)
        subbtn.setOnClickListener {
            dialog.setContentView(R.layout.layout_for_alert_dialog_sub_manual_amount)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val amountofdata = dialog.findViewById<TextView>(R.id.availableamount)
            val currencydata = dialog.findViewById<TextView>(R.id.cur)
            amountofdata.text = getString(R.string.availableamount, amount.toString())
            currencydata.text = getString(R.string.currency,currency)

            val okaybtn = dialog.findViewById<Button>(R.id.subtract)
            val cancelbtn = dialog.findViewById<Button>(R.id.cancelbtn)

            okaybtn.setOnClickListener {
                val enteranamount = dialog.findViewById<EditText>(R.id.enteranamount)
                val enteredamount = enteranamount.text.toString() // the amount which user entered
                amountofdata.text = getString(R.string.availableamount, amount.toString())
                currencydata.text = getString(R.string.currency,currencydata)
                val res = subtracttheamount(enteredamount.toFloat(),amount)
                val editor = sp.edit()
                if (feature == "w")
                    editor.putFloat("amount_in_wallet_$keyString", res)
                else
                    editor.putFloat("amount_in_bank_$keyString", res)
                editor.apply()

                Toast.makeText(this@SubstractionBtnActivity, "result = $res", Toast.LENGTH_LONG).show()
                dialog.dismiss()
                val intent = Intent(this@SubstractionBtnActivity, FirstPageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }
    }

    private fun subtracttheamount(enteredamount : Float, existedamount : Float): Float {
        val result = existedamount - enteredamount
        return if (result >= 0) {
            result // Update existedAmount only if result is non-negative
        } else {
            existedamount // Keep existedAmount unchanged if result is negative
        }
    }
}