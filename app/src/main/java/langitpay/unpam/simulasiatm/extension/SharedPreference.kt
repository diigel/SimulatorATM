package langitpay.unpam.simulasiatm.extension

import android.content.Context
import android.content.SharedPreferences

class SharedPreference {
    private val PREFS_NAME = "MR_PREFS"

    fun save(context: Context, key: String, text: String) {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        editor.putString(key, text)
        editor.apply()
    }

    fun getValue(context: Context, key: String): String? {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val text: String?
        text = settings.getString(key, null)
        return text
    }

    fun clearSharedPreference(context: Context) {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()

        editor.clear()
        editor.apply()
    }
}