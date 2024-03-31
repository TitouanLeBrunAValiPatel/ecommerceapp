package fr.titouan.ecommerceapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    @SerialName("name") val name: String
)
