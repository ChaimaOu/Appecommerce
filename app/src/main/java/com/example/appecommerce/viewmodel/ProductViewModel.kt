package com.example.appecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appecommerce.model.Product
import com.example.appecommerce.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repo = ProductRepository()

    // --- Produits de l'API ---
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    // --- Loading ---
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // --- Search Query ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // --- Selected Filter ---
    private val _selectedFilter = MutableStateFlow<String?>(null)
    val selectedFilter: StateFlow<String?> = _selectedFilter

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun applyFilter(filter: String) {
        if (_selectedFilter.value == filter) {
            _selectedFilter.value = null // Deselect if already selected
        } else {
            _selectedFilter.value = filter
        }
    }

    // --- Produits filtrés par la recherche et le filtre ---
    val filteredProducts = combine(_products, _searchQuery, _selectedFilter) { list, query, filter ->
        var result = list

        // 1. Filter by Search Query
        if (query.isNotBlank()) {
            result = result.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }

        // 2. Filter by Category/Filter
        if (filter != null) {
            when (filter) {
                "Price" -> result = result.sortedBy { it.price } // Example: Sort by price
                "New Arrivals" -> result = result.filter { it.isNew }
                "Collections" -> { /* Logic for collections if needed */ }
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
}
