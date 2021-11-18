package sa.edu.twuaiq.e_commerce.view.identity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.edu.twuaiq.e_commerce.model.indentity.UserInfoModel
import sa.edu.twuaiq.e_commerce.repositories.ApiServiceRepository
import java.io.File

private const val TAG = "ProfileViewModel"
class ProfileViewModel: ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val profileLiveData = MutableLiveData<UserInfoModel>()
    val uploadImageLiveData = MutableLiveData<String>()

    val profileErrorsLiveData = MutableLiveData<String>()

    fun callUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.getUserInfo()

                if (response.isSuccessful) {
                    response.body()?.run {
                        profileLiveData.postValue(this)
                        Log.d(TAG,this.toString())
                    }
                } else {
                    Log.d(TAG,response.message())
                    profileErrorsLiveData.postValue(response.message().toString())
                }

            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                profileErrorsLiveData.postValue(e.message.toString())
            }
        }
    }

    fun uploadUserImage(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.uploadUserImage(file)

                if (response.isSuccessful) {
                    uploadImageLiveData.postValue("successful")
                } else {
                    Log.d(TAG,response.message())
                    profileErrorsLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                profileErrorsLiveData.postValue(e.message.toString())
            }
        }
    }
}