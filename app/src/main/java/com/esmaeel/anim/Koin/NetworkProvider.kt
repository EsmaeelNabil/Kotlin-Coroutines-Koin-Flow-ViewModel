package com.esmaeel.anim.Koin

import android.app.Activity
import android.content.Context
import com.esmaeel.pr.di.Modules.WebService
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit

open class NetworkProvider : KoinComponent {


//    val retrofit: Retrofit by inject()
//    val localClient: OkHttpClient by inject()
//
//
//    fun getLocalService(activity: Activity): WebService? {
//        return retrofit(getHttpLocalClient(activity))!!.create(WebService::class.java).also { apiService = it }
//    }
//
//    fun getLocalService(retrofit: Retrofit?): WebService? {
//        return retrofit?.create(WebService::class.java)
//    }
//
//    fun getTokenService(authContext: Context?): WebService? {
//        return getRetrofit(
//            getHttpAuthClient(authContext)
//        )!!.create(
//            WebService::class.java
//        ).also { apiService = it }
//    }


}