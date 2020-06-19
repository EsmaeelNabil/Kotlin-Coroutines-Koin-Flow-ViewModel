package com.esmaeel.anim.MainExample

import com.esmaeel.anim.Base.Constants
import com.esmaeel.anim.Koin.Network.Contract
import com.esmaeel.anim.MainExample.Models.CenterResponse
import com.esmaeel.anim.MainExample.Models.ProfileResponse
import com.esmaeel.anim.Utils.MyUtils
import com.esmaeel.pr.di.Modules.WebService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import retrofit2.Response

public class GlobalPresenter() : KoinComponent /* meaning look in modules for the proprties*/ {

    val mServiceLocale: WebService by inject(named(Constants.LOCALE_SERVICE)) /* in network module*/
    val mServiceAuth: WebService by inject(named(Constants.AUTH_SERVICE)) /* in network module*/

    suspend fun getCountries() = mServiceLocale.getCountries()

    suspend fun getUserDetailsFlow() = flow {
        emit(mServiceAuth.getUserDetailsFlow())
    }

    suspend fun getUserDetails(): Response<ProfileResponse> = mServiceAuth.getUserDetails()

    fun getSalons(pageNumber: Int, randomKey: Int): Single<CenterResponse> =
        mServiceAuth.getFilteredCentersRx(page = pageNumber, random_order_key = randomKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getSalonsFlow(pageNumber: Int, randomKey: Int) = flow {
        emit(Contract.onLoading(data = null))
        try {
            val response =
                mServiceAuth.getFlowCenters(page = pageNumber, random_order_key = randomKey)
            if (response.isSuccessful) {
                emit(Contract.onSuccess(data = response.body()))
            } else {
                response.errorBody()?.let { errorBody ->
                    emit(
                        Contract.onError(
                            data = null,
                            message = MyUtils.getError(errorBody)
                        )
                    )
                }
            }
        } catch (a: Exception) {
            emit(
                Contract.onError(
                    data = null,
                    message = MyUtils.getError(a)
                )
            )
        }

    }

}
