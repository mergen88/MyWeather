package kz.weather.myweatherapp.database

import androidx.room.*
import io.reactivex.Single
import kz.weather.myweatherapp.models.Clouds
import kz.weather.myweatherapp.models.Main
import kz.weather.myweatherapp.models.Weather
import kz.weather.myweatherapp.models.WeatherData

@Dao
interface WeatherDao {


    @Insert
    fun insertWeatherData(sugests: WeatherData)

    @Delete
    fun delete(suggest: WeatherData)

    @Query("SELECT * FROM weatherdata WHERE pos=:pos")
    fun getWeatherData(pos: String): WeatherData?

    @Query("DELETE FROM weatherdata")
    fun clearWeatherData()

    @Query("SELECT count(*) FROM weatherdata")
    fun count(): Int

}