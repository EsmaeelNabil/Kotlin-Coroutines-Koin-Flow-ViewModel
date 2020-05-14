package com.esmaeel.pr.di.Modules

import com.esmaeel.anim.MainExample.Models.CountriesResponse
import com.esmaeel.anim.MainExample.Models.ProfileResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

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
}
