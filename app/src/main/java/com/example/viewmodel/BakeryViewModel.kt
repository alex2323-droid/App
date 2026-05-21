package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.CartItem
import com.example.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BakeryViewModel : ViewModel() {

    private val sampleProducts = listOf(
        Product("1", "Pan Francés", "Crujiente por fuera, suave por dentro.", 1.50, "https://images.unsplash.com/photo-1597079910443-60c43fc4f729?auto=format&fit=crop&w=400&q=80"),
        Product("2", "Croissant", "Delicioso y hojaldrado con mantequilla.", 2.50, "https://images.unsplash.com/photo-1555507036-ab1f4038808a?auto=format&fit=crop&w=400&q=80", isPromotion = true),
        Product("3", "Pan de Muerto", "Tradicional pan dulce con esencia de azahar.", 3.00, "https://images.unsplash.com/photo-1605333390317-0b190fbae888?auto=format&fit=crop&w=400&q=80"),
        Product("4", "Magdalenas", "Esponjosas y dulces para acompañar el café.", 4.00, "https://images.unsplash.com/photo-1587241321921-91a834d6d191?auto=format&fit=crop&w=400&q=80"),
        Product("5", "Tarta de Fresas", "Fina base con crema pastelera y fresas frescas.", 15.00, "https://images.unsplash.com/photo-1565958011703-44f9829ba187?auto=format&fit=crop&w=400&q=80", isPromotion = true),
        Product("6", "Donas Glaseadas", "Clásicas donas con glaseado de vainilla.", 1.20, "https://images.unsplash.com/photo-1551024601-bec78aea704b?auto=format&fit=crop&w=400&q=80")
    )

    private val _products = MutableStateFlow(sampleProducts)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.update { items ->
            val existingItem = items.find { it.product.id == product.id }
            if (existingItem != null) {
                items.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                items + CartItem(product, 1)
            }
        }
    }

    fun removeFromCart(product: Product) {
        _cartItems.update { items ->
            items.filter { it.product.id != product.id }
        }
    }

    fun decreaseQuantity(product: Product) {
        _cartItems.update { items ->
            items.mapNotNull {
                if (it.product.id == product.id) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
                } else {
                    it
                }
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
    
    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }
}
