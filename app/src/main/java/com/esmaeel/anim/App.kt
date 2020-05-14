package com.esmaeel.anim

//import com.esmaeel.anim.Koin.viewModelModule
import android.app.Application
import com.esmaeel.anim.Koin.NetworkClasses.networkModule
import com.esmaeel.anim.Koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App.applicationContext) /* the context */
            modules(
                listOf(
                    appModule,
                    networkModule
                )
            ) /*the val inside KoinModules that has all of our modules*/
        }
    }
}