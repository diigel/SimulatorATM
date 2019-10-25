package langitpay.unpam.simulasiatm.menu

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tarik_tunai.*
import langitpay.unpam.simulasiatm.extension.Extension
import langitpay.unpam.simulasiatm.R
import langitpay.unpam.simulasiatm.extension.SharedPreference
import org.jetbrains.anko.toast

class TarikTunaiActivity : AppCompatActivity() {

    private val sp = SharedPreference()
    private var userSaldo: String? = null
    private var nominal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarik_tunai)

        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        btSubmit.setOnClickListener {
            userSaldo = sp.getValue(this, resources.getString(R.string.user_saldo))
            nominal = etNominal.text.toString()
            val saldo:Int = userSaldo!!.toInt()
            val nominalTarik:Int = nominal!!.toInt()
            when {
                nominal.isNullOrEmpty() -> toast("Masukkan Nominal")
                nominalTarik > saldo -> toast("Saldo Tidak Mencukupi")
                else -> showAlertDialog()
            }
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Tarik Tunai")
            .setMessage("Apakah kamu ingin Tarik Tunai ?  \nNominal ${Extension.formatRupiah(nominal!!.toInt())}")
            .setNegativeButton("Kembali", null)
            .setPositiveButton("Kirim") { _, _ ->
                val newSaldo: Int = userSaldo!!.toInt() - nominal!!.toInt()
                sp.save(this, resources.getString(R.string.user_saldo), "$newSaldo")
                finish()
                toast("Sukses Tarik Tunai ${Extension.formatRupiah(nominal!!.toInt())}")
            }.create().show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
