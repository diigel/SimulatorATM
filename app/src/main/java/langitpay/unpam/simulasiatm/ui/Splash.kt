package langitpay.unpam.simulasiatm.ui

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import langitpay.unpam.simulasiatm.R
import langitpay.unpam.simulasiatm.extension.SharedPreference

class Splash : AppCompatActivity() {

    private var _active = true
    private var _splashTime = 3400
    internal var sp = SharedPreference()
    private var userame : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        val splashTread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    while (_active && waited < _splashTime) {
                        sleep(100)
                        if (_active) {
                            waited += 100
                        }
                    }
                } catch (e: InterruptedException) {
                    // do nothing
                } finally {
                    userame = sp.getValue(this@Splash,resources.getString(R.string.username))
                    if (userame == null) {
                        val newIntent = Intent(applicationContext, Login::class.java)
                        startActivityForResult(newIntent, 0)
                    }else{
                        val newIntent = Intent(applicationContext, MainActivity::class.java)
                        startActivityForResult(newIntent, 0)
                    }
                    finish()
                }
            }
        }
        splashTread.start()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            _active = false
        }
        return true
    }
}
