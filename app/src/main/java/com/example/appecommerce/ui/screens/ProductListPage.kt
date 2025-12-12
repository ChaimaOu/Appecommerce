package com.example.appecommerce.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.appecommerce.data.CartManager.cartItems
import com.example.appecommerce.viewmodel.ProductViewModel
import com.example.appecommerce.model.Product
import com.example.appecommerce.ui.components.BottomNavBar


@Composable
fun ProductListPage(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {


    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredProducts by viewModel.filteredProducts.collectAsState()


    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val cartItemsDB by viewModel.cartItems.collectAsState(initial = emptyList())


    Scaffold(
        bottomBar = {
            BottomNavBar(

            cartCount = cartItemsDB.sumOf { it.quantity },

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

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = { Text("Search products...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD32F2F),
                    focusedLabelColor = Color(0xFFD32F2F)
                ),
                singleLine = true
            )


            Spacer(modifier = Modifier.height(12.dp))



            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(
                    text = "Price",
                    isSelected = selectedFilter == "Price",
                    onClick = { viewModel.applyFilter("Price") }
                )

                FilterChip(
                    text = "New Arrivals",
                    isSelected = selectedFilter == "New Arrivals",
                    onClick = { viewModel.applyFilter("New Arrivals") }
                )

                FilterChip(
                    text = "Collections",
                    isSelected = selectedFilter == "Collections",
                    onClick = { viewModel.applyFilter("Collections") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredProducts) { product ->
                    ProductCard(product) {
                            onProductClick(product.id)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {

    val bgColor = if (isSelected) Color(0xFFB71C1C) else Color(0xFFF2E7E7)
    val textColor = if (isSelected) Color.White else Color.Black

    Surface(
        shape = MaterialTheme.shapes.small,
        color = bgColor,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}


@Composable
fun ProductCard(product: Product, onClick: () -> Unit = {}) {
    Column(modifier = Modifier.clickable { onClick() }) {

        AsyncImage(
            model = product.image,   // URL de l'image venant de l'API
            contentDescription = product.name,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
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
