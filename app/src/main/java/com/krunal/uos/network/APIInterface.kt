package com.krunal.uos.network

import com.krunal.uos.model.GalleryResponse
import com.krunal.uos.model.BloodResponse
import com.krunal.uos.model.EventModelResponse
import com.krunal.uos.model.SearchUserModel
import com.krunal.uos.model.UosDataModel
import com.krunal.uos.model.UserResponse
import com.krunal.uos.model.requestModelResponse
import com.uos.bloodbank.utils.Constant
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIInterface {


    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_REGISTER)
    fun getRegister(@FieldMap registerMap: Map<String, String>): Call<UserResponse>


    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_LOGIN)
    fun getLogin(@FieldMap registerMap: Map<String, String>): Call<UserResponse>

    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_SEARCH)
    fun getSearch(@FieldMap registerMap: Map<String, String>): Call<SearchUserModel>


    @POST(Constant.WEB_SERVICES.WS_ALL_REQUEST)
    fun getAllRequest():Call<requestModelResponse>
    // Define other API endpoints here as needed

    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_BLOOD_REQUEST_DONOR)
    fun sendRequestBlood(@FieldMap requestMap:Map<String,String>):Call<BloodResponse>

    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_UPDATE_USER)
    fun updateUser(@FieldMap registerMap: Map<String, String>): Call<UserResponse>

    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_CALL_DATA)
    fun callInsert(@FieldMap callMap:Map<String,String>) : Call<BloodResponse>

    @POST(Constant.WEB_SERVICES.WS_GALLERY)
    fun getGallery():Call<GalleryResponse>

    @POST(Constant.WEB_SERVICES.WS_EVENT)
    fun getEvent():Call<EventModelResponse>


    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_DELETE)
    fun deleteUser(@FieldMap deleteMap:Map<String,String>) : Call<BloodResponse>

    @FormUrlEncoded
    @POST(Constant.WEB_SERVICES.WS_FORGET_PASS)
    fun forgetPassword(@FieldMap forgetMap:Map<String,String>):Call<BloodResponse>


    @POST(Constant.WEB_SERVICES.WS_DATA_CHANGE)
    fun getDataChange(): Call<UosDataModel>

}
