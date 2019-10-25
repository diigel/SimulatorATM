package langitpay.unpam.simulasiatm.extension

import java.text.NumberFormat
import java.util.*

object Extension {
    fun formatRupiah(amount: Int): String {
        return NumberFormat.getNumberInstance(Locale.US).format(amount.toLong()).replace(",", ".")
    }
}