package com.esmaeel.anim.Base

sealed class ViewsEvents {
    data class onItemClicked(val position: Int, val data: Any) : ViewsEvents()
    data class onItemDeleted(val position: Int, val data: Any) : ViewsEvents()
}