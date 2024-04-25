package com.example.expensemanagermobileapplication.activities

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.adapter.DestinationAdapter
import com.example.expensemanagermobileapplication.dataClass.DestinationItem
import com.example.expensemanagermobileapplication.databinding.ActivitySettingsBinding
import java.lang.System.exit

class SettingsActivity : AppCompatActivity() {
    //user data
    val addcardbtn by lazy { findViewById<LinearLayout>(R.id.addcardbtn) }
    val addwalletbtn by lazy { findViewById<LinearLayout>(R.id.addwalletbtn) }
    val deleteonebtn by lazy { findViewById<LinearLayout>(R.id.deleteonebtn) }
    val destination by lazy { findViewById<Spinner>(R.id.selecttodelete) }
    val destinationLayout by lazy { findViewById<LinearLayout>(R.id.deleteonespinner) }
    val deleteallbtn by lazy { findViewById<LinearLayout>(R.id.deleteallbtn) }
//    //energies
//    val addmoneybtn by lazy { findViewById<LinearLayout>(R.id.addmoneybtn) }
//    val submoneybtn by lazy { findViewById<LinearLayout>(R.id.submoneybtn) }
//    val transfermoneybtn by lazy { findViewById<LinearLayout>(R.id.transfermoneybtn) }
    //about expense manager
    val ratebtn by lazy { findViewById<LinearLayout>(R.id.ratebtn) }
    val contactbtn by lazy { findViewById<LinearLayout>(R.id.contactbtn) }
    val scbtn by lazy { findViewById<LinearLayout>(R.id.sourcecodebtn) }
    val fpbtn by lazy { findViewById<LinearLayout>(R.id.futureplansbtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settings)

        val binding = DataBindingUtil.setContentView<ActivitySettingsBinding>(this,R.layout.activity_settings)
        val dialog = Dialog(this)
        val sp = getSharedPreferences("userOptions", MODE_PRIVATE)
        val editor = sp.edit()

        //user data
        addcardbtn.setOnClickListener {
            val x = Intent(this, AddCardActivity::class.java)
            startActivity(x)
        }

        addwalletbtn.setOnClickListener{
            val y = Intent(this, AddCashActivity::class.java)
            startActivity(y)
        }

        deleteonebtn.setOnClickListener {
            dialog.setContentView(R.layout.layout_for_delete_one_data)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val deletebtn = dialog.findViewById<Button>(R.id.delete)
            val cancelbtn = dialog.findViewById<Button>(R.id.cancelbtn)
            val placeholder = dialog.findViewById<TextView>(R.id.placeholder)
            val spinner = dialog.findViewById<Spinner>(R.id.selecttodelete)

            val destinations = mutableListOf<DestinationItem>()
            destinations.add(DestinationItem("", "" ,"", -1, ""))
            val b_id = sp.getInt("bankID", 0)
            for (b in 1..b_id) {
                val bankName = sp.getString("name_of_bank_$b", "null")
                if (bankName == "null")
                    continue
                var bankAmount = sp.getFloat("amount_in_bank_$b", 0F)
                val bankCur = sp.getString("currency_bank_$b", "null")!!
                destinations.add(DestinationItem(bankName!!, bankAmount.toString(), bankCur.last().toString(), b, "b"))

            }

            val w_id = sp.getInt("walletID", 0)
            for (w in 1..w_id) {
                val walletName = sp.getString("name_of_wallet_$w", "null")
                if (walletName == "null")
                    continue
                var walletAmount = sp.getFloat("amount_in_wallet_$w", 0F)
                val walletCur = sp.getString("currency_wallet_$w", "null")!!
                destinations.add(DestinationItem(walletName!!, walletAmount.toString(), walletCur.last().toString(), w, "w"))
            }

            val adapter = DestinationAdapter(this, destinations)
            spinner.adapter = adapter

            placeholder.setOnClickListener {
                spinner.performClick()
            }

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    if (position != 0)
                        placeholder.text = destinations[position].name
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            deletebtn.setOnClickListener {
                val id = (spinner.selectedItem as DestinationItem).key
                val name = (spinner.selectedItem as DestinationItem).name
                val feature = (spinner.selectedItem as DestinationItem).feature
                if (feature == "w") {
                    editor.remove("name_of_wallet_$id")
                    editor.remove("amount_in_wallet_$id")
                    editor.remove("currency_wallet_$id")
                }
                else {
                    editor.remove("name_of_bank_$id")
                    editor.remove("amount_in_bank_$id")
                    editor.remove("currency_bank_$id")
                }
                editor.apply()

                Toast.makeText(this@SettingsActivity, "Deleted $name Successfully", Toast.LENGTH_LONG).show()
                dialog.dismiss()
                val intent = Intent(this@SettingsActivity, FirstPageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }

        deleteallbtn.setOnClickListener{
            dialog.setContentView(R.layout.layout_for_delete_all_data)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val deletebtn = dialog.findViewById<Button>(R.id.delete)
            val cancelbtn = dialog.findViewById<Button>(R.id.cancelbtn)

            deletebtn.setOnClickListener {
                editor.clear()
                editor.apply()
                dialog.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }
        //energies

        //about expense manager
        ratebtn.setOnClickListener{
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.zestas.cryptmyfiles")))
                } catch (e1: ActivityNotFoundException) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.zestas.cryptmyfiles")))
                    } catch (e2: ActivityNotFoundException) {
                        Toast.makeText(this,"Cannot open link.", Toast.LENGTH_LONG).show()
                    }
                }
        }

        contactbtn.setOnClickListener{
            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            selectorIntent.data = Uri.parse("mailto:")

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("stefaniatz99@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Expense Manager Feedback")
            emailIntent.selector = selectorIntent

            startActivity(Intent.createChooser(emailIntent, "Expense Manager Feedback"))

        }

        scbtn.setOnClickListener{
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/stefaniatzanera/Expense-Manager-Mobile-Application")))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this,"Cannot open link.", Toast.LENGTH_LONG).show()
            }

        }

        fpbtn.setOnClickListener{
            val a = Intent(this@SettingsActivity, FuturePlansActivity::class.java)
            startActivity(a)
        }
    }
}