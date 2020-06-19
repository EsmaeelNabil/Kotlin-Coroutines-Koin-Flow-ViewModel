package com.esmaeel.pr.di.Modules

import com.esmaeel.anim.MainExample.Models.CenterResponse
import com.esmaeel.anim.MainExample.Models.CountriesResponse
import com.esmaeel.anim.MainExample.Models.ProfileResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WebService {
    @Headers("Accept: application/json")
    @GET("common/countries")
    suspend fun getCountries(): Response<CountriesResponse>

    @Headers("Accept: application/json")
    @GET("user/profile")
    suspend fun getUserDetailsFlow(): Flow<Response<ProfileResponse>>

    @Headers("Accept: application/json")
    @GET("user/profile")
    suspend fun getUserDetails(): Response<ProfileResponse>

    @Headers("Accept: application/json")
    @GET("categories/list")
    fun getFilteredCentersRx(
        @Query("city_id") city_id: String = "0",
        @Query("category_id") category_id: Int = 0 ,
        @Query("page") page: Int = 1,
        @Query("random_order_key") random_order_key: Int = -1
    ): Single<CenterResponse>


    @Headers("Accept: application/json")
    @GET("categories/list")
    suspend fun getFlowCenters(
        @Query("city_id") city_id: String = "0",
        @Query("category_id") category_id: String = "0" ,
        @Query("page") page: Int = 1,
        @Query("random_order_key") random_order_key: Int = -1
    ): Response<CenterResponse>
}
