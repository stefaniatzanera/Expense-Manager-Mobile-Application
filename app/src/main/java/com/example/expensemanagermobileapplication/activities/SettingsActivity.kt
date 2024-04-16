package com.example.expensemanagermobileapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settings)

        val binding = DataBindingUtil.setContentView<ActivitySettingsBinding>(this,R.layout.activity_settings)


    }
}