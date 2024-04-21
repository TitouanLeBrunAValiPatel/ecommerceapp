package fr.titouan.ecommerceapp.ui.screens.account.views

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
import fr.titouan.ecommerceapp.model.User
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginFormState
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginUiState
import fr.titouan.ecommerceapp.ui.screens.login.views.LoginViewModel
import kotlinx.coroutines.launch

sealed interface AccountUiState {
    data class Success(val user: User) : AccountUiState
    data class Error(val message : String) : AccountUiState
    data object Loading : AccountUiState
    data object Nothing : AccountUiState
}
data class AccountFormState(
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

class AccountViewModel(
    private val ecommerceRepository: EcommerceRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    var accountUiState: AccountUiState by mutableStateOf(AccountUiState.Nothing)
        private set

    var accountFormState: AccountFormState by mutableStateOf(AccountFormState())
        private set

    var user : User? by mutableStateOf(sessionManager.getUser())

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            accountUiState = AccountUiState.Loading
            val result = sessionManager.getUser()?.let { ecommerceRepository.getUser(it.idUser) }
            when (result) {
                is NetworkResult.Success -> {
                    accountUiState = AccountUiState.Success(result.data)
                    user = result.data
                    sessionManager.saveUser(user!!)
                }
                is NetworkResult.Error -> {
                    accountUiState =
                        AccountUiState.Error(result.message ?: "Une erreur inconnue est survenue.")

                    sessionManager.saveUser(GetSampleData.getUser(2))
                }
                NetworkResult.Loading -> accountUiState = AccountUiState.Loading
                else -> {
                    AccountUiState.Error("Impossible to recuperate the user.")
                    sessionManager.saveUser(GetSampleData.getUser(5))
                    user = sessionManager.getUser()
                }
            }

        }
    }
    fun setInformations(user: User) {
        viewModelScope.launch {
            accountUiState = AccountUiState.Loading
            val result = ecommerceRepository.updateInformationUser(user)
            when (result) {
                is NetworkResult.Success -> {
                    accountUiState = AccountUiState.Success(result.data)
                    sessionManager.saveUser(GetSampleData.getUser(2))
                }
                is NetworkResult.Error -> {
                    accountUiState =
                        AccountUiState.Error(result.message ?: "Une erreur inconnue est survenue.")
                    sessionManager.saveUser(GetSampleData.getUser(2))
                }
                NetworkResult.Loading -> accountUiState = AccountUiState.Loading
            }

        }
    }

    fun setPassword(newPassword : String) {

        user?.password = newPassword
        viewModelScope.launch {
            accountUiState = AccountUiState.Loading
            val result = user?.let { ecommerceRepository.updateInformationUser(it) }
            when (result) {
                is NetworkResult.Success -> {
                    accountUiState = AccountUiState.Success(result.data)
                    sessionManager.saveUser(GetSampleData.getUser(2))
                }
                is NetworkResult.Error -> {
                    accountUiState =
                        AccountUiState.Error(result.message ?: "Une erreur inconnue est survenue.")
                    sessionManager.saveUser(GetSampleData.getUser(2))
                }
                NetworkResult.Loading -> accountUiState = AccountUiState.Loading
                else -> {AccountUiState.Error("Impossible to recuperate the user.")}
            }

        }

    }

    fun oldPasswordIsValid(oldPassword: String) : Boolean {
        return sessionManager.getUser()?.password == oldPassword
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EcommerceApplication)
                val ecommerceRepository : EcommerceRepository = application.container.ecommerceRepository
                val sessionManager : SessionManager = application.container.provideSessionManager(application.applicationContext)
                AccountViewModel(ecommerceRepository = ecommerceRepository,
                    sessionManager = sessionManager)
            }
        }
    }
}