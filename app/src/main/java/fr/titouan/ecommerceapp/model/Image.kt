package fr.titouan.ecommerceapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: Int,
    val image: String?,
    val idColor : Int) {

    val safeImage : String
        get() = image ?: "https://www.atmosphera.com/fstrz/r/s/www.atmosphera.com/fr/phototheque/atmosphera.com/65000/medium/01W064824A.jpg?frz-v=1548"

}