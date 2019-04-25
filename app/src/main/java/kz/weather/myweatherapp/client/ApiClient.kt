package kz.weather.myweatherapp.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        val SUGGEST_URL = "http://suggest-maps.yandex.ru/suggest-geo?lang=ru-RU&search_type=tune&v=9&ll=67.118185%2C48.813320&spn=0.5%2C0.5&n=10&part=";
        val WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?appid=5ad4d1fc0b880d0238b54966788fab89&units=metric&"
    }

    inline fun <reified T> getData(url: String, typeToken: Type) :  T {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        val client: OkHttpClient = builder.build();
        val response = client.newCall(Request.Builder().get().url(url).build()).execute()
        var json = ""
        if (response.code()==200) {
            json = response.body()!!.string()
        }
        return GsonBuilder().create().fromJson<T>(json, typeToken)
    }
}


