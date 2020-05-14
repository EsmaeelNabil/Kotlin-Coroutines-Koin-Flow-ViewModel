package com.esmaeel.anim.MainExample

import androidx.lifecycle.*
import com.esmaeel.anim.MyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class GlobalViewModel(private val presenter: GlobalPresenter /* injected using koin by viewModel()*/) :
    ViewModel() {

    fun getCountries() = liveData(Dispatchers.IO) {
        emit(Contract.onLoading(data = null))
        /* hit the spot.*/
        try {
            /*try to get a success response and pass it to the contract*/
            emit(Contract.onSuccess(data = presenter.getCountries()))
        } catch (e: Exception) {
            /* Request Fails and here is the Error*/
            // TODO: 5/11/20 Figure something with the Response Error
            emit(
                Contract.onError(
                    data = null,
                    message = MyUtils.getExceptionErrorString(e) ?: "UnKnownError"
                )
            )
        }
    }

    var profileResponse: LiveData<Contract<Response<ProfileResponse>?>> = MutableLiveData()

//    fun getUserProfile() {
//        profileResponse.postValue(Contract.onLoading(data = null))
//        //* hit the spot.*//
//        try {
//            //*try to get a success response and pass it to the contract*//
//            viewModelScope.launch {
//                profileResponse.e(Contract.onSuccess(data = presenter.getUserDetailsFlow().asLiveData()))
//            }
//        } catch (e: Exception) {
//            //* Request Fails and here is the Error*//
//            // TODO: 5/11/20 Figure something with the Response Error
//            profileResponse.postValue(
//                Contract.onError(
//                    data = null,
//                    message = MyUtils.getExceptionErrorString(e) ?: "UnKnownError"
//                )
//            )
//        }
//    }

    fun getUserDetails() = liveData(Dispatchers.IO) {
        emit(Contract.onLoading(data = null))
        //* hit the spot.*//
        try {
            //*try to get a success response and pass it to the contract*//
            emit(Contract.onSuccess(data = presenter.getUserDetails()))
        } catch (e: Exception) {
            //* Request Fails and here is the Error*//
            // TODO: 5/11/20 Figure something with the Response Error
            emit(
                Contract.onError(
                    data = null,
                    message = MyUtils.getExceptionErrorString(e) ?: "UnKnownError"
                )
            )
        }
    }


}
