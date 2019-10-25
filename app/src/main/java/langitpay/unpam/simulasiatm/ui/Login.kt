package langitpay.unpam.simulasiatm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import langitpay.unpam.simulasiatm.R
import langitpay.unpam.simulasiatm.extension.SharedPreference
import org.jetbrains.anko.toast

class Login : AppCompatActivity() {

    private var username: String? = null
    private var password: String? = null
    private var usersaldo: String? = null
    private var sp = SharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usersaldo = sp.getValue(this, resources.getString(R.string.user_saldo))
        btLogin.setOnClickListener {
            username = etUsername.text.toString().trim()
            password = etPassword.text.toString()
            val usernameLength: Int = username!!.length
            val passLength: Int = etPassword.length()
            when {
                usernameLength < 5 -> toast("Username harus 5 digit")
                passLength < 6 -> toast("pasword harus lebih dari 6")
                else -> {
                    sp.save(this, resources.getString(R.string.username), username!!)
                    sp.save(this, resources.getString(R.string.password), password!!)
                    if (usersaldo == null)
                        sp.save(this, resources.getString(R.string.user_saldo), "1000000")

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
    }
}