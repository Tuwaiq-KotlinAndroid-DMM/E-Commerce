package sa.edu.twuaiq.e_commerce.model.indentity


import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("image")
    val image: String
)