package langitpay.unpam.simulasiatm.menu

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setor_tunai.*
import langitpay.unpam.simulasiatm.extension.Extension
import langitpay.unpam.simulasiatm.R
import langitpay.unpam.simulasiatm.extension.SharedPreference
import org.jetbrains.anko.toast

class SetorTunaiActivity : AppCompatActivity() {

    private val sp = SharedPreference()
    private var userSaldo: String? = null
    private var nominalSetor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setor_tunai)

        // initialize action bar
        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        btSubmit.setOnClickListener {
            userSaldo = sp.getValue(this, resources.getString(R.string.user_saldo))
            nominalSetor = etNominal.text.toString()
            if (nominalSetor != null)
                showAlertDialog()
            else
                toast("Masukkan Nominal")
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Setor Tunai")
            .setMessage("Apakah Kamu Yakin ingin Setor Tunai ? \nNominal ${Extension.formatRupiah(nominalSetor!!.toInt())}")
            .setNegativeButton("Kembali", null)
            .setPositiveButton("Kirim") { _, _ ->
                val newSaldo: Int = userSaldo!!.toInt() + nominalSetor!!.toInt()
                sp.save(this, resources.getString(R.string.user_saldo), "$newSaldo")
                finish()
                toast("Sukses Setor Tunai ${Extension.formatRupiah(nominalSetor!!.toInt())}")
            }.create().show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
