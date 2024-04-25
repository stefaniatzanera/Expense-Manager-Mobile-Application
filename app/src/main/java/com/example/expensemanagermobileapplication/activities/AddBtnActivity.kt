package com.example.expensemanagermobileapplication.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.expensemanagermobileapplication.R
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import io.github.g00fy2.quickie.content.QRContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.ref.WeakReference

class AddBtnActivity : AppCompatActivity() {
    private val title by lazy { findViewById<TextView>(R.id.addbtntitle) }
    private val nameofdata by lazy { findViewById<TextView>(R.id.nameofdata2) }
    val qrbtn by lazy { findViewById<ImageButton>(R.id.qrbtn) }
    val addbtn by lazy { findViewById<ImageButton>(R.id.addbtn) }
    val infobtn by lazy { findViewById<ImageButton>(R.id.info) }
    val task = MyAsyncTask(this)

    companion object {
        class MyAsyncTask internal constructor(context: AddBtnActivity) : AsyncTask<String, String, String>() {

            private var resp: String? = null
            private val activityReference: WeakReference<AddBtnActivity> = WeakReference(context)

            override fun onPreExecute() {
                val activity = activityReference.get()
                Toast.makeText(activity, "Fetching data...", Toast.LENGTH_SHORT).show()
            }

            override fun doInBackground(vararg params: String?): String? {
                try {
                    val url = params[0]!!
                    val doc = Jsoup.connect(url).get()
                    val classes: Elements = doc.select(".receipt")
                    val text = classes.text()
                    resp = text.substringAfter("Συνολικού ποσού ").substringBefore(" ευρώ")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    resp = e.message
                } catch (e: Exception) {
                    e.printStackTrace()
                    resp = e.message
                }

                return resp
            }

            override fun onPostExecute(result: String?) {
                val activity = activityReference.get()
                if (result == null)
                    return
                if (activity == null || activity.isFinishing)
                    return
            }
           }
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_btn)

        val name = intent.getStringExtra("name")
        nameofdata.text = getString(R.string.nameofdata, name)
        val amount = intent.getFloatExtra("amount", 0f)
        val currency = intent.getStringExtra("currency")
        val keyString = intent.getStringExtra("key")!!
        val feature = intent.getStringExtra("feature")

        val sp = getSharedPreferences("userOptions", MODE_PRIVATE)

        val scanQrCodeLauncher = registerForActivityResult(ScanQRCode()) { result ->
            // handle QRResult
            if (result is QRResult.QRSuccess) {
                val content = result.content
                if (content is QRContent.Url) {
                    val url = content.url
                    lifecycleScope.launch( context = Dispatchers.Main){
                        task.execute(url)
                    }
                    lifecycleScope.launch( context = Dispatchers.Main){
                        val resp = task.get()
                        val enteredamount = resp.toFloatOrNull()
                        if (enteredamount == null) {
                            Toast.makeText(this@AddBtnActivity, "Something went wrong. Try manually!", Toast.LENGTH_SHORT).show()
                            finish()
                            return@launch
                        }
                        val res = addtheamount(enteredamount.toFloat(),amount)
                        val editor = sp.edit()
                        if (feature == "w")
                            editor.putFloat("amount_in_wallet_$keyString", res)
                        else
                            editor.putFloat("amount_in_bank_$keyString", res)
                        editor.apply()
                        Toast.makeText(this@AddBtnActivity, "Added: $enteredamount", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@AddBtnActivity, FirstPageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
        }

        qrbtn.setOnClickListener {
            scanQrCodeLauncher.launch(null)
        }

        val dialog = Dialog(this)
        addbtn.setOnClickListener {
            dialog.setContentView(R.layout.layout_for_alert_dialog_add_manual_amount)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val amountofdata = dialog.findViewById<TextView>(R.id.availableamount)
            val currencydata = dialog.findViewById<TextView>(R.id.cur)
            amountofdata.text = getString(R.string.availableamount, amount.toString())
            currencydata.text = getString(R.string.currency,currency)

            val okaybtn = dialog.findViewById<Button>(R.id.add)
            val cancelbtn = dialog.findViewById<Button>(R.id.cancelbtn)

            okaybtn.setOnClickListener {
                val enteranamount = dialog.findViewById<EditText>(R.id.enteranamount)
                val enteredamount = enteranamount.text.toString() // the amount which user entered
                amountofdata.text = getString(R.string.availableamount, amount.toString())
                currencydata.text = getString(R.string.currency,currencydata)
                val res = addtheamount(enteredamount.toFloat(),amount)
                val editor = sp.edit()
                if (feature == "w")
                    editor.putFloat("amount_in_wallet_$keyString", res)
                else
                    editor.putFloat("amount_in_bank_$keyString", res)
                editor.apply()

                Toast.makeText(this@AddBtnActivity, "result = $res", Toast.LENGTH_LONG).show()
                dialog.dismiss()
                val intent = Intent(this@AddBtnActivity, FirstPageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }

        infobtn.setOnClickListener{
            dialog.setContentView(R.layout.layout_for_info)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val titleinfo = dialog.findViewById<TextView>(R.id.infotitle)
            val descinfo = dialog.findViewById<TextView>(R.id.descriptioninfo)

            titleinfo.text = getString(R.string.infoaddbtntlt)
            descinfo.text = getString(R.string.infoaddbtndesc)

            dialog.setCancelable(true)
            dialog.show()
        }
    }

    private fun addtheamount(enteredamount : Float, existedamount : Float): Float {
        val result = existedamount + enteredamount
        return if (result >= 0) {
            result // Update existedAmount only if result is non-negative
        } else {
            existedamount // Keep existedAmount unchanged if result is negative
        }
    }
}

