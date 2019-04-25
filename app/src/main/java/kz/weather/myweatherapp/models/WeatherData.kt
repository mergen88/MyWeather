package kz.weather.myweatherapp.models

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity
data class WeatherData(
        @PrimaryKey() var wdid: String,
        @ColumnInfo(name = "pos") var pos: String?,
        @ColumnInfo(name = "weather") @SerializedName("weather") var weather: List<Weather>?,
        @ColumnInfo(name = "main") @SerializedName("main") var main: Main?,
        @ColumnInfo(name = "clouds") @SerializedName("clouds") var clouds: Clouds?
){
    constructor():this(UUID.randomUUID().toString(),"",null,null,null)
}


@Entity
data class Weather(
        @PrimaryKey var wid: String,
        @SerializedName("description") var description: String?,
        @SerializedName("icon") var icon: String?
){
    constructor():this(UUID.randomUUID().toString(),"","")
}

@Entity
data class Main(
        @PrimaryKey var mid: String,
        @SerializedName("temp") var temp: Float?,
        @SerializedName("humidity") var humidity: String?
){
    constructor():this(UUID.randomUUID().toString(),0f,"")
}

@Entity
data class Clouds(
        @PrimaryKey var cid: String,
        @SerializedName("all") var all: Long
){
    constructor():this(UUID.randomUUID().toString(),0)
}
