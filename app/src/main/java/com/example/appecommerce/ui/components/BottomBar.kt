package com.example.appecommerce.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun BottomNavBar(
    selectedItem: Int = 0,     // 0 = Home, 1 = Cart, 2 = Profile
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

        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = onCartClick,
            icon = {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Cart",
                    tint = if (selectedItem == 1) Color.Red else Color.Gray
                )
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
