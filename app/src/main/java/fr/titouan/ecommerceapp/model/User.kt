package fr.titouan.ecommerceapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val idUser : Int,
    @SerialName("name") var name: String,
    @SerialName("mail") val mail: String,
    @SerialName("password") var password: String? = null,
    @SerialName("surname") val surname: String? = null,
    @SerialName("gender") val gender: String,
    @SerialName("city") var city: String? = null,
    @SerialName("postCode") var postCode: String? = null
) {

    val safeSurname: String
        get() = surname ?: "Titouan"
}

@Serializable
data class Credentials(
    val email: String,
    val password: String
)

data class GoogleToken(val token: String)
@Serializable
data class AuthToken(val token: String)

