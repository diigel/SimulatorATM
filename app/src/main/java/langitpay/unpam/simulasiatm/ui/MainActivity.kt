package langitpay.unpam.simulasiatm.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lin_dialog.*
import langitpay.unpam.simulasiatm.extension.Extension
import langitpay.unpam.simulasiatm.R
import langitpay.unpam.simulasiatm.extension.SharedPreference
import langitpay.unpam.simulasiatm.menu.SetorTunaiActivity
import langitpay.unpam.simulasiatm.menu.TarikTunaiActivity
import langitpay.unpam.simulasiatm.menu.TransferActivity


class MainActivity : AppCompatActivity(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    // init variable type string
    private var saldo: String? = null
    private var username: String? = null

    // init / mengenalkan kelas sharedpreferences
    private var sp = SharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val swipeLayout = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
            ContextCompat.getColor(this, android.R.color.holo_blue_bright),
            ContextCompat.getColor(this, android.R.color.holo_green_light),
            ContextCompat.getColor(this, android.R.color.holo_orange_light),
            ContextCompat.getColor(this, android.R.color.holo_red_light)
        )

        // mengambil saldo dan username pada sharedpreferences
        saldo =
            sp.getValue(this, resources.getString(R.string.user_saldo))
        username =
            sp.getValue(this, resources.getString(R.string.username))

        // init action button
        btCek.setOnClickListener(this)
        btSt.setOnClickListener(this)
        btTf.setOnClickListener(this)
        btTtunai.setOnClickListener(this)
    }

    // action button
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btCek -> dialogShowSaldo()
            R.id.btTf -> startActivity(
                Intent(this, TransferActivity::class.java)
            )
            R.id.btTtunai -> startActivity(
                Intent(this, TarikTunaiActivity::class.java)
            )
            R.id.btSt -> startActivity(
                Intent(this, SetorTunaiActivity::class.java)
            )

        }
    }

    // menampilkan popup untuk ceksaldo
    private fun dialogShowSaldo() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.lin_dialog)

        dialog.username.text = username
        dialog.tvSaldo?.text = Extension.formatRupiah(saldo!!.toInt())
        dialog.bt_close?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // life circle untuk mengambil kembali saldo dari sharedprefereces setiap kembali lgi ke activity
    override fun onRestart() {
        super.onRestart()
        saldo =
            sp.getValue(this, resources.getString(R.string.user_saldo))
    }

    // life circle untuk mengambil kembali saldo dari sharedprefereces setiap kembali lgi ke activity dan refresh layout
    override fun onRefresh() {
        Handler().postDelayed({ swipe.isRefreshing = false }, 2000)
        saldo =
            sp.getValue(this, resources.getString(R.string.user_saldo))
    }

    // init menu pada actionbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // action menu pada action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_logout) {
            logout()
            return true
        }else if (id == R.id.about){
            startActivity(
                Intent(this,About::class.java)
            )
        }
        return super.onOptionsItemSelected(item)
    }

    // menampilkan popup / dialog setelah logout di klik
    private fun logout() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Kamu Yakin ingin Logout ? \nJika kamu logout saldo kamu akan di reset beserta username dan password")
            .setNegativeButton("Kembali", null)
            .setPositiveButton("Keluar") { _, _ ->
                sp.clearSharedPreference(this)
                val intent = Intent(this@MainActivity, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }.create().show()
    }

}
