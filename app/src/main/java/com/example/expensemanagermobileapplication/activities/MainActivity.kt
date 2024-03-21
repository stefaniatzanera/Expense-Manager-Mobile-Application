package com.example.expensemanagermobileapplication.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanagermobileapplication.R

class MainActivity : AppCompatActivity() {
    private val startBtn by lazy { findViewById<Button>(R.id.startButton)}
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences("userOptions", MODE_PRIVATE)
        val isEmpty = isSharedPreferencesEmpty(this, "userOptions")
        if (isEmpty) {
            startBtn.setOnClickListener {
                //Toast.makeText(this,"pressed", Toast.LENGTH_LONG).show()
                val intent = Intent(this, BeginningActivity::class.java)
                startActivity(intent)
                //onBackPressed()
            }
        } else {
            val y = Intent(this, FirstPageActivity::class.java)
            startActivity(y)
        }
    }

    fun isSharedPreferencesEmpty(context: Context, sharedPreferencesName: String): Boolean {
        val sharedPreferences = context.getSharedPreferences("userOptions", Context.MODE_PRIVATE)
        return sharedPreferences.all.isEmpty()
    }
}