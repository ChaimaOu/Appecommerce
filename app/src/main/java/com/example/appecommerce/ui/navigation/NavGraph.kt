package com.example.appecommerce.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appecommerce.ui.screens.LogoPage
import com.example.appecommerce.ui.screens.LoginPage
import com.example.appecommerce.ui.screens.ProductDetailsScreen
import com.example.appecommerce.ui.screens.SignUpPage
import com.example.appecommerce.ui.screens.ProductListPage
import com.example.appecommerce.ui.screens.CartPage

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "logo"
    ) {

        // ------------------------- LOGO PAGE -------------------------
        composable("logo") {
            LogoPage(
                onStartClick = {
                    navController.navigate("login")
                },
                onFinish = { }
            )
        }

        // ------------------------- LOGIN PAGE -------------------------
        composable("login") {
            LoginPage(
                onNavigateToSignUp = {
                    navController.navigate("signup")
                },
                onLoginSuccess = {
                    navController.navigate("productList") {
                        popUpTo("login") { inclusive = true } // optional
                    }
                }
            )
        }

        // ------------------------- SIGN UP PAGE -------------------------
        composable("signup") {
            SignUpPage(
                onSignUpSuccess = {
                    navController.navigate("productList") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // ------------------------- PRODUCT LIST PAGE -------------------------
        composable("productList") {
            ProductListPage(
                onBackClick = { navController.popBackStack() },
                onHomeClick = { /* déjà ici */ },
                onCartClick = { navController.navigate("cart") },
                onProfileClick = { navController.navigate("profile") },
                onProductClick = { id ->
                    navController.navigate("productDetails/$id")
                }
            )
        }
        // ------------------------- PRODUCT DETAILS PAGE -------------------------
        composable("productDetails/{productId}") { backStack ->
            val id = backStack.arguments?.getString("productId")?.toInt() ?: 0
            ProductDetailsScreen(
                productId = id,
                navController = navController,   // ❤️ ajoute ceci

                onBackClick = { navController.popBackStack() },
                onProductClick = { selectedId ->
                    navController.navigate("productDetails/$selectedId")
                }
            )
        }

        // ---------------------- CART PAGE ------------------------
        composable("cart") {
            CartPage(
                // Uses default CartManager.cartItems
                onBackClick = { navController.popBackStack() },
                onHomeClick = { navController.navigate("productList") },
                onCartClick = { navController.navigate("cart") },
                onProfileClick = { navController.navigate("profile") }
            )
        }

    }
}
