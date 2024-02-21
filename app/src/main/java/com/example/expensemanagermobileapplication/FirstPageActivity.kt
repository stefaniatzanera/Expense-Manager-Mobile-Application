package com.example.expensemanagermobileapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanagermobileapplication.databinding.ActivityFirstPageBinding

class FirstPageActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    val newnamebtn by lazy { findViewById<TextView>(R.id.hello_txt) }

    lateinit var binding: ActivityFirstPageBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: BankAdapter

    private val addBtn by lazy { findViewById<Button>(R.id.addbtn)}
    private val substractionBtn by lazy { findViewById<Button>(R.id.subtractionbtn)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.money
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BankAdapter()
        recyclerView.adapter = adapter

        val sp = getSharedPreferences("userOptions", MODE_PRIVATE)

        newnamebtn.text = getString(R.string.hellotxt, sp.getString("userName" , "User"))

        val b_id = sp.getInt("bankID",0)
        val w_id = sp.getInt("walletID",0)

        for(b in 1..b_id){
            val bankName = sp.getString("name_of_bank_$b","null")
            val bankAmount = sp.getFloat("amount_in_bank_$b", 0F)
            val bankCur = sp.getString("currency_bank_$b","null")?.last().toString()
            val bankdata = BankInfos( bankName!!, bankAmount, bankCur)
            adapter.addInfo(bankdata)
        }

        for(w in 1..w_id){
            val walletName = sp.getString("name_of_wallet_$w","null")
            val walletAmount = sp.getFloat("amount_in_wallet_$w", 0F)
            val walletCur = sp.getString("currency_wallet_$w","null")?.last().toString()
            val walletdata = WalletInfos( walletName!!, walletAmount, walletCur)
            adapter.addInfo(walletdata)
        }


        //Buttons add & substraction money
        addBtn.setOnClickListener {
            val x = Intent(this, AddBtnActivity::class.java)
            startActivity(x)
        }

        substractionBtn.setOnClickListener{
            val y = Intent(this, SubstractionBtnActivity::class.java)
            startActivity(y)
        }

        val dialog = Dialog(this)
        newnamebtn.setOnClickListener {
            dialog.setContentView(R.layout.layout_for_alert_dialog_newname)
            // Set the layout parameters for the dialog window
            //dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val okaybtn by lazy { dialog.findViewById<Button>(R.id.okbtn)}
            val cancelbtn by lazy {dialog.findViewById<Button>(R.id.cancelbtn)}
            val nametext by lazy {dialog.findViewById<EditText>(R.id.entername)}

            okaybtn.setOnClickListener {
                val userName = nametext.text.toString()
                sp.edit().putString("userName", userName).apply()
                newnamebtn.text = getString(R.string.hellotxt, userName)
                dialog.dismiss()
            }
            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }

    }

}