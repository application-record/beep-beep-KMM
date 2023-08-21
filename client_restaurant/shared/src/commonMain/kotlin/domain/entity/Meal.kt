package domain.entity

data class Meal(
    val id: String,
    val restaurantId: String,
    val name: String,
    val description: String,
    val price: Double,
)
