package com.example.expensemanagermobileapplication.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.adapter.DestinationAdapter
import com.example.expensemanagermobileapplication.dataClass.BankInfos
import com.example.expensemanagermobileapplication.dataClass.DestinationItem
import com.example.expensemanagermobileapplication.dataClass.WalletInfos

class TransferActivity : AppCompatActivity() {
    val transfertitle by lazy { findViewById<TextView>(R.id.transferttl) }
    val from by lazy { findViewById<TextView>(R.id.from) }
    val amountto by lazy { findViewById<TextView>(R.id.amountto) }
    val currencyamount by lazy { findViewById<TextView>(R.id.currencyamount) }
    val destination by lazy { findViewById<Spinner>(R.id.selectdestination) }
    val placeholderText by lazy {findViewById<TextView>(R.id.placeholder) }
    val qr by lazy { findViewById<Button>(R.id.qrbtn) }
    val manualtransfer by lazy { findViewById<Button>(R.id.manualtransferbtn) }
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        val name = intent.getStringExtra("name")
        from.text = name
        val amount = intent.getStringExtra("amount")
        amountto.text = amount
        val currency = intent.getStringExtra("currency")
        currencyamount.text = currency?.last().toString()
        val keyString = intent.getStringExtra("key")!!

        val namesofdata = destination.findViewById<TextView>(R.id.name)
        val amountsofdata = destination.findViewById<TextView>(R.id.amount)
        val currencyofdata = destination.findViewById<TextView>(R.id.currency)

        sp = getSharedPreferences("userOptions", MODE_PRIVATE)

        val destinations = mutableListOf<DestinationItem>()
        val b_id = sp.getInt("bankID", 0)
        for (b in 1..b_id) {
            val bankName = sp.getString("name_of_bank_$b", "null")
            if (bankName == "null")
                continue
            val bankAmount = sp.getFloat("amount_in_bank_$b", 0F)
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
            val walletAmount = sp.getFloat("amount_in_wallet_$w", 0F)
            val walletCur = sp.getString("currency_wallet_$w", "null")!!
            if (walletCur == currency && name != walletName) {
                destinations.add(DestinationItem(walletName!!, walletAmount.toString(), walletCur.last().toString()))
            }
        }

        val adapter = DestinationAdapter(this, destinations)
        destination.adapter = adapter

        placeholderText.setOnClickListener {
            destination.performClick()
        }

        manualtransfer.setOnClickListener {

        }
    }
}