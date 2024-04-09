package com.example.expensemanagermobileapplication.adapter

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanagermobileapplication.dataClass.BankInfos
import com.example.expensemanagermobileapplication.dataClass.WalletInfos
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.activities.DataActivity

class BankAdapter(context: Context): RecyclerView.Adapter<BankAdapter.ViewHolder>() {
    //private val bankList: MutableList<BankInfos> = mutableListOf()
    private var infoList: MutableList<Any> = mutableListOf()
    private var context: Context? = null

    init {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_for_user_data, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = infoList[position]
        holder.bind(info, position)
    }

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

    fun removeItem(position: Int) {
        infoList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView by lazy { itemView.findViewById<TextView>(R.id.name)}
        val amountTextView by lazy { itemView.findViewById<TextView>(R.id.amount)}
        val currencyTextView by lazy { itemView.findViewById<TextView>(R.id.currency)}

        fun bind(info: Any, position: Int) {
            var currencyString = ""
            var keyString = ""
            var feature = ""
            if(info is BankInfos) {
                //val bank = info as BankInfos
                feature = "c"
                currencyString = info.b_currency
                keyString = info.key
                nameTextView.text = info.bank_name
                amountTextView.text = info.b_amount.toString()
                currencyTextView.text = currencyString.last().toString()
            }
            else if (info is WalletInfos) {
                feature = "w"
                currencyString = info.w_currency
                keyString = info.key
                nameTextView.text = info.wallet_name
                amountTextView.text = info.w_amount.toString()
                currencyTextView.text = currencyString.last().toString()
            }

            itemView.setOnClickListener {
                val intent = Intent(context, DataActivity::class.java).apply {
                    putExtra("name", nameTextView.text)
                    putExtra("amount", amountTextView.text)
                    putExtra("currency", currencyString)
                    putExtra("key", keyString)
                    putExtra("feature", feature)
                }

                // Start the second activity with the Intent
                context?.startActivity(intent)
            }

            fun updateData(newData: MutableList<String>) {
                infoList = newData.toMutableList()
                notifyDataSetChanged()
            }
        }
    }
}