package kz.weather.myweatherapp.source

import io.reactivex.Observable
import kz.weather.myweatherapp.application.App
import kz.weather.myweatherapp.client.ApiClient
import kz.weather.myweatherapp.database.DBHelper
import kz.weather.myweatherapp.models.Suggest
import kz.weather.myweatherapp.models.WeatherData
import kz.weather.myweatherapp.utils.isOneHourPassed
import kz.weather.myweatherapp.utils.isSaved
import kz.weather.myweatherapp.utils.save
import org.koin.core.KoinComponent
import org.koin.core.inject


class DataSource {


    companion object: KoinComponent {
        private val dbHelper: DBHelper by inject()
        fun getSuggestObservable(query: String): Observable<Suggest> {
            val url = ApiClient.SUGGEST_URL+query
            return Observable.create<Suggest> {
                subscriber ->
                try {
                    if(query.isSaved() && !dbHelper.suggestIsEmpty()){
                        subscriber.onNext(Suggest(ArrayList(dbHelper.getSuggests())))
                    } else {
                        dbHelper.clearWeather()
                        subscriber.onNext(ApiClient().getData(url, Suggest::class.java))
                        query.save()
                    }
                    subscriber.onComplete()
                } catch (e: Exception){
                    subscriber.onError(e)
                }
            }
        }
        fun getWeatherObservable(pos: String): Observable<WeatherData> {
            val position = pos.split(",")
            val lat = position[1].trim()
            val lon = position[0].trim()
            val url = ApiClient.WEATHER_URL.plus("lat=").plus(lat).plus("&lon=").plus(lon)
            return Observable.create<WeatherData> {
                subscriber ->
                try {
                    var weatherData: WeatherData? = dbHelper.getWeatherData(pos)
                    if(!isOneHourPassed() && weatherData != null){
                        subscriber.onNext(weatherData)
                    } else {
                        weatherData = ApiClient().getData(url, WeatherData::class.java) as WeatherData
                        weatherData.pos = pos
                        dbHelper.insertWeatherData(weatherData)
                        subscriber.onNext(weatherData)
                    }
                    subscriber.onComplete()
                } catch (e: Exception){
                    subscriber.onError(e)
                }
            }
        }



    }

}