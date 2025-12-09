package com.example.appecommerce.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.appecommerce.model.Product
import com.example.appecommerce.ui.components.BottomNavBar
import com.example.appecommerce.data.CartManager


@Composable
fun CartPage(
    cartItems: List<Product> = CartManager.cartItems, // Default to CartManager
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    // Quantities
    var quantities by remember {
        mutableStateOf(cartItems.associate { it.id to 1 }.toMutableMap())
    }

    // Prices
    val subtotal = cartItems.sumOf { it.price * (quantities[it.id] ?: 1) }
    val shipping = 10.0
    val total = subtotal + shipping

    // Snackbar State
    val snackbarHostState = remember { SnackbarHostState() }
    var showSuccess by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavBar(
                selectedItem = 1,
                onHomeClick = onHomeClick,
                onCartClick = onCartClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // TOP BAR
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Spacer(modifier = Modifier.width(90.dp))

                Text(
                    text = "Cart",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            // CART ITEMS
            cartItems.forEach { product ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(model = product.image),
                        contentDescription = product.name,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Moroccan tote bag",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Minus
                        IconButton(
                            onClick = {
                                if ((quantities[product.id] ?: 1) > 1) {
                                    quantities = quantities.toMutableMap().also {
                                        it[product.id] = (it[product.id] ?: 1) - 1
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove")
                        }

                        Text(
                            text = "${quantities[product.id]}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Plus
                        IconButton(
                            onClick = {
                                quantities = quantities.toMutableMap().also {
                                    it[product.id] = (it[product.id] ?: 1) + 1
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // PRICES
            PriceRow("Subtotal", subtotal)
            PriceRow("Shipping", shipping)
            PriceRow("Total", total, bold = true)

            Spacer(modifier = Modifier.height(20.dp))

            // CHECKOUT BUTTON
            Button(
                onClick = {
                    showSuccess = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Checkout", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }

    // Show Snackbar after clicking checkout
    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            snackbarHostState.showSnackbar("Commande validée avec succès !")
            showSuccess = false
        }
    }
}


@Composable
fun PriceRow(label: String, value: Double, bold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        Text(
            text = "$${String.format("%.2f", value)}",
            fontSize = 16.sp,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}
