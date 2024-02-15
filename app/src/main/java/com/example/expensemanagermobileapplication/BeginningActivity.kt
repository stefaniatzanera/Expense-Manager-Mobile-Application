package com.example.expensemanagermobileapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BeginningActivity : AppCompatActivity() {
    private val addCardButton by lazy { findViewById<Button>(R.id.addcardbtn)}
    private val addCashButton by lazy { findViewById<Button>(R.id.addcashbtn)}
    private val helpButton by lazy { findViewById<Button>(R.id.helpbtn)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginning)
        addCardButton.setOnClickListener {
            //Toast.makeText(this,"pressed", Toast.LENGTH_LONG).show()
            val x = Intent(this, AddCardActivity::class.java)
            startActivity(x)
        }

        addCashButton.setOnClickListener{
            val y = Intent(this, AddCashActivity::class.java)
            startActivity(y)
        }

        helpButton.setOnClickListener{
            val z = Intent(this, HelpActivity::class.java)
            startActivity(z)
        }
    }
}