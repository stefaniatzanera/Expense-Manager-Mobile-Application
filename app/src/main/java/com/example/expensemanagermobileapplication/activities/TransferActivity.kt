package com.example.expensemanagermobileapplication.activities

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.adapter.DestinationAdapter
import com.example.expensemanagermobileapplication.dataClass.DestinationItem
import com.example.expensemanagermobileapplication.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {
    val transfertitle by lazy { findViewById<TextView>(R.id.transferttl) }
    val from by lazy { findViewById<TextView>(R.id.from) }
    val amountto by lazy { findViewById<TextView>(R.id.amountto) }
    val currencyamount by lazy { findViewById<TextView>(R.id.currencyamount) }
    val destination by lazy { findViewById<Spinner>(R.id.selectdestination) }
    val qr by lazy { findViewById<ImageButton>(R.id.qrbtn) }
    val destinationLayout by lazy { findViewById<LinearLayout>(R.id.destinationplaceholder) }
    val manualtransfer by lazy { findViewById<ImageButton>(R.id.manualtransferbtn) }
    private lateinit var sp: SharedPreferences
    private lateinit var activityTransferBinding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTransferBinding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(activityTransferBinding.root)

        val name = intent.getStringExtra("name")
        from.text = name
        val amount = intent.getFloatExtra("amount",0F)
        amountto.text = amount.toString()
        val currency = intent.getStringExtra("currency")
        currencyamount.text = currency?.last().toString()
        val keyString = intent.getStringExtra("key")!!
        val feature = intent.getStringExtra("feature")

        val namesofdata = destination.findViewById<TextView>(R.id.name)
        var amountsofdata = destination.findViewById<TextView>(R.id.amount)
        val currencyofdata = destination.findViewById<TextView>(R.id.currency)

        sp = getSharedPreferences("userOptions", MODE_PRIVATE)

        val destinations = mutableListOf<DestinationItem>()
        destinations.add(DestinationItem("", "" ,""))
        val b_id = sp.getInt("bankID", 0)
        for (b in 1..b_id) {
            val bankName = sp.getString("name_of_bank_$b", "null")
            if (bankName == "null")
                continue
            var bankAmount = sp.getFloat("amount_in_bank_$b", 0F)
            val bankCur = sp.getString("currency_bank_$b", "null")!!
            if (bankCur == currency && name != bankName) {
                destinations.add(DestinationItem(bankName!!, bankAmount.toString(), bankCur.last().toString()))
            }
        }

        val w_id = sp.getInt("walletID", 0)
        for (w in 1..w_id) {
            val walletName = sp.getString("name_of_wallet_$w", "null")
            if (walletName == "null")
                continue
            var walletAmount = sp.getFloat("amount_in_wallet_$w", 0F)
            val walletCur = sp.getString("currency_wallet_$w", "null")!!
            if (walletCur == currency && name != walletName) {
                destinations.add(DestinationItem(walletName!!, walletAmount.toString(), walletCur.last().toString()))
            }
        }

        val adapter = DestinationAdapter(this, destinations)
        destination.adapter = adapter

        destinationLayout.setOnClickListener {
            destination.performClick()
        }

        destination.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position != 0) {
                    activityTransferBinding.destinationplaceholder.buttonname = destinations[position].name
                    activityTransferBinding.destinationplaceholder.buttonamount = destinations[position].amount
                    activityTransferBinding.destinationplaceholder.buttoncurrency = destinations[position].currency
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val dialog = Dialog(this)
        manualtransfer.setOnClickListener{
            dialog.setContentView(R.layout.layout_for_manual_transfering)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var amountofdata = dialog.findViewById<TextView>(R.id.availableamount)
            val currencydata = dialog.findViewById<TextView>(R.id.cur)
            amountofdata.text = getString(R.string.availableamount, amount.toString())
            currencydata.text = getString(R.string.currency,currency)

            val transferbtn = dialog.findViewById<Button>(R.id.transfer)
            val cancelbtn = dialog.findViewById<Button>(R.id.cancelbtn)

            transferbtn.setOnClickListener {
                val enteranamount = dialog.findViewById<EditText>(R.id.enteranamount)
                val enteredamount = enteranamount.text.toString() // the amount which user entered
                amountofdata.text = getString(R.string.availableamount, amount.toString()) //amount from
                currencydata.text = getString(R.string.currency,currencydata)
                val res = amount - enteredamount.toFloat()
                if (res < 0F) {
                    Toast.makeText(this@TransferActivity, "Transaction not possible!", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

//                val editor = sp.edit()
//                if (feature == "w") {
//                    editor.putFloat("amount_in_wallet_$keyString", res)
//                }
//                else
//                    editor.putFloat("amount_in_bank_$keyString", res)
//                editor.apply()
//
//                Toast.makeText(this@TransferActivity, "result = $res", Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//                val intent = Intent(this@TransferActivity, FirstPageActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
            }
            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }

    }

//    private fun transferamount(enteredamount : Float, existedamountto : Float, existedamountfrom : Float): Float {
//        val destamount = existedamountto + enteredamount
//        if(existedamountfrom!= null && existedamountfrom>=enteredamount){
//            existedamountfrom -= enteredamount
//            existedamountto = destamount
//            return existedamountto
//        }
//        else if{
//            return Any
//        }
//    }
}