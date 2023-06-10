package com.padeltournaments.presentation.viewmodels

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
/*
class RazorPayments(private val context: Context, private val activity: Activity) : PaymentResultListener{

    fun savePayments(amount: Int) {
        Checkout.preload(activity)
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_xpZWqx3vLFKVRg")
        try {
            val options = JSONObject()
            options.put("name", "Metodo de pago")
            options.put("description", "Pago para la inscripcion al torneo")
            options.put("theme.color", "#3399cc")
            options.put("currency", "EUR")
            options.put("ammount", amount)

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            checkout.open(activity, options)
        } catch (e: Exception) {
            print("Error en el pago")
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(context, "Payment is successful : " + p0, Toast.LENGTH_SHORT).show();
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(context, "Pago realizado con exito", Toast.LENGTH_SHORT).show();
    }


}
*/
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