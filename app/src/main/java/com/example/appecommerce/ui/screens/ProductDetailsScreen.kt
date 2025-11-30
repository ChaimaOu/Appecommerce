package com.example.appecommerce.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.appecommerce.data.productsList
import com.example.appecommerce.ui.components.BottomNavBar
import com.example.appecommerce.data.CartManager


@Composable
fun ProductDetailsScreen(
    productId: Int,
    navController: NavHostController,

    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    val product = productsList.find { it.id == productId }
        ?: return Text("Product not found", modifier = Modifier.padding(30.dp))

    Scaffold(
        bottomBar = {
            BottomNavBar(
                onHomeClick = {},
                onCartClick = {navController.navigate("cart")},
                onProfileClick = {}
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // ----------- SCROLLABLE CONTENT -----------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {

                // IMAGE + BACK + SHARE
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {

                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                    IconButton(
                        onClick = { /* share */ },
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                    }
                }

                // PRODUCT INFO
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = product.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${product.price} MAD",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFB71C1C)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Handcrafted in Marrakech",
                        color = Color(0xFFB71C1C),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Eco-friendly cotton tote bag with intricate Moroccan embroidery. Perfect for everyday use.",
                        fontSize = 15.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )

                    // HORIZONTAL LIST
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        val otherProducts = productsList.filter { it.id != product.id }

                        items(otherProducts) { item ->

                            Column(
                                modifier = Modifier
                                    .width(140.dp)
                                    .clickable { onProductClick(item.id) }
                            ) {
                                Image(
                                    painter = painterResource(id = item.imageRes),
                                    contentDescription = item.name,
                                    modifier = Modifier
                                        .height(120.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(Modifier.height(6.dp))

                                Text(
                                    text = item.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "${item.price} MAD",
                                    fontSize = 13.sp,
                                    color = Color(0xFFB71C1C),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // ----------- FIXED ADD TO CART BUTTON -----------
            Button(
                onClick = {
                    CartManager.addToCart(product)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)

            ) {
                Text("Add to Cart", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
