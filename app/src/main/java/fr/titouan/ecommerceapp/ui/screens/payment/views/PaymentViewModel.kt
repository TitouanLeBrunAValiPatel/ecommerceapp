package fr.titouan.ecommerceapp.ui.screens.payment.views

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
import fr.titouan.ecommerceapp.data.sharedPreferences.SessionManager
import fr.titouan.ecommerceapp.model.Order
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.ui.screens.order.views.OrderUiState
import kotlinx.coroutines.launch


sealed interface PaymentUiState {
    data class Success(val user: User) : PaymentUiState
    data class Error(val message : String) : PaymentUiState
    data object Loading : PaymentUiState
}

class PaymentViewModel(
    private val ecommerceRepository: EcommerceRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    var paymentUiStateUiState: PaymentUiState by mutableStateOf(PaymentUiState.Loading)
        private set

    var user: User? by mutableStateOf(null)
        private set

    init {
        getInformationUser()

    }
    fun getInformationUser() {
        viewModelScope.launch {
            paymentUiStateUiState = PaymentUiState.Loading
            val result = sessionManager.getUser()?.let { ecommerceRepository.getUser(it.idUser) }
            paymentUiStateUiState = when (result) {
                is NetworkResult.Success -> PaymentUiState.Success(result.data)
                is NetworkResult.Error -> PaymentUiState.Error(
                    result.message ?: "Une erreur inconnue est survenue."
                )
                NetworkResult.Loading -> PaymentUiState.Loading
                else -> {
                    PaymentUiState.Error("Une erreur inconnue est survenue.")
                }
            }
        }
    }

    fun updateUser(userInfo: User) {
        user = userInfo
    }

    fun getSafeInformationUser() {
        user = GetSampleData.getUser(2)
        paymentUiStateUiState = PaymentUiState.Success(user = user!!)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                val sessionManager : SessionManager = application.container.provideSessionManager(application.applicationContext)
                PaymentViewModel(ecommerceRepository = ecommerceRepository,
                    sessionManager = sessionManager)
            }
        }
    }
}