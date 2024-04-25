package com.example.expensemanagermobileapplication.activities

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.expensemanagermobileapplication.R
import com.example.expensemanagermobileapplication.adapter.DestinationAdapter
import com.example.expensemanagermobileapplication.dataClass.DestinationItem
import com.example.expensemanagermobileapplication.databinding.ActivityTransferBinding
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import io.github.g00fy2.quickie.content.QRContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.ref.WeakReference

class TransferActivity : AppCompatActivity() {
    val transfertitle by lazy { findViewById<TextView>(R.id.transferttl) }
    val from by lazy { findViewById<TextView>(R.id.from) }
    val amountto by lazy { findViewById<TextView>(R.id.amountto) }
    val currencyamount by lazy { findViewById<TextView>(R.id.currencyamount) }
    val destination by lazy { findViewById<Spinner>(R.id.selectdestination) }
    val qr by lazy { findViewById<ImageButton>(R.id.qrbtn) }
    val destinationLayout by lazy { findViewById<LinearLayout>(R.id.destinationplaceholder) }
    val manualtransfer by lazy { findViewById<ImageButton>(R.id.manualtransferbtn) }
    val infobtn by lazy { findViewById<ImageView>(R.id.info) }
    private lateinit var sp: SharedPreferences
    private lateinit var activityTransferBinding: ActivityTransferBinding
    val task = TransferActivity.Companion.MyAsyncTask(this)

    companion object {
        class MyAsyncTask internal constructor(context: TransferActivity) : AsyncTask<String, String, String>() {

            private var resp: String? = null
            private val activityReference: WeakReference<TransferActivity> = WeakReference(context)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTransferBinding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(activityTransferBinding.root)

        val name = intent.getStringExtra("name")
        from.text = name
        val amount = intent.getFloatExtra("amount",0F)
        amountto.text = amount.toString()
        val currency = intent.getStringExtra("currency")
        currencyamount.text = currency?.last().toString()
        val keyString = intent.getStringExtra("key")!!
        val feature = intent.getStringExtra("feature")

        val namesofdata = destination.findViewById<TextView>(R.id.name)
        var amountsofdata = destination.findViewById<TextView>(R.id.amount)
        val currencyofdata = destination.findViewById<TextView>(R.id.currency)

        sp = getSharedPreferences("userOptions", MODE_PRIVATE)

        val destinations = mutableListOf<DestinationItem>()
        destinations.add(DestinationItem("", "" ,"", -1, ""))
        val b_id = sp.getInt("bankID", 0)
        for (b in 1..b_id) {
            val bankName = sp.getString("name_of_bank_$b", "null")
            if (bankName == "null")
                continue
            var bankAmount = sp.getFloat("amount_in_bank_$b", 0F)
            val bankCur = sp.getString("currency_bank_$b", "null")!!
            if (bankCur == currency && name != bankName) {
                destinations.add(DestinationItem(bankName!!, bankAmount.toString(), bankCur.last().toString(), b, "b"))
            }
        }

        val w_id = sp.getInt("walletID", 0)
        for (w in 1..w_id) {
            val walletName = sp.getString("name_of_wallet_$w", "null")
            if (walletName == "null")
                continue
            var walletAmount = sp.getFloat("amount_in_wallet_$w", 0F)
            val walletCur = sp.getString("currency_wallet_$w", "null")!!
            if (walletCur == currency && name != walletName) {
                destinations.add(DestinationItem(walletName!!, walletAmount.toString(), walletCur.last().toString(), w, "w"))
            }
        }

        val adapter = DestinationAdapter(this, destinations)
        destination.adapter = adapter

        destinationLayout.setOnClickListener {
            destination.performClick()
        }

        destination.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position != 0) {
                    activityTransferBinding.destinationplaceholder.buttonname = destinations[position].name
                    activityTransferBinding.destinationplaceholder.buttonamount = destinations[position].amount
                    activityTransferBinding.destinationplaceholder.buttoncurrency = destinations[position].currency
                }
                else {
                    activityTransferBinding.destinationplaceholder.buttonname = getString(R.string.clicktoselect)
                    activityTransferBinding.destinationplaceholder.buttonamount = ""
                    activityTransferBinding.destinationplaceholder.buttoncurrency = ""
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val dialog = Dialog(this)
        manualtransfer.setOnClickListener{
            if (destination.selectedItemPosition == 0) {
                Toast.makeText(this@TransferActivity, "Nothing Selected!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            dialog.setContentView(R.layout.layout_for_manual_transfering)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var amountofdata = dialog.findViewById<TextView>(R.id.availableamount)
            val currencydata = dialog.findViewById<TextView>(R.id.cur)
            amountofdata.text = getString(R.string.availableamount, amount.toString())
            currencydata.text = getString(R.string.currency,currency)

            val transferbtn = dialog.findViewById<Button>(R.id.transfer)
            val cancelbtn = dialog.findViewById<Button>(R.id.cancelbtn)

            transferbtn.setOnClickListener {
                val enteranamount = dialog.findViewById<EditText>(R.id.enteranamount)
                val enteredamount = enteranamount.text.toString() // the amount which user entered
                amountofdata.text = getString(R.string.availableamount, amount.toString()) //amount from
                currencydata.text = getString(R.string.currency,currencydata)
                val res = amount - enteredamount.toFloat()
                if (res < 0F) {
                    Toast.makeText(this@TransferActivity, "Transaction not possible!", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                val editor = sp.edit()
                val toKey = (destination.selectedItem as DestinationItem).key
                val toKeyAmount = (destination.selectedItem as DestinationItem).amount.toFloat() + enteredamount.toFloat()
                val toKeyFeature = (destination.selectedItem as DestinationItem).feature

                if (feature == "w")
                    editor.putFloat("amount_in_wallet_$keyString", res)
                else
                    editor.putFloat("amount_in_bank_$keyString", res)

                if (toKeyFeature == "w")
                    editor.putFloat("amount_in_wallet_$toKey", toKeyAmount)
                else
                    editor.putFloat("amount_in_bank_$toKey", toKeyAmount)

                editor.apply()

                Toast.makeText(this@TransferActivity, "Transfer Successful", Toast.LENGTH_LONG).show()
                dialog.dismiss()
                val intent = Intent(this@TransferActivity, FirstPageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            cancelbtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }

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
                            Toast.makeText(this@TransferActivity, "Something went wrong. Try manually!", Toast.LENGTH_SHORT).show()
                            finish()
                            return@launch
                        }
                        val res = amount - enteredamount.toFloat()
                        val editor = sp.edit()
                        val toKey = (destination.selectedItem as DestinationItem).key
                        val toKeyAmount = (destination.selectedItem as DestinationItem).amount.toFloat() + enteredamount.toFloat()
                        val toKeyFeature = (destination.selectedItem as DestinationItem).feature

                        if (feature == "w")
                            editor.putFloat("amount_in_wallet_$keyString", res)
                        else
                            editor.putFloat("amount_in_bank_$keyString", res)

                        if (toKeyFeature == "w")
                            editor.putFloat("amount_in_wallet_$toKey", toKeyAmount)
                        else
                            editor.putFloat("amount_in_bank_$toKey", toKeyAmount)

                        editor.apply()

                        Toast.makeText(this@TransferActivity, "Transfer Successful", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                        val intent = Intent(this@TransferActivity, FirstPageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
        }

        qr.setOnClickListener{
            scanQrCodeLauncher.launch(null)
        }

        infobtn.setOnClickListener{
            dialog.setContentView(R.layout.layout_for_info)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val titleinfo = dialog.findViewById<TextView>(R.id.infotitle)
            val descinfo = dialog.findViewById<TextView>(R.id.descriptioninfo)

            titleinfo.text = getString(R.string.infotransferbtntlt)
            descinfo.text = getString(R.string.infotransferbtndesc)

            dialog.setCancelable(true)
            dialog.show()
        }
    }
}