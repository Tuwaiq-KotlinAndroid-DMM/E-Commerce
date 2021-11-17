package sa.edu.twuaiq.e_commerce.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.edu.twuaiq.e_commerce.model.product.Product
import sa.edu.twuaiq.e_commerce.repositories.ApiServiceRepository
import java.lang.Exception

private const val TAG = "ProductsViewModel"
class ProductsViewModel : ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val productsLiveDate = MutableLiveData<List<Product>>()

    fun callProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getProducts()

                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, this.toString())
                        productsLiveDate.postValue(products)
                    }
                } else {
                    Log.d(TAG,response.message())
                }

            } catch (e: Exception)
            {
                Log.d(TAG,e.message.toString())
            }
        }
    }
}