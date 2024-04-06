package fr.titouan.ecommerceapp.ui.screens.account.login.orders.views

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
import fr.titouan.ecommerceapp.data.repository.EcommerceRepository
import fr.titouan.ecommerceapp.model.Order
import fr.titouan.ecommerceapp.model.Product
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface OrdersUiState {
    data class Success(val orders: List<Order>) : OrdersUiState
    data class Error(val message : String) : OrdersUiState
    data object Loading : OrdersUiState
}

class OrdersViewModel(
    private val ecommerceRepository: EcommerceRepository,
) : ViewModel() {
    var ordersUiState: OrdersUiState by mutableStateOf(OrdersUiState.Loading)
        private set
    var orders: List<Order> by mutableStateOf(listOf())
        private set

    fun getOrders() {
        viewModelScope.launch {
            ordersUiState = OrdersUiState.Loading
            ordersUiState = try {
                orders = ecommerceRepository.getOrders()

                OrdersUiState.Success(
                    orders
                )
            } catch (e: IOException) {
                OrdersUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                OrdersUiState.Error(e.message.toString())
            }

        }
    }

    fun getSafeOrders(){
        orders = GetSampleData.getOrders(8)
        ordersUiState = OrdersUiState.Success(orders = orders)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                OrdersViewModel(ecommerceRepository = ecommerceRepository)
            }
        }
    }
}