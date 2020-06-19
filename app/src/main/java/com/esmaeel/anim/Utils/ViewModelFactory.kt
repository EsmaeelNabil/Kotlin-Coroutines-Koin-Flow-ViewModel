package com.esmaeel.anim.Utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esmaeel.anim.MainExample.GlobalPresenter
import com.esmaeel.anim.MainExample.GlobalViewModel

class ViewModelFactory(private val presenter: GlobalPresenter) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GlobalViewModel::class.java)) {
            return GlobalViewModel(presenter) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
