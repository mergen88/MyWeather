package kz.weather.myweatherapp

import kz.weather.myweatherapp.models.Result
import java.lang.Exception

interface ViewContract<T>{


    fun onLoadComplete(data: T?)
    fun onError(message: String?)
}