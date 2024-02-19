package com.example.expensemanagermobileapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanagermobileapplication.databinding.ActivityFirstPageBinding

class FirstPageActivity : AppCompatActivity() {
//    val recyclerView by lazy { findViewById<RecyclerView>(R.id.money) }
    val bank: MutableList<BankInfos> = mutableListOf()
    val wallet: MutableList<WalletInfos> = mutableListOf()

    lateinit var binding: ActivityFirstPageBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: BankAdapter

    private val addBtn by lazy { findViewById<Button>(R.id.addbtn)}
    private val substractionBtn by lazy { findViewById<Button>(R.id.subtractionbtn)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPageBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_first_page)
        setContentView(binding.root)

        recyclerView = binding.money
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BankAdapter()
        recyclerView.adapter = adapter

        val sp = getSharedPreferences("userOptions", MODE_PRIVATE)
        val editor = sp.edit()
        val b_id = sp.getInt("bankID",0)
        val w_id = sp.getInt("walletID",0)

        for(b in 1..b_id){
            val bankName = sp.getString("name_of_bank_$b","null")
            val bankAmount = sp.getFloat("amount_in_bank_$b", 0F)
            val bankCur = sp.getString("currency_bank_$b","null")?.last().toString()
            val bankdata = BankInfos( bankName!!, bankAmount, bankCur)
            adapter.addBank(bankdata)
        }

        for(w in 1..w_id){
            val walletName = sp.getString("name_of_wallet_$w","null")
            val walletAmount = sp.getFloat("amount_in_wallet_$w", 0F)
            val walletCur = sp.getString("currency_wallet_$w","null")
            val walletdata = WalletInfos( walletName!!, walletAmount, walletCur!!)
        }

        addBtn.setOnClickListener {
            val x = Intent(this, AddBtnActivity::class.java)
            startActivity(x)
        }

        substractionBtn.setOnClickListener{
            val y = Intent(this, SubstractionBtnActivity::class.java)
            startActivity(y)
        }


    }

}