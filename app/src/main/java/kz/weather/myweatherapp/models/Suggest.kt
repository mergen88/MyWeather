package kz.weather.myweatherapp.models

import androidx.room.*
import com.google.gson.annotations.SerializedName


data class Suggest(@SerializedName("results") val results: ArrayList<Result>)

@Entity(tableName = "suggest")
data class Result(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        @ColumnInfo(name = "type") @SerializedName("type") var type: String?,
        @ColumnInfo(name = "title") @SerializedName("title") var title: String?,
        @ColumnInfo(name = "subtitle") @SerializedName("subtitle") var subtitle: String?,
        @ColumnInfo(name = "size") @SerializedName("size") var size: Double?,
        @ColumnInfo(name = "geoid") @SerializedName("geoid") var geoid: Int?,
        @ColumnInfo(name = "pos") @SerializedName("pos") var pos: String?
) {


    fun isKz(): Boolean{
        return (subtitle!!.contains("Kazakhstan") || subtitle!!.contains("Казахстан"))
    }


}