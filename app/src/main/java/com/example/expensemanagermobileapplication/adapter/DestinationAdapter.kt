package com.example.expensemanagermobileapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.dataClass.DestinationItem

class DestinationAdapter(context: Context, private val items: List<DestinationItem>) : ArrayAdapter<DestinationItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.spinner_bankwallet_data, parent, false)
        }

        val currentItem = items[position]

        val nameTextView = itemView!!.findViewById<TextView>(R.id.name)
        val amountTextView = itemView.findViewById<TextView>(R.id.amount)
        val currencyTextView = itemView.findViewById<TextView>(R.id.currency)

        nameTextView.text = currentItem.name
        amountTextView.text = currentItem.amount
        currencyTextView.text = currentItem.currency

        return itemView
    }
}
