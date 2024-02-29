package com.example.expensemanagermobileapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanagermobileapplication.dataClass.BankInfos
import com.example.expensemanagermobileapplication.dataClass.WalletInfos
import com.example.expensemanagermobileapplication.R

class BankAdapter: RecyclerView.Adapter<BankAdapter.ViewHolder>() {
    //private val bankList: MutableList<BankInfos> = mutableListOf()
    private val infoList: MutableList<Any> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_for_user_data, parent, false)
        return ViewHolder(view)
    }
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val bank = bankList[position]
//        holder.bind(bank)
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = infoList[position]
        holder.bind(info)
    }
//    override fun getItemCount(): Int {
//        return bankList.size
//    }

    override fun getItemCount(): Int {
        return infoList.size
    }

//    fun addBank(bank: BankInfos) {
//        infoList.add(bank)
//        notifyDataSetChanged()
//    }

    fun addInfo(info: Any) {
        infoList.add(info)
        notifyDataSetChanged()
    }
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nameTextView by lazy { itemView.findViewById<TextView>(R.id.name)}
//        val amountTextView by lazy { itemView.findViewById<TextView>(R.id.amount)}
//        val currencyTextView by lazy { itemView.findViewById<TextView>(R.id.currency)}
//
//        fun bind(bank: BankInfos) {
//            nameTextView.text = bank.bank_name
//            amountTextView.text = bank.b_amount.toString()
//            currencyTextView.text = bank.b_currency
//        }
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView by lazy { itemView.findViewById<TextView>(R.id.name)}
        val amountTextView by lazy { itemView.findViewById<TextView>(R.id.amount)}
        val currencyTextView by lazy { itemView.findViewById<TextView>(R.id.currency)}

        fun bind(info: Any) {
            if(info is BankInfos) {
                //val bank = info as BankInfos
                nameTextView.text = info.bank_name
                amountTextView.text = info.b_amount.toString()
                currencyTextView.text = info.b_currency
            }
            else if (info is WalletInfos) {
                nameTextView.text = info.wallet_name
                amountTextView.text = info.w_amount.toString()
                currencyTextView.text = info.w_currency
            }
        }
    }
}