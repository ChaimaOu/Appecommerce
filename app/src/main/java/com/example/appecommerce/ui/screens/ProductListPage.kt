package com.example.appecommerce.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appecommerce.data.productsList
import com.example.appecommerce.model.Product
import com.example.appecommerce.ui.components.BottomNavBar

@Composable
fun ProductListPage(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onProductClick: (Int) -> Unit

) {

    Scaffold(
        bottomBar = {
            BottomNavBar(
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

            // --- Top Bar ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "back")
                }
                Text(
                    text = "Tote Bags",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Fake Filters ---
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip("Price")
                FilterChip("New Arrivals")
                FilterChip("Collections")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Products Grid ---
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productsList) { product ->
                    ProductCard(product) {
                        onProductClick(product.id)
                    }

                }
            }
        }
    }
}


@Composable
fun FilterChip(text: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = Color(0xFFF2E7E7)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit = {}) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = product.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "$${product.price}",
            color = Color(0xFFB71C1C),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
