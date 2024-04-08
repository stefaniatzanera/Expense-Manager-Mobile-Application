package com.example.expensemanagermobileapplication.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.adapter.BankAdapter
import com.example.expensemanagermobileapplication.dataClass.BankInfos
import com.example.expensemanagermobileapplication.dataClass.WalletInfos
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DataActivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var sp: SharedPreferences
    private var context: Context? = null

    private val fimage by lazy { findViewById<ImageView>(R.id.featureimage) }
    private val nameofdata by lazy { findViewById<TextView>(R.id.nameofdata) }
    private val amountofdata by lazy { findViewById<TextView>(R.id.amountofdata) }
    private val currencydata by lazy { findViewById<TextView>(R.id.currencydata) }

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_animation) }
    private val rotateClosed: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_closed_animation) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_animation) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_animation) }
    private var clicked = false
    private val actions_btn by lazy { findViewById<FloatingActionButton>(R.id.actionsbtn) }
    private val addbtn by lazy { findViewById<FloatingActionButton>(R.id.addaction) }
    private val subbtn by lazy { findViewById<FloatingActionButton>(R.id.subtractaction) }
    private val transferbtn by lazy { findViewById<FloatingActionButton>(R.id.transferaction) }
    private val deletebtn by lazy { findViewById<FloatingActionButton>(R.id.deleteaction) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        lateinit var adapter: BankAdapter

        val name = intent.getStringExtra("name")
        val amount = intent.getStringExtra("amount")
        val currency = intent.getStringExtra("currency")
        val keyString = intent.getStringExtra("key")!!
        var feature = intent.getStringExtra("feature")

        //image for data
        val drawableResId = when (feature) {
            "w" -> R.drawable.cash
            "c" -> R.drawable.card
            else -> R.drawable.money // Default drawable resource
        }
        fimage.setImageResource(drawableResId)
        nameTextView = findViewById<TextView>(R.id.nameofdata)
        val amountTextView = findViewById<TextView>(R.id.amountofdata)
        val currencyTextView = findViewById<TextView>(R.id.currencydata)

        nameofdata.text = name
        amountofdata.text = amount
        currencydata.text = currency.toString()

        nameTextView.setOnClickListener {
            showEditNameDialog(keyString)
        }

        actions_btn.setOnClickListener{
            onAddButtonClicked()
        }

        addbtn.setOnClickListener{
            val x = Intent(this, AddBtnActivity::class.java).apply {
                putExtra("name", nameofdata.text)
                putExtra("amount", amountofdata.text.toString().toFloat())
                putExtra("currency", currencydata.text)
                putExtra("key",keyString)
                putExtra("feature",feature)
            }
            startActivity(x)
        }

        subbtn.setOnClickListener {
            val y = Intent(this, SubstractionBtnActivity::class.java).apply {
                putExtra("name", nameofdata.text)
                putExtra("amount", amountofdata.text.toString().toFloat())
                putExtra("currency", currencydata.text)
                putExtra("key",keyString)
                putExtra("feature",feature)
            }
            startActivity(y)
        }

        transferbtn.setOnClickListener {
            val z = Intent(this, TransferActivity::class.java).apply{
                putExtra("name", nameofdata.text)
                putExtra("amount", amountofdata.text.toString().toFloat())
                putExtra("currency", currencydata.text)
                putExtra("key",keyString)
                putExtra("feature",feature)
            }
            startActivity(z)
        }

        deletebtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.layout_for_delete)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val title by lazy { dialog.findViewById<TextView>(R.id.question) }
            val erasebtn by lazy { dialog.findViewById<Button>(R.id.delete) }
            val cancel by lazy { dialog.findViewById<Button>(R.id.cancelbtn) }

            title.text = getString(R.string.areyousure,name)

            cancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }
    }
    //show dialog for edit the name of bank/wallet
    private fun showEditNameDialog(keyString: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_for_alert_dialog_newname)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okaybtn by lazy { dialog.findViewById<Button>(R.id.okbtn) }
        val cancelbtn by lazy { dialog.findViewById<Button>(R.id.cancelbtn) }
        val nametext by lazy { dialog.findViewById<EditText>(R.id.entername) }

        okaybtn.setOnClickListener {
            val newName = nametext.text.toString()
            sp.edit().putString(keyString, newName).apply()
            nameofdata.text = newName
            dialog.dismiss()
        }
        cancelbtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.show()
    }

    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
//        setClickable(clicked)
        clicked = !clicked
    }
    private fun setVisibility(clicked: Boolean){
        if(!clicked){
            addbtn.visibility = View.VISIBLE
            subbtn.visibility = View.VISIBLE
            transferbtn.visibility = View.VISIBLE
            deletebtn.visibility = View.VISIBLE
        }else{
            addbtn.visibility = View.INVISIBLE
            subbtn.visibility = View.INVISIBLE
            transferbtn.visibility = View.INVISIBLE
            deletebtn.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(clicked: Boolean){
        if(!clicked){
            addbtn.startAnimation(fromBottom)
            subbtn.startAnimation(fromBottom)
            transferbtn.startAnimation(fromBottom)
            deletebtn.startAnimation(fromBottom)
            actions_btn.startAnimation(rotateOpen)
        }else{
            addbtn.startAnimation(toBottom)
            subbtn.startAnimation(toBottom)
            transferbtn.startAnimation(toBottom)
            deletebtn.startAnimation(toBottom)
            actions_btn.startAnimation(rotateClosed)
        }
    }
}