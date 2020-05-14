package com.esmaeel.anim.Utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.WindowManager
import com.esmaeel.anim.Base.ErrorModel
import com.esmaeel.anim.Base.ShowLoaderBus
import com.esmaeel.anim.Base.ShowLoaderBusWithTouch
import com.esmaeel.anim.R
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import java.util.*


object MyUtils {
    const val uKnownError = "UnKnown Error"

    @JvmStatic
    fun getGson(): Gson {
        return Gson()
    }

    @JvmStatic
    fun getError(errorBody: ResponseBody): String {
        errorBody.let {
            var error = getGson()
                .fromJson(errorBody?.string(), ErrorModel::class.java)
            return getErrorsString(errorList = error.msg)
        }
        return uKnownError
    }

    @JvmStatic
    fun getErrorsString(errorList: Collection<String>): String {
        var ErrorString = uKnownError

        if (errorList.isNullOrEmpty())
            return uKnownError

        for (s in errorList) {
            ErrorString = ErrorString + s + "\n"
        }
        return ErrorString
    }

    fun getExceptionErrorString(throwable: Throwable): String? {
        return if (throwable is HttpException) {
            throwable.response()?.errorBody()?.let {
                getError(
                    it
                )
            }
        } else {
            throwable.message
        }
    }

    @JvmStatic
    fun getLoader(context: Context): Dialog {
        val dialog = Dialog(context)
        val view = (context as Activity).layoutInflater.inflate(R.layout.loader_layout, null)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog
    }


    fun showLoader() {
        EventBus.getDefault().post(ShowLoaderBus(yes = true));
    }

    fun hideLoader() {
        EventBus.getDefault().post(ShowLoaderBus(yes = false));
        EventBus.getDefault().post(ShowLoaderBusWithTouch(yes = false));
    }

    fun showLoaderWithTouch() {
        EventBus.getDefault().post(ShowLoaderBusWithTouch(yes = true));
    }

    fun getLocalLanguage(context: Context): String {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
        return locale.getLanguage()
    }

    fun isFakeResponse(response: Response): Boolean {
        val header = response.headers["Content-Type"]
        return header == null || header.contains("html")
    }

    fun getCustomResponse(
        request: Request?,
        errorMessage: String,
        statusCode: Int?
    ): Response {

        /*creates a custom error response*/
        val message =
            """{"status": false,"msg": [$errorMessage]}""".toResponseBody("application/json".toMediaTypeOrNull())
        return Response.Builder().request(request!!)
            .protocol(Protocol.HTTP_1_1)
            .message(errorMessage)
            .body(message)
            .sentRequestAtMillis(-1L)
            .receivedResponseAtMillis(System.currentTimeMillis())
            .code(statusCode!!)
            .build()
    }


}
