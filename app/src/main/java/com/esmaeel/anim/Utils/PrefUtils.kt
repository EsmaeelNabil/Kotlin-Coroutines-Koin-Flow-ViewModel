package com.esmaeel.anim.Utils

import android.content.Context
import com.esmaeel.anim.Base.Constants

object PrefUtils {
    @JvmStatic
    fun getUserToken(context: Context): String {
        return Constants.TOKEN
    }
}
