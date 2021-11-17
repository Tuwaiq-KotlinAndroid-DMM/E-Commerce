package sa.edu.twuaiq.e_commerce.model.indentity


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("token")
    val token: String
)