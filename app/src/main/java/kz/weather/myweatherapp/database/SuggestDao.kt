package kz.weather.myweatherapp.database

import androidx.room.*
import kz.weather.myweatherapp.models.Result

@Dao
interface SuggestDao {

    @Insert
    fun insertAll(sugests: List<Result>)

    @Delete
    fun delete(suggest: Result)

    @Query("SELECT * FROM suggest")
    fun getAllSuggest(): List<Result>

    @Query("DELETE FROM suggest")
    fun clearSuggest()

    @Query("SELECT count(*) FROM suggest")
    fun count(): Int
}