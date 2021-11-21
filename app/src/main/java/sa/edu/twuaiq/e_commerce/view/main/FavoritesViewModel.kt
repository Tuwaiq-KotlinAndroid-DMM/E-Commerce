package sa.edu.twuaiq.e_commerce.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.edu.twuaiq.e_commerce.model.product.Product
import sa.edu.twuaiq.e_commerce.repositories.ApiServiceRepository

private const val TAG = "FavoritesViewModel"
class FavoritesViewModel: ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val favoritesLiveData = MutableLiveData<List<Product>>()
    val favoritesErrorLiveData = MutableLiveData<String>()

    fun callFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getFavoriteProducts()

                if (response.isSuccessful) {
                    response.body()?.run {
                        favoritesLiveData.postValue(this)
                        Log.d(TAG,this.toString())
                    }
                } else {
                    Log.d(TAG,response.message())
                    favoritesErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                favoritesErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}