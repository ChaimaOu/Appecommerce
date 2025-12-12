package com.example.appecommerce.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderSuccessPage(onHomeClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "🎉 Commande réussie !",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Merci pour votre achat.",
            fontSize = 18.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onHomeClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour à l'accueil")
        }
    }
}
