package com.example.uos.Vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.uos.Repo.VMRepo
import com.example.uos.model.GalleryResponse
import com.example.uos.model.BloodResponse
import com.example.uos.model.SearchUserModel
import com.example.uos.model.UosDataModel
import com.example.uos.model.UserResponse
import com.example.uos.model.requestModelResponse
import retrofit2.Call
import retrofit2.http.FieldMap

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val vmRepo: VMRepo = VMRepo.getInstance()

    fun userRegister(registerMap: HashMap<String, String>): LiveData<UserResponse> {
        return vmRepo.userRegister(registerMap)
    }

    fun getLogin(registerMap: Map<String, String>): LiveData<UserResponse>{
        return vmRepo.getLogin(registerMap)
    }

    fun getSearch(registerMap: Map<String, String>): LiveData<SearchUserModel>
    {
        return vmRepo.getSearch(registerMap)
    }

    fun getAllRequest():LiveData<requestModelResponse>
    {
        return vmRepo.getAllRequest();
    }

    fun sendRequestBlood(requestMap:Map<String,String>):LiveData<BloodResponse>
    {
        return vmRepo.sendRequestBlood(requestMap)
    }

    fun updateUser(registerMap: HashMap<String, String>): LiveData<UserResponse> {
        return vmRepo.updateUser(registerMap)
    }

    fun callInsert(callMap:Map<String,String>) : LiveData<BloodResponse>{
        return vmRepo.callInsert(callMap)
    }


    fun getGallery():LiveData<GalleryResponse>
    {
        return vmRepo.getGallery();
    }


    fun deleteUser(@FieldMap deleteMap:Map<String,String>) : LiveData<BloodResponse>{
        return vmRepo.deleteUser(deleteMap)
    }

    fun forgetPassword(forgetMap:Map<String,String>):LiveData<BloodResponse>{
        return vmRepo.forgetPassword(forgetMap)

    }

    fun getDataChange(): LiveData<UosDataModel>{
        return vmRepo.getDataChange();
    }


}
