package com.example.appecommerce.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(
    selectedItem: Int = 0,
    cartCount: Int = 0,           // <---- AJOUT ICI
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {

        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = onHomeClick,
            icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = if (selectedItem == 0) Color.Red else Color.Gray
                )
            },
            label = { Text("Home") }
        )

        // ---- CART AVEC BADGE ----
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = onCartClick,
            icon = {
                Box {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = "Cart",
                        tint = if (selectedItem == 1) Color.Red else Color.Gray
                    )

                    if (cartCount > 0) {
                        Badge(
                            modifier = Modifier
                                .offset(x = 12.dp, y = (-6).dp)
                        ) {
                            Text(cartCount.toString())
                        }
                    }
                }
            },
            label = { Text("Cart") }
        )

        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = onProfileClick,
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = if (selectedItem == 2) Color.Red else Color.Gray
                )
            },
            label = { Text("Profile") }
        )
    }
}
