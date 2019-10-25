package langitpay.unpam.simulasiatm.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_transfer.*
import langitpay.unpam.simulasiatm.extension.Extension
import langitpay.unpam.simulasiatm.R
import langitpay.unpam.simulasiatm.extension.SharedPreference
import org.jetbrains.anko.toast

class TransferActivity : AppCompatActivity() {

    private val sp = SharedPreference()
    private var userSaldo : String? = null
    private var tujuan : String? = null
    private var nominal : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        val actionbar = supportActionBar

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        btSubmit.setOnClickListener {
            userSaldo = sp.getValue(this,resources.getString(R.string.user_saldo))
            tujuan = etTujuan.text.toString()
            nominal = etNominal.text.toString().trim()

            val saldo : Int = userSaldo!!.toInt()
            val nominalTransfer : Int = nominal!!.toInt()

            when {
                nominal.isNullOrEmpty() -> toast("Masukkan Nominal")
                nominalTransfer > saldo -> toast("Saldo Tidak Mencukupi")
                else -> showAlertDialog()
            }
        }
    }

    private fun showAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("Transfer Saldo")
            .setMessage("Apakah Kamu Yakin ingin transfer Saldo ? \nTujuan A/n $tujuan \nNominal ${Extension.formatRupiah(nominal!!.toInt())}")
            .setNegativeButton("Kembali",null)
            .setPositiveButton("Kirim") { _, _ ->
                val newSaldo : Int = userSaldo!!.toInt() - nominal!!.toInt()
                sp.save(this,resources.getString(R.string.user_saldo), "$newSaldo")
                toast("Sukses Transfer Saldo $nominal")
                finish()
            }.create().show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
