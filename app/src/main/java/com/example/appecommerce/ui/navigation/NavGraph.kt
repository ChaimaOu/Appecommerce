package com.example.appecommerce.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appecommerce.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "logo"
    ) {

        // ------------------------- LOGO PAGE -------------------------
        composable("logo") {
            LogoPage(
                onStartClick = { navController.navigate("login") },
                onFinish = { }
            )
        }

        // ------------------------- LOGIN PAGE -------------------------
        composable("login") {
            LoginPage(
                onNavigateToSignUp = { navController.navigate("signup") },
                onLoginSuccess = {
                    navController.navigate("productList") {
                        popUpTo("login") { inclusive = true }
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
                onBackClick = { navController.popBackStack() }
            )
        }

        // ------------------------- PRODUCT LIST PAGE -------------------------
        composable("productList") {
            ProductListPage(
                onBackClick = { navController.popBackStack() },
                onHomeClick = { /* déjà ici */ },
                onCartClick = { navController.navigate("cart") },
                onProfileClick = { navController.navigate("profile") },
                onProductClick = { id -> navController.navigate("productDetails/$id") }
            )
        }

        // ------------------------- PRODUCT DETAILS PAGE -------------------------
        composable("productDetails/{productId}") { backStack ->
            val id = backStack.arguments?.getString("productId")?.toInt() ?: 0

            ProductDetailsScreen(
                productId = id,
                navController = navController,
                onBackClick = { navController.popBackStack() },
                onProductClick = { selectedId ->
                    navController.navigate("productDetails/$selectedId")
                }
            )
        }

        // ------------------------- CART PAGE -------------------------
        composable("cart") {
            CartPage(
                onBackClick = { navController.popBackStack() },
                onHomeClick = { navController.navigate("productList") },
                onCartClick = { /* déjà ici */ },
                onProfileClick = { navController.navigate("profile") },

                // ❤️ Checkout → vider panier + aller page succès
                onCheckoutClick = {
                    navController.navigate("orderSuccess") {
                        popUpTo("cart") { inclusive = true }
                    }
                }
            )
        }

        // ------------------------- ORDER SUCCESS PAGE -------------------------
        composable("orderSuccess") {
            OrderSuccessPage(
                onHomeClick = { navController.navigate("productList") }
            )
        }

        // ------------------------- PROFILE PAGE -------------------------

    }
}
