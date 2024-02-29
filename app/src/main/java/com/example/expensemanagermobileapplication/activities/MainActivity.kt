package com.example.expensemanagermobileapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanagermobileapplication.R

class MainActivity : AppCompatActivity() {
    private val startBtn by lazy { findViewById<Button>(R.id.startButton)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn.setOnClickListener {
            //Toast.makeText(this,"pressed", Toast.LENGTH_LONG).show()
            val intent = Intent(this, BeginningActivity::class.java)
            startActivity(intent)
            //onBackPressed()
        }
    }
}