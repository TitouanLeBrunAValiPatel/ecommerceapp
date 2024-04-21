package fr.titouan.ecommerceapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val idUser : Int,
    @SerialName("name") var name: String? = "",
    @SerialName("mail") val mail: String? = "",
    @SerialName("password") var password: String? = "",
    @SerialName("surname") val surname: String? = "",
    @SerialName("gender") val gender: String? = "",
    @SerialName("city") var city: String? = "",
    @SerialName("postCode") var postCode: String? = ""
) {

    val safeSurname: String
        get() = surname ?: ""

    val safeName: String
        get() = name ?: ""

    val safeMail: String
        get() = mail ?: ""

    val safePassword: String
        get() = password ?: ""
    val safeGender: String
        get() = gender ?: ""
    val safeCity: String
        get() = city ?: ""
    val safePostCode: String
        get() = postCode ?: ""
}

@Serializable
data class Credentials(
    val email: String,
    val password: String
)

data class GoogleToken(val token: String)
@Serializable
data class AuthToken(val token: String)

