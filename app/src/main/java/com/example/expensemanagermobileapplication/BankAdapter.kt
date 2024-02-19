package com.example.expensemanagermobileapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BankAdapter: RecyclerView.Adapter<BankAdapter.ViewHolder>() {
    private val bankList: MutableList<BankInfos> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_for_user_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bank = bankList[position]
        holder.bind(bank)
    }

    override fun getItemCount(): Int {
        return bankList.size
    }

    fun addBank(bank: BankInfos) {
        bankList.add(bank)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView by lazy { itemView.findViewById<TextView>(R.id.name)}
        val amountTextView by lazy { itemView.findViewById<TextView>(R.id.amount)}
        val currencyTextView by lazy { itemView.findViewById<TextView>(R.id.currency)}

        fun bind(bank: BankInfos) {
            nameTextView.text = bank.bank_name
            amountTextView.text = bank.b_amount.toString()
            currencyTextView.text = bank.b_currency
        }
    }
}