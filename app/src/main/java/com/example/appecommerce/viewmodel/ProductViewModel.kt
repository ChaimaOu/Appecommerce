package com.example.appecommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.appecommerce.database.AppDataBase
import com.example.appecommerce.database.CartItemEntity
import com.example.appecommerce.model.Product
import com.example.appecommerce.repository.CartRepository
import com.example.appecommerce.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = ProductRepository()

    // ------------------- ROOM DATABASE -------------------
    private val db = Room.databaseBuilder(
        application,
        AppDataBase::class.java,
        "app_database"
    ).build()

    private val cartRepo = CartRepository(db.cartDao())

    val cartItems = cartRepo.cartItems

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val item = CartItemEntity(
                id = product.id,
                name = product.name,
                price = product.price,
                image = product.image,
                quantity = 1
            )
            cartRepo.addToCart(item)
        }
    }
    // -----------------------------------------------------


    // ---------------- PRODUITS API ------------------------
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedFilter = MutableStateFlow<String?>(null)
    val selectedFilter: StateFlow<String?> = _selectedFilter

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun applyFilter(filter: String) {
        _selectedFilter.value = if (_selectedFilter.value == filter) null else filter
    }

    val filteredProducts = combine(_products, _searchQuery, _selectedFilter) { list, query, filter ->
        var result = list

        if (query.isNotBlank()) {
            result = result.filter { it.name.contains(query, ignoreCase = true) }
        }

        if (filter != null) {
            when (filter) {
                "Price" -> result = result.sortedBy { it.price }
                "New Arrivals" -> result = result.filter { it.isNew }
                "Collections" -> {}
            }
        }

        result
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        loadProductsFromApi()
    }

    private fun loadProductsFromApi() {
        viewModelScope.launch {
            try {
                _products.value = repo.getProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun removeFromCart(item: CartItemEntity) {
        viewModelScope.launch {
            cartRepo.removeItem(item)
        }
    }

    fun updateQuantity(id: Int, newQty: Int) {
        viewModelScope.launch {
            cartRepo.updateQuantity(id, newQty)
        }
    }


}
