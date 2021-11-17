package sa.edu.twuaiq.e_commerce.model.indentity


import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("rememberMe")
    val rememberMe: Boolean
)