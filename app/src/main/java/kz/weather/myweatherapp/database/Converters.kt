package kz.weather.myweatherapp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.weather.myweatherapp.models.Clouds
import kz.weather.myweatherapp.models.Main
import kz.weather.myweatherapp.models.Weather

class Converters {

    @TypeConverter
    fun weatherFromJson(json: String): List<Weather>{
        return Gson().fromJson<List<Weather>>(json, object: TypeToken<List<Weather>>(){}.type)
    }

    @TypeConverter
    fun mainFromJson(json: String): Main{
        return Gson().fromJson<Main>(json, Main::class.java)
    }

    @TypeConverter
    fun cloudsFromJson(json: String): Clouds{
        return Gson().fromJson<Clouds>(json, Clouds::class.java)
    }

    @TypeConverter
    fun weatherToJsonString(weather: List<Weather>): String? {
        return Gson().toJson(weather)
    }

    @TypeConverter
    fun mainToJsonString(main: Main): String? {
        return Gson().toJson(main)
    }

    @TypeConverter
    fun cloudsToJsonString(clouds: Clouds): String? {
        return Gson().toJson(clouds)
    }
}