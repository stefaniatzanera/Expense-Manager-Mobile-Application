package com.example.expensemanagermobileapplication.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.adapter.BankAdapter
import com.example.expensemanagermobileapplication.dataClass.BankInfos
import com.example.expensemanagermobileapplication.dataClass.WalletInfos
import com.example.expensemanagermobileapplication.databinding.ActivityFirstPageBinding
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream


class FirstPageActivity : AppCompatActivity() {
    private val userimage by lazy { findViewById<CircleImageView>(R.id.userimage) }
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var sp: SharedPreferences
    private val newnamebtn by lazy { findViewById<TextView>(R.id.hello_txt) }
    private val settingsbtn by lazy { findViewById<ImageView>(R.id.settingsbtn)}
    lateinit var binding: ActivityFirstPageBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: BankAdapter
    private val addBtn by lazy { findViewById<Button>(R.id.addbtn)}
    private val substractionBtn by lazy { findViewById<Button>(R.id.subtractionbtn)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.money
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BankAdapter()
        recyclerView.adapter = adapter
        sp = getSharedPreferences("userOptions", MODE_PRIVATE)

        val replacedimage = sp.getBoolean("replacedImage",false)
        if(!replacedimage){
            userimage.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.default_prof_im))
        }
        else{
            val image_name = sp.getString("image_name", "null")
            if (image_name != "null")
                userimage.setImageURI(Uri.fromFile(File("${filesDir.absoluteFile}/${image_name}")))
            else
                Log.d("FirstPageActivity", "error in image_name")

        }

        userimage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        val b_id = sp.getInt("bankID", 0)
        for (b in 1..b_id) {
            val bankName = sp.getString("name_of_bank_$b", "null")
            val bankAmount = sp.getFloat("amount_in_bank_$b", 0F)
            val bankCur = sp.getString("currency_bank_$b", "null")?.last().toString()
            val bankdata = BankInfos(bankName!!, bankAmount, bankCur)
            adapter.addInfo(bankdata)
        }

        val w_id = sp.getInt("walletID", 0)
        for (w in 1..w_id) {
            val walletName = sp.getString("name_of_wallet_$w", "null")
            val walletAmount = sp.getFloat("amount_in_wallet_$w", 0F)
            val walletCur = sp.getString("currency_wallet_$w", "null")?.last().toString()
            val walletdata = WalletInfos(walletName!!, walletAmount, walletCur)
            adapter.addInfo(walletdata)
        }

        //change the name of user
        newnamebtn.text = getString(R.string.hellotxt, sp.getString("userName", "User"))
        val dialog = Dialog(this)
        newnamebtn.setOnClickListener {
            dialog.setContentView(R.layout.layout_for_alert_dialog_newname)
            // Set the layout parameters for the dialog window
            //dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val okaybtn by lazy { dialog.findViewById<Button>(R.id.okbtn) }
            val cancelbtn by lazy { dialog.findViewById<Button>(R.id.cancelbtn) }
            val nametext by lazy { dialog.findViewById<EditText>(R.id.entername) }

            okaybtn.setOnClickListener {
                val userName = nametext.text.toString()
                sp.edit().putString("userName", userName).apply()
                newnamebtn.text = getString(R.string.hellotxt, userName)
                dialog.dismiss()
            }
            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }

        //btn for settings
        settingsbtn.setOnClickListener {
            val y = Intent(this, SettingsActivity::class.java)
            startActivity(y)
        }

        //Buttons add & substraction money
        addBtn.setOnClickListener {
            val x = Intent(this, AddBtnActivity::class.java)
            startActivity(x)
        }
        substractionBtn.setOnClickListener {
            val y = Intent(this, SubstractionBtnActivity::class.java)
            startActivity(y)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI
            val selectedImageUri = data.data
            val mime = MimeTypeMap.getSingleton()
            val type = mime.getExtensionFromMimeType(selectedImageUri?.let {
                contentResolver.getType(
                    it
                )
            })

            val inputStream = selectedImageUri?.let { contentResolver.openInputStream(it) }
            val output = FileOutputStream(File("${filesDir.absoluteFile}/profile_picture.${type}"))
            inputStream?.copyTo(output, 4 * 1024)
            inputStream?.close()

            sp.edit().putString("image_name", "profile_picture.${type}").apply()
            sp.edit().putBoolean("replacedImage", true).apply()

            // Update CircleImageView with the selected image
            userimage.setImageURI(selectedImageUri)
        }
    }
}