package com.krunal.uos.Vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.krunal.uos.Repo.VMRepo
import com.krunal.uos.model.GalleryResponse
import com.krunal.uos.model.BloodResponse
import com.krunal.uos.model.EventModelResponse
import com.krunal.uos.model.SearchUserModel
import com.krunal.uos.model.UosDataModel
import com.krunal.uos.model.UserResponse
import com.krunal.uos.model.requestModelResponse
import retrofit2.Call
import retrofit2.http.FieldMap

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val vmRepo: com.krunal.uos.Repo.VMRepo = com.krunal.uos.Repo.VMRepo.getInstance()

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

    fun getEvent():LiveData<EventModelResponse>{
        return vmRepo.getEvent()
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
