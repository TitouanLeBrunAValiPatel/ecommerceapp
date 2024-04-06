package fr.titouan.ecommerceapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    @SerialName("id") val idOrder : Int,
    @SerialName("user") val user: User,
    val date: String = "12 december",
    val products: List<ProductInOrder>? = null,
    @SerialName("total") val totalPrice: Float? = 0f
) {
    val safeTotalPrice: Float
        get() = totalPrice ?: 300f
}
