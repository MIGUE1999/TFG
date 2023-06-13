package com.padeltournaments.presentation.viewmodels

import android.app.Activity
import com.razorpay.Checkout
import org.json.JSONObject

class RazorPayments(private val activity: Activity) {
    companion object {
        private const val KEY_API_KEY = "rzp_test_xpZWqx3vLFKVRg"
    }

    fun startPayment(amount: Int) {
        val checkout = Checkout()
        checkout.setKeyID(KEY_API_KEY)

        val options = JSONObject()
        options.put("name", "Padel Tournaments")
        options.put("description", "Inscripcion al torneo")
        options.put("amount", amount * 100) // El monto debe ser en céntimos, por lo que se multiplica por 100
        options.put("currency", "EUR") // Cambia al código de tu moneda

        checkout.open(activity, options)
    }
}