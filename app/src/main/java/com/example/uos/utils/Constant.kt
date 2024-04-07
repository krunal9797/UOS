package com.uos.bloodbank.utils

import android.content.Context
import android.net.ConnectivityManager


object Constant {
    const val REQUEST_STATUS_CODE_SUCCESS = 200
    const val REQUEST_STATUS_CODE_WRONG_CREDENTIALS = 400
    const val REQUEST_STATUS_CODE_AUTH_FAILED = 404
    const val REQUEST_STATUS_CODE_NO_RECORDS = 100
    const val REQUEST_STATUS_CODE_ERROR = -1
    const val REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE = -2

    const val INSTAGRAM = "https://www.instagram.com/unityofsouthgujarat/"
    const val FACEBOOK = "https://www.facebook.com/unityofsouthgujaratuos"
    const val GOOGLE = "https://unityofsouthgujarat.in"

    object WEB_SERVICES
    {
        const val WS_GET_STATE = "rest_api/get_state_list.php"
        const val WS_GET_DISTRICT_LIST = "rest_api/get_district_list.php"
        const val Transactions = "paytmallinone/init_Transaction.php"
        const val WS_REGISTER = "api/register.php"
        const val WS_LOGIN = "api/login.php"
        const val WS_SEARCH = "api/search.php"
        const val WS_FETCH_USER = "api/fetch_user.php"
        const val WS_ALL_REQUEST = "api/all_request.php"
        const val WS_BLOOD_REQUEST_DONOR = "api/request_donor.php"
        const val WS_UPDATE_USER = "api/update_user.php"
        const val WS_CALL_DATA = "api/call_details.php"
        const val WS_GALLERY = "api/gallery.php"
        const val WS_DELETE = "api/delete_user.php"
        const val WS_FORGET_PASS = "api/forgetpassword.php"
        const val WS_DATA_CHANGE = "api/get_uos_data.php"
        const val WS_ABOUT = "api/about.html"
        const val WS_PRIVACY_POLICY = "api/privacy_policy.html"
        const val WS_TERMS_CONDITIONS = "api/terms_condition.html"
    }


}
