package kz.weather.myweatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.weather.myweatherapp.models.*

@Database(entities = arrayOf(Result::class, Weather::class, Main::class, Clouds::class, WeatherData::class), version = 1)

@TypeConverters(Converters::class)
abstract class AppDB: RoomDatabase() {

    abstract val getSuggestDao: SuggestDao
    abstract val getWeatherDao: WeatherDao
}