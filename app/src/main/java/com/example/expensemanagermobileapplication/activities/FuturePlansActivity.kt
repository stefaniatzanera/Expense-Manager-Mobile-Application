package com.example.expensemanagermobileapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.databinding.ActivityFuturePlansBinding
import com.example.expensemanagermobileapplication.databinding.ActivitySettingsBinding

class FuturePlansActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_future_plans)

        val binding = DataBindingUtil.setContentView<ActivityFuturePlansBinding>(this,R.layout.activity_future_plans)
    }
}