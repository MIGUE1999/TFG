package com.padeltournaments.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import com.padeltournaments.R
import com.padeltournaments.presentation.navigation.*
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.util.PaymentSucceed
import com.razorpay.AutoReadOtpHelper
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity: ComponentActivity(), PaymentResultListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                window.navigationBarColor = resources.getColor(R.color.purple_500)
                BottomNavGraph()
            }
        }
    }
    override fun onPaymentSuccess(paymentId: String?) {
        // Lógica para el pago exitoso
        PaymentSucceed.inscriptionSucceed = true
        Toast.makeText(this, "Pago realizado con exito", Toast.LENGTH_SHORT).show()

    }
    override fun onPaymentError(code: Int, message: String?) {
        // Lógica para el error de pago
        PaymentSucceed.inscriptionSucceed = false
        Toast.makeText(this, "Pago abortado", Toast.LENGTH_SHORT).show()
    }
}





