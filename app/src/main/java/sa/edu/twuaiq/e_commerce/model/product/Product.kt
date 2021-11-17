package sa.edu.twuaiq.e_commerce.model.product


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String
)