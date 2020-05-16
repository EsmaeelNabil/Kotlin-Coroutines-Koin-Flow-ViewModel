package com.esmaeel.anim.MainExample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.esmaeel.anim.Koin.Network.Contract
import com.esmaeel.anim.Koin.Network.Status
import com.esmaeel.anim.MainExample.Models.ProfileResponse
import com.esmaeel.anim.Utils.MyUtils
import com.esmaeel.anim.MainExample.Models.CenterResponse
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import retrofit2.Response

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

    var details: MutableLiveData<Contract<Response<ProfileResponse>>> = MutableLiveData()
    private var userJop: Job? = null

    fun getUserDetailsFlow() {
        userJop?.let {
            userJop?.cancel()
        }

        val scope = MainScope()

//        userJop = viewModelScope.launch() {
//            presenter.getUserDetailsFlow().onEach {
//                it.collect{
//
//                }
//            }.launchIn(scope)
//        }
    }
//        details.postValue(Contract.onLoading(data = null))
//        try {
//            //*try to get a success response and pass it to the contract*//
//            details.postValue(
//                Contract.onSuccess(data = presenter.getUserDetailsFlow()
//                    .collect(object : FlowCollector<Response<ProfileResponse>> {
//                        override suspend fun emit(value: Response<ProfileResponse>) {
//                            emit(value)
//                        }
//                    })
//                )
//            )
//        } catch (e: Exception) {
//            //* Request Fails and here is the Error*//
//            emit(
//                Contract.onError(
//                    data = null,
//                    message = MyUtils.getExceptionErrorString(e) ?: "UnKnownError"
//                )
//            )
//        }

    var centers: MutableLiveData<Contract<CenterResponse>> = MutableLiveData()

    fun getSalons(pageNumber: Int, randomKey: Int) {
        presenter.getSalons(pageNumber,randomKey)
            .subscribe(object : SingleObserver<CenterResponse> {
                override fun onSuccess(t: CenterResponse) {
                    centers.postValue(Contract(status = Status.SUCCESS, data = t, message = null))
                }

                override fun onSubscribe(d: Disposable) {
                    centers.postValue(
                        Contract(
                            status = Status.LOADING,
                            data = null,
                            message = null
                        )
                    )
                    bag.add(d)
                }

                override fun onError(e: Throwable) {
                    centers.postValue(
                        Contract(
                            status = Status.ERROR,
                            data = null,
                            message = MyUtils.getExceptionErrorString(e)
                        )
                    )
                }

            })
    }

    val bag = CompositeDisposable()

    override fun onCleared() {
        bag?.clear()
        super.onCleared()
    }

}

