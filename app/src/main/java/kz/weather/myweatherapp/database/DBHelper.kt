package kz.weather.myweatherapp.database

import android.content.Context
import androidx.room.Room
import kz.weather.myweatherapp.models.Result
import kz.weather.myweatherapp.models.WeatherData

class DBHelper(context: Context) {


    private var db: AppDB

    init {
        db = Room.databaseBuilder(context,
                AppDB::class.java, "suggestdb").build()
    }

    fun insertSuggests(sugests: List<Result>){
        db.getSuggestDao.clearSuggest()
        db.getSuggestDao.insertAll(sugests)
    }
    fun insertWeatherData(weatherData: WeatherData){
        db.getWeatherDao.insertWeatherData(weatherData)
    }

    fun getSuggests(): List<Result>{
        return db.getSuggestDao.getAllSuggest()
    }

    fun suggestIsEmpty() : Boolean{
        return db.getSuggestDao.count()==0
    }
    fun weatherIsEmpty() : Boolean{
        return db.getWeatherDao.count()==0
    }

    fun clearWeather(){
        db.getWeatherDao.clearWeatherData()
    }
    fun getWeatherData(pos: String): WeatherData? {
        val wd = db.getWeatherDao.getWeatherData(pos)

        return wd
    }

}