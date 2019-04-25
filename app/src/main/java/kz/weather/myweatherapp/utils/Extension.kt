package kz.weather.myweatherapp.utils

import android.content.SharedPreferences
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*


val CUR_TIME = "current_time"
val CUR_REQUEST = "current_request"

private object Koin : KoinComponent {
    val shPref: SharedPreferences by inject()
}

fun String.save(): String {
    if(!isSaved() || isOneHourPassed()) {
        Koin.shPref.edit().putString(CUR_REQUEST, this).commit()
        saveCurrentTime()
    }
    return this
}

fun String.isSaved(): Boolean {
    if(isOneHourPassed()){
        return false
    }
    return getCurrentRequest().equals(this)
}
fun getCurrentRequest() : String{
    return Koin.shPref.getString(CUR_REQUEST, "")
}

fun saveCurrentTime(){
    Koin.shPref.edit().putLong(CUR_TIME, Date().time).commit()
}

fun isOneHourPassed(): Boolean{
    val cal = Calendar.getInstance()
    cal.time = Date(Koin.shPref.getLong(CUR_TIME, Date().time))
    cal.add(Calendar.HOUR_OF_DAY, 1)
    return cal.time.before(Date())
}