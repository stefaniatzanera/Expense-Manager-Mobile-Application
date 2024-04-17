package com.example.expensemanagermobileapplication.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    //user data
    val addcardbtn by lazy { findViewById<LinearLayout>(R.id.addcardbtn) }
    val addwalletbtn by lazy { findViewById<LinearLayout>(R.id.addwalletbtn) }
    val deleteonebtn by lazy { findViewById<LinearLayout>(R.id.deleteonebtn) }
    val deleteallbtn by lazy { findViewById<LinearLayout>(R.id.deleteallbtn) }
    //energies
    val addmoneybtn by lazy { findViewById<LinearLayout>(R.id.addmoneybtn) }
    val submoneybtn by lazy { findViewById<LinearLayout>(R.id.submoneybtn) }
    val transfermoneybtn by lazy { findViewById<LinearLayout>(R.id.transfermoneybtn) }
    //about expense manager
    val ratebtn by lazy { findViewById<LinearLayout>(R.id.ratebtn) }
    val contactbtn by lazy { findViewById<LinearLayout>(R.id.contactbtn) }
    val scbtn by lazy { findViewById<LinearLayout>(R.id.sourcecodebtn) }
    val fpbtn by lazy { findViewById<LinearLayout>(R.id.futureplansbtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settings)

        val binding = DataBindingUtil.setContentView<ActivitySettingsBinding>(this,R.layout.activity_settings)

        addcardbtn.setOnClickListener {
            val x = Intent(this, AddCardActivity::class.java)
            startActivity(x)
        }

        addwalletbtn.setOnClickListener{
            val y = Intent(this, AddCashActivity::class.java)
            startActivity(y)
        }

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