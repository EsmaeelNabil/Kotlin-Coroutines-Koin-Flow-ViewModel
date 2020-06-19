package com.esmaeel.anim.MainExample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.esmaeel.anim.Koin.Network.Contract
import com.esmaeel.anim.Koin.Network.Status
import com.esmaeel.anim.MainExample.Models.CenterResponse
import com.esmaeel.anim.MainExample.Models.ProfileResponse
import com.esmaeel.anim.Utils.MyUtils
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
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
                    message = MyUtils.getExceptionErrorString(e) ?: "-"
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
                    message = MyUtils.getExceptionErrorString(e) ?: "-"
                )
            )
        }
    }


    var centers: MutableLiveData<Contract<CenterResponse>> = MutableLiveData()

    var centersFlow: MutableLiveData<Contract<CenterResponse?>> = MutableLiveData()

    fun getSalonsFlow(pageNumber: Int, randomKey: Int) {
        viewModelScope.launch {
            val salonsFlow = presenter.getSalonsFlow(pageNumber, randomKey).flowOn(Dispatchers.IO)
            salonsFlow.collect { value: Contract<CenterResponse?> ->
                centersFlow.value = value
            }
        }
    }

    fun getSalons(pageNumber: Int, randomKey: Int) {
        presenter.getSalons(pageNumber, randomKey)
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

