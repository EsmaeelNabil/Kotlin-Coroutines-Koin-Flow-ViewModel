package com.esmaeel.anim.Base

data class ViewContract<out T /* is the out class type whatever the type*/>(
    val data: T? /*same*/
) {
    companion object {
        fun <T> onClick(data: T): ViewContract<T> =
            ViewContract(
                data = data
            )
    }
}
