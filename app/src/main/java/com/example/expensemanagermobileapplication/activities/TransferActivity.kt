package com.example.expensemanagermobileapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.expensemanagermobileapplication.R

class TransferActivity : AppCompatActivity() {
    val transfertitle by lazy { findViewById<TextView>(R.id.transferttl) }
    val select1 by lazy { findViewById<LinearLayout>(R.id.slc) }
    val to by lazy { findViewById<TextView>(R.id.to2) }
    val select2 by lazy { findViewById<LinearLayout>(R.id.slc2) }
    val amount by lazy { findViewById<EditText>(R.id.enteranamount) }
    val transferbtn by lazy { findViewById<Button>(R.id.transferbtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
    }
}