package fr.titouan.ecommerceapp.ui.screens.product.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import fr.titouan.ecommerceapp.EcommerceApplication
import fr.titouan.ecommerceapp.data.GetSampleData
import fr.titouan.ecommerceapp.data.network.NetworkResult
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.model.Product
import fr.titouan.ecommerceapp.ui.screens.order.views.OrderViewModel
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    data class Success(val product: Product) : ProductUiState
    data class Error(val message : String) : ProductUiState
    data object Loading : ProductUiState
}

class ProductViewModel(
    private val ecommerceRepository: EcommerceRepository
) : ViewModel() {

    var mProductUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set
    var mProduct: Product? by mutableStateOf(null)
        private set

    fun getProduct(productId : Int) {
        viewModelScope.launch {
            mProductUiState = ProductUiState.Loading
            val result = ecommerceRepository.getProduct(productId)
            mProductUiState = when (result) {
                is NetworkResult.Success -> ProductUiState.Success(result.data)
                is NetworkResult.Error -> ProductUiState.Error(result.message ?: "Une erreur inconnue est survenue.")
                NetworkResult.Loading -> ProductUiState.Loading
            }
        }
    }


    fun getSafeProduct(productId: Int) {
        mProduct = GetSampleData.getProduct(productId)
        mProductUiState = ProductUiState.Success(product = mProduct!!)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                ProductViewModel(ecommerceRepository = ecommerceRepository)
            }
        }
    }
}