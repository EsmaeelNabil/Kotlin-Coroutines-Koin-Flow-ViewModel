package com.esmaeel.anim.Koin

import android.content.Context
import com.esmaeel.anim.Base.Constants
import com.esmaeel.anim.BuildConfig
import com.esmaeel.anim.MainExample.GlobalPresenter
import com.esmaeel.anim.MainExample.GlobalViewModel
import com.esmaeel.anim.R
import com.esmaeel.anim.Utils.Loader
import com.esmaeel.anim.Utils.MyUtils
import com.esmaeel.anim.Utils.PrefUtils
import com.esmaeel.pr.di.Modules.WebService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val appModule = module {
    /*Single : creates one instance*/
    single { ActivityProvider(get()) }


    single { Loader() }
    factory { GlobalPresenter() }
    single { GlobalViewModel(get()) }

    /*Factory : creates a different instance every time*/
}

val networkModule = module {

    single(named(Constants.LOCALE_INTERCEPTOR)) { LocalInterceptor(get()) }
    single(named(Constants.AUTH_INTERCEPTOR)) { AuthInterceptor(get()) }
    single(named(Constants.ERROR_INTERCEPTOR)) { CustomErrorInterceptor(get()) }
    single { LoggingInterceptor() }
    single { getGson() }

    single(named(Constants.AUTH_CLIENT)) {
        HttpAuthClient(
            get(),
            get(named(Constants.AUTH_INTERCEPTOR)),
            get(named(Constants.ERROR_INTERCEPTOR))
        )
    }

    single(named(Constants.LOCALE_CLIENT)) {
        HttpLocaleClient(
            get(),
            get(named(Constants.LOCALE_INTERCEPTOR)),
            get(named(Constants.ERROR_INTERCEPTOR))
        )
    }

    single(named(Constants.LOCALE_SERVICE)) {
        getLocalApiService(
            get(
                named(
                    Constants.LOCALE_CLIENT
                )
            )
        )
    }
    single(named(Constants.AUTH_SERVICE)) {
        getAuthApiService(
            get(
                named(
                    Constants.AUTH_CLIENT
                )
            )
        )
    }

}

/*use in both local and token services*/
open class CustomErrorInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // get the response and check it for response code
        val response = chain.proceed(request)
        when (response.code) {
            200, 429 -> {
                if (MyUtils.isFakeResponse(response))
                    return MyUtils.getCustomResponse(
                        request,
                        context.getString(R.string.html_error),
                        402
                    )
                else
                    return response
            }
            404, 503, 500 -> { /*show Error Page*/
                println("Error code ${response.code}")
                return response
            }
            401 -> {/* do Auth Thing*/
                println("Error code 401")
                return response
            }
        }
        return response
    }
}

/*use in token service only*/
class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newReq = chain.request().newBuilder()
            .header("timezone", TimeZone.getDefault().id)
            .header("locale", MyUtils.getLocalLanguage(context))
            .header("Authorization", PrefUtils.getUserToken(context))
            .method(original.method, original.body)
            .build()

        return chain.proceed(newReq)
    }
}

class LocalInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(
            chain.request().newBuilder()
                .header("timezone", TimeZone.getDefault().id)
                .header("locale", MyUtils.getLocalLanguage(context))
                .method(original.method, original.body)
                .build()
        )
    }
}

fun HttpAuthClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authInterceptor: AuthInterceptor,
    customErrorInterceptor: CustomErrorInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .followRedirects(false)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(customErrorInterceptor)
        .readTimeout(Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .connectTimeout(Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()
}

fun HttpLocaleClient(
    loggingInterceptor: HttpLoggingInterceptor,
    localInterceptor: LocalInterceptor,
    customErrorInterceptor: CustomErrorInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .followRedirects(false)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(localInterceptor)
        .addInterceptor(customErrorInterceptor)
        .readTimeout(Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .connectTimeout(Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()
}


fun LoggingInterceptor(): HttpLoggingInterceptor {
    return if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
}


fun getGson(): Gson {
    return GsonBuilder().setLenient().create()
}


fun getLocalApiService(client: OkHttpClient): WebService {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
        .create(WebService::class.java)
}

fun getAuthApiService(client: OkHttpClient): WebService {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
        .create(WebService::class.java)
}
