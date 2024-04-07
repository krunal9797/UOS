package com.example.uos.Repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uos.model.GalleryResponse
import com.example.uos.model.BloodResponse
import com.example.uos.model.EventModelResponse
import com.example.uos.model.SearchUserModel
import com.example.uos.model.UosDataModel
import com.example.uos.model.UserResponse
import com.example.uos.model.requestModelResponse
import com.example.uos.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.FieldMap

class VMRepo private constructor() {
    companion object {
        private var vmRepo: VMRepo? = null

        @Synchronized
        fun getInstance(): VMRepo {
            if (vmRepo == null) {
                vmRepo = VMRepo()
            }
            return vmRepo!!
        }
    }

    fun userRegister(registerMap: HashMap<String, String>): LiveData<UserResponse> {
        val responseModelMutableLiveData = MutableLiveData<UserResponse>()

        ApiClient.getAPIService().getRegister(registerMap).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                call.cancel()
            }
        })

        return responseModelMutableLiveData

    }

    fun getLogin(registerMap: Map<String, String>): LiveData<UserResponse> {
        val responseModelMutableLiveData = MutableLiveData<UserResponse>()

        ApiClient.getAPIService().getLogin(registerMap).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                call.cancel()
            }

        })
        return responseModelMutableLiveData
    }

    fun getSearch(registerMap: Map<String, String>): LiveData<SearchUserModel> {

        val responseModelMutableLiveData = MutableLiveData<SearchUserModel>()

        ApiClient.getAPIService().getSearch(registerMap)
            .enqueue(object : Callback<SearchUserModel> {
                override fun onResponse(
                    call: Call<SearchUserModel>,
                    response: Response<SearchUserModel>
                ) {
                    if (response.isSuccessful) {
                        Log.e("TAG123456789", "onResponse: " + response.message())
                        responseModelMutableLiveData.postValue(response.body())
                    } else {
                        responseModelMutableLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<SearchUserModel>, t: Throwable) {
                    call.cancel()
                }

            })
        return responseModelMutableLiveData
    }


    fun getAllRequest(): LiveData<requestModelResponse> {
        val responseModelMutableLiveData = MutableLiveData<requestModelResponse>()
        ApiClient.getAPIService().getAllRequest().enqueue(object : Callback<requestModelResponse> {
            override fun onResponse(
                call: Call<requestModelResponse>,
                response: Response<requestModelResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<requestModelResponse>, t: Throwable) {
                call.cancel()
            }

        })
        return responseModelMutableLiveData
    }


    fun sendRequestBlood(requestMap: Map<String, String>): LiveData<BloodResponse> {
        val responseModelMutableLiveData = MutableLiveData<BloodResponse>()
        ApiClient.getAPIService().sendRequestBlood(requestMap)
            .enqueue(object : Callback<BloodResponse> {
                override fun onResponse(
                    call: Call<BloodResponse>,
                    response: Response<BloodResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("TAG123456789", "onResponse: " + response.message())
                        responseModelMutableLiveData.postValue(response.body())
                    } else {
                        responseModelMutableLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<BloodResponse>, t: Throwable) {
                    call.cancel()
                }

            })
        return responseModelMutableLiveData
    }

    fun updateUser(registerMap: HashMap<String, String>): LiveData<UserResponse> {
        val responseModelMutableLiveData = MutableLiveData<UserResponse>()

        ApiClient.getAPIService().updateUser(registerMap).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                call.cancel()
            }
        })

        return responseModelMutableLiveData

    }

    fun callInsert(callMap: Map<String, String>): LiveData<BloodResponse> {
        val responseModelMutableLiveData = MutableLiveData<BloodResponse>()

        ApiClient.getAPIService().callInsert(callMap).enqueue(object : Callback<BloodResponse> {
            override fun onResponse(
                call: Call<BloodResponse>,
                response: Response<BloodResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<BloodResponse>, t: Throwable) {
                call.cancel()
            }

        })
        return responseModelMutableLiveData

    }

    fun getGallery(): LiveData<GalleryResponse> {
        val responseModelMutableLiveData = MutableLiveData<GalleryResponse>()
        ApiClient.getAPIService().getGallery().enqueue(object : Callback<GalleryResponse> {
            override fun onResponse(
                call: Call<GalleryResponse>,
                response: Response<GalleryResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GalleryResponse>, t: Throwable) {
                call.cancel()
            }

        })
        return responseModelMutableLiveData
    }

    fun getEvent():LiveData<EventModelResponse>{
        val responseModelMutableLiveData = MutableLiveData<EventModelResponse>()
        ApiClient.getAPIService().getEvent().enqueue(object : Callback<EventModelResponse> {
            override fun onResponse(
                call: Call<EventModelResponse>,
                response: Response<EventModelResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<EventModelResponse>, t: Throwable) {
                call.cancel()
            }

        })
        return responseModelMutableLiveData
    }


    fun deleteUser(deleteMap: Map<String, String>): LiveData<BloodResponse> {
        val responseModelMutableLiveData = MutableLiveData<BloodResponse>()

        ApiClient.getAPIService().deleteUser(deleteMap).enqueue(object : Callback<BloodResponse> {
            override fun onResponse(
                call: Call<BloodResponse>,
                response: Response<BloodResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<BloodResponse>, t: Throwable) {
                call.cancel()
            }

        })
        return responseModelMutableLiveData

    }


    fun forgetPassword(forgetMap: Map<String, String>): LiveData<BloodResponse> {
        val responseModelMutableLiveData = MutableLiveData<BloodResponse>()
        ApiClient.getAPIService().forgetPassword(forgetMap)
            .enqueue(object : Callback<BloodResponse> {
                override fun onResponse(
                    call: Call<BloodResponse>,
                    response: Response<BloodResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("TAG123456789", "onResponse: " + response.message())
                        responseModelMutableLiveData.postValue(response.body())
                    } else {
                        responseModelMutableLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<BloodResponse>, t: Throwable) {
                    call.cancel()
                }

            })
        return responseModelMutableLiveData
    }

    fun getDataChange(): LiveData<UosDataModel>{
        val responseModelMutableLiveData = MutableLiveData<UosDataModel>()

        ApiClient.getAPIService().getDataChange().enqueue(object :Callback<UosDataModel>{
            override fun onResponse(call: Call<UosDataModel>, response: Response<UosDataModel>) {
                if (response.isSuccessful) {
                    Log.e("TAG123456789", "onResponse: " + response.message())
                    responseModelMutableLiveData.postValue(response.body())
                } else {
                    responseModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UosDataModel>, t: Throwable) {
                call.cancel()
            }

        })
        return responseModelMutableLiveData
    }

    // Define other repository functions here as needed
}
