package com.example.expensemanagermobileapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.expensemanagermobileapplication.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val addCardButton by lazy { findViewById<Button>(R.id.addcardbtn)}
        val addCashButton by lazy { findViewById<Button>(R.id.addcashbtn)}
        val infoButton by lazy { findViewById<Button>(R.id.infobtn)}

        addCardButton.setOnClickListener {
            //Toast.makeText(this,"pressed", Toast.LENGTH_LONG).show()
            val x = Intent(this, AddCardActivity::class.java)
            startActivity(x)
        }

        addCashButton.setOnClickListener{
            val y = Intent(this, AddCashActivity::class.java)
            startActivity(y)
        }

        infoButton.setOnClickListener{
            val z = Intent(this, InfoActivity::class.java)
            startActivity(z)
        }
    }
}