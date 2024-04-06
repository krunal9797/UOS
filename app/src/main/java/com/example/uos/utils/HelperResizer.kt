package com.example.mycartooncharacter.utils


import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

object HelperResizer {
    var height = 0
    var width = 0
    val SCALE_WIDTH = 1080 // scale width of ui
    val SCALE_HEIGHT = 1920 // scale height of ui

    fun getHeightAndWidth(context: Context) {
        getHeight(context)
        getWidth(context)
    }

    fun getWidth(context: Context): Int {
        width = context.resources.displayMetrics.widthPixels
        return width
    }

    fun getHeight(context: Context): Int {
        height = context.resources.displayMetrics.heightPixels
        return height
    }

    fun setHeight(mContext: Context, view: View, v_height: Int) {
        val dm = mContext.resources.displayMetrics
        val height = dm.heightPixels * v_height / SCALE_HEIGHT
        view.layoutParams.height = height
    }

    fun setWidth(context: Context, view: View, v_Width: Int) {
        val dm = context.resources.displayMetrics
        val width = dm.widthPixels * v_Width / SCALE_WIDTH
        view.layoutParams.width = width
    }

    fun setHeight(h: Int): Int {
        return (height * h) / 1920
    }

    fun setWidth(w: Int): Int {
        return (width * w) / 1080
    }

    fun setSize(view: View, width: Int, height: Int) {
        view.layoutParams.height = setHeight(height)
        view.layoutParams.width = setWidth(width)
    }

    fun setHeightByWidth(mContext: Context, view: View, v_height: Int) {
        val dm = mContext.resources.displayMetrics
        val height = dm.widthPixels * v_height / SCALE_WIDTH
        view.layoutParams.height = height
    }

    fun setSize(view: View, width: Int, height: Int, sameHeightAndWidth: Boolean) {
        if (sameHeightAndWidth) {
            view.layoutParams.height = setWidth(height)
            view.layoutParams.width = setWidth(width)
        } else {
            view.layoutParams.height = setHeight(height)
            view.layoutParams.width = setHeight(width)
        }
    }

    fun setMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.setMargins(setWidth(left), setHeight(top), setWidth(right), setHeight(bottom))
    }

    fun setPadding(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        view.setPadding(left, top, right, bottom)
    }

    fun FS(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun FS2(mActivity: Activity) {
        mActivity.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    fun isOnline(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
