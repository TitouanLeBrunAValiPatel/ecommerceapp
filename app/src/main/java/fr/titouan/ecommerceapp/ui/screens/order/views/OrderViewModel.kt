package fr.titouan.ecommerceapp.ui.screens.order.views

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
import fr.titouan.ecommerceapp.model.Order
import kotlinx.coroutines.launch

sealed interface OrderUiState {
    data class Success(val order: Order) : OrderUiState
    data class Error(val message : String) : OrderUiState
    data object Loading : OrderUiState
}

class OrderViewModel(
    private val ecommerceRepository: EcommerceRepository
) : ViewModel() {

    var mOrderUiState: OrderUiState by mutableStateOf(OrderUiState.Loading)
        private set
    var mOrder: Order? by mutableStateOf(null)
        private set

    fun getOrder(orderId : Int) {
        viewModelScope.launch {
            mOrderUiState = OrderUiState.Loading
            val result = ecommerceRepository.getOrder(orderId)
            mOrderUiState = when (result) {
                is NetworkResult.Success -> OrderUiState.Success(result.data)
                is NetworkResult.Error -> OrderUiState.Error(
                    result.message ?: "Une erreur inconnue est survenue."
                )
                NetworkResult.Loading -> OrderUiState.Loading
            }
        }
    }


    fun getSafeOrder(orderId: Int) {
        mOrder = GetSampleData.getOrder(orderId)
        mOrderUiState = OrderUiState.Success(order = mOrder!!)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                OrderViewModel(ecommerceRepository = ecommerceRepository)
            }
        }
    }
}