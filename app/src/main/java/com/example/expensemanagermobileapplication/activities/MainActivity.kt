package com.example.expensemanagermobileapplication.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
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
                val intent = Intent(this, BeginningActivity::class.java)
                startActivity(intent)
                //onBackPressed()
            }
        } else {
            startBtn.visibility = View.GONE
            startBtn.postDelayed({
                val y = Intent(this, FirstPageActivity::class.java)
                startActivity(y)
            }, 300)
        }
    }

    fun isSharedPreferencesEmpty(context: Context, sharedPreferencesName: String): Boolean {
        val sharedPreferences = context.getSharedPreferences("userOptions", Context.MODE_PRIVATE)
        return sharedPreferences.all.isEmpty()
    }
}