package com.example.expensemanagermobileapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FirstPageActivity : AppCompatActivity() {
    val recyclerView by lazy { findViewById<RecyclerView>(R.id.money) }
    val names: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        names.add("Test1")
        names.add("Test2")
        names.add("Test3")
        names.add("Test4")
        names.add("Test5")

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(applicationContext());
    }

}