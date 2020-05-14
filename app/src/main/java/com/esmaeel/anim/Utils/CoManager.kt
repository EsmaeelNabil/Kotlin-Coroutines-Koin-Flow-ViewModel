package com.esmaeel.pr

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object CoManager {

    fun onMainThread(givenFunction: suspend (() -> Unit)) {
        CoroutineScope(Dispatchers.Main).launch {
            givenFunction()
        }
    }

    fun onIOThread(givenFunction: suspend (() -> Unit)) {
        CoroutineScope(Dispatchers.IO).launch {
            givenFunction()
        }
    }

}
