package com.esmaeel.anim.MainExample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.esmaeel.anim.Koin.Network.Contract
import com.esmaeel.anim.Utils.MyUtils
import kotlinx.coroutines.Dispatchers

class GlobalViewModel(private val presenter: GlobalPresenter /* injected using koin by viewModel()*/) :
    ViewModel() {

    fun getCountries() = liveData(Dispatchers.IO) {
        emit(Contract.onLoading(data = null))
        try {
            /*try to get a success response and pass it to the contract*/
            emit(Contract.onSuccess(data = presenter.getCountries()))
        } catch (e: Exception) {
            /* Request Fails and here is the Error*/
            emit(
                Contract.onError(
                    data = null,
                    message = MyUtils.getExceptionErrorString(e) ?: "UnKnownError"
                )
            )
        }
    }

    fun getUserDetails() = liveData(Dispatchers.IO) {
        emit(Contract.onLoading(data = null))
        try {
            //*try to get a success response and pass it to the contract*//
            emit(Contract.onSuccess(data = presenter.getUserDetails()))
        } catch (e: Exception) {
            //* Request Fails and here is the Error*//
            emit(
                Contract.onError(
                    data = null,
                    message = MyUtils.getExceptionErrorString(e) ?: "UnKnownError"
                )
            )
        }
    }


}
