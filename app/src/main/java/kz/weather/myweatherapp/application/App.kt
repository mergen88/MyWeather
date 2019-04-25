package kz.weather.myweatherapp.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import kz.weather.myweatherapp.Helper
import kz.weather.myweatherapp.database.DBHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App: Application() {


    val module = module{
        single { Helper() }
        single { DBHelper(androidContext()) }
        single { getSharedPreferences(packageName, Context.MODE_PRIVATE) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(module)
        }
    }
}