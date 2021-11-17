package sa.edu.twuaiq.e_commerce.repositories

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sa.edu.twuaiq.e_commerce.api.CommerceApi
import java.lang.Exception

const val SHARED_PREF_FILE = "Auth"
const val TOKEN_KEY = "token"
private const val BASE_URL = "http://18.196.156.64"
class ApiServiceRepository(val context: Context) {

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitApi = retrofitService.create(CommerceApi::class.java)

    private val sharedPref = context.getSharedPreferences(SHARED_PREF_FILE , Context.MODE_PRIVATE)

    private val accessToken = sharedPref.getString(TOKEN_KEY,"")

    suspend fun getProducts() =
        retrofitApi.getProducts("Bearer $accessToken")

    companion object {
        private var instance: ApiServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiServiceRepository(context)
        }

        fun get() : ApiServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }
    }
}