package com.example.expensemanagermobileapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.expensemanagermobileapplication.R

class SubstractionBtnActivity : AppCompatActivity() {
    private val title by lazy { findViewById<TextView>(R.id.subbtntitle) }
    private val nameofdata by lazy { findViewById<TextView>(R.id.nameofdata) }
    private val amountofdata by lazy { findViewById<TextView>(R.id.amountofdata) }
    private val currencydata by lazy { findViewById<TextView>(R.id.currencydata) }
    val qrbtn by lazy { findViewById<ImageButton>(R.id.qrbtn) }
    val subbtn by lazy { findViewById<ImageButton>(R.id.subbtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_substraction_btn)

        val name = intent.getStringExtra("name")

        title.text = getString(R.string.substractionbtntitle, name)
    }
}