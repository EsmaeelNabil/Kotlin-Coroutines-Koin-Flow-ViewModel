package com.esmaeel.anim.MainExample

import com.esmaeel.anim.ApiManagerDefault.Constants
import com.esmaeel.pr.di.Modules.WebService
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import retrofit2.Response

public class GlobalPresenter() : KoinComponent /* meaning look in modules for the proprties*/ {

    val mServiceLocale: WebService by inject(named(Constants.LOCALE_SERVICE)) /* in network module*/
    val mServiceAuth: WebService by inject(named(Constants.AUTH_SERVICE)) /* in network module*/

    suspend fun getCountries() = mServiceLocale.getCountries()

    suspend fun getUserDetailsFlow(): Flow<Response<ProfileResponse>> =
        mServiceAuth.getUserDetailsFlow()

    suspend fun getUserDetails(): Response<ProfileResponse> = mServiceAuth.getUserDetails()

}
