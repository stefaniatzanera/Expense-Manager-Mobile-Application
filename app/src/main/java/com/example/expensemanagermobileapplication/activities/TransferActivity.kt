package com.example.expensemanagermobileapplication.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.expensemanagermobileapplication.R

class TransferActivity : AppCompatActivity() {
    val transfertitle by lazy { findViewById<TextView>(R.id.transferttl) }
    val from by lazy { findViewById<TextView>(R.id.from) }
    val select by lazy { findViewById<Spinner>(R.id.selectdestination) }
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        val name = intent.getStringExtra("name")
        from.text = name


    }
}