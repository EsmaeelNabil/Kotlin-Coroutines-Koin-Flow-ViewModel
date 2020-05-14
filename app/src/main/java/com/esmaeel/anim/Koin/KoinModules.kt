package com.esmaeel.anim.Koin

import com.esmaeel.anim.MainExample.GlobalPresenter
import com.esmaeel.anim.MainExample.GlobalViewModel
import org.koin.dsl.module

val appModule = module {
    /*Single : creates one instance*/
    single { ActivityProvider(get()) }

    factory { GlobalPresenter() }
    single { GlobalViewModel(get()) }

    /*Factory : creates a different instance every time*/
}

//val networkModule = module {
//    single { CustomErrorInterceptor(get()) }
//    single { getGson() }
//    single { AuthInterceptor(get()) }
//    single { LocalInterceptor(get()) }
//    single { LoggingInterceptor() }
//
//    single { HttpAuthClient(get(), get()) }
//    single { HttpLocaleClient(get(), get()) }
//    single { getRetrofit(get()) }
//
////    factory { provideOkHttpClient(get()) }
////    factory { provideForecastApi(get()) }
//
//}