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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appecommerce.ui.components.BottomNavBar
import com.example.appecommerce.viewmodel.ProductViewModel
import com.example.appecommerce.database.CartItemEntity

@Composable
fun CartPage(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    viewModel: ProductViewModel = viewModel()
)
{
    val cartItems by viewModel.cartItems.collectAsState(initial = emptyList())

    val subtotal = cartItems.sumOf { it.price * it.quantity }
    val shipping = 10.0
    val total = subtotal + shipping

    val snackbarHostState = remember { SnackbarHostState() }
    var showSuccess by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            val cartItems by viewModel.cartItems.collectAsState(initial = emptyList())

            BottomNavBar(
                selectedItem = 1,
                cartCount = cartItems.sumOf { it.quantity },  // <---- badge dynamique
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Spacer(modifier = Modifier.width(90.dp))

                Text("Cart", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))


            cartItems.forEach { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(item.image),
                        contentDescription = item.name,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Moroccan tote bag", fontSize = 13.sp, color = Color.Gray)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        IconButton(
                            onClick = {
                                if (item.quantity > 1) {
                                    viewModel.updateQuantity(item.id, item.quantity - 1)
                                } else {
                                    viewModel.removeFromCart(item)
                                }
                            }
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove")
                        }

                        Text(
                            text = "${item.quantity}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = {
                                viewModel.updateQuantity(item.id, item.quantity + 1)
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            PriceRow("Subtotal", subtotal)
            PriceRow("Shipping", shipping)
            PriceRow("Total", total, bold = true)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.clearCart()
                    onCheckoutClick()
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
}


@Composable
fun PriceRow(label: String, value: Double, bold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        Text(
            text = "${String.format("%.2f", value)} MAD",
            fontSize = 16.sp,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}
