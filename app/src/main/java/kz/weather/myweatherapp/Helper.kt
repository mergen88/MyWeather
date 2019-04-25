package kz.weather.myweatherapp

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.weather.myweatherapp.application.App
import kz.weather.myweatherapp.database.DBHelper
import kz.weather.myweatherapp.models.Result
import kz.weather.myweatherapp.models.Suggest
import kz.weather.myweatherapp.models.WeatherData
import kz.weather.myweatherapp.source.DataSource
import kz.weather.myweatherapp.utils.DisposableManager
import org.koin.core.KoinComponent
import org.koin.core.inject

class Helper:KoinComponent {

    private val dbHelper: DBHelper by inject()
    private var contract: ViewContract<List<Result>>? = null
    private var subscriptionSuggest: Disposable? = null
    private var subscriptionWeather: Disposable? = null

    fun getSuggest(query: String){

        subscriptionSuggest = DataSource.getSuggestObservable(query)
                .subscribeOn(Schedulers.io())
                .map {
                    t: Suggest ->
                        var results = ArrayList<Result>()
                        t.results.forEach { res: Result? ->
                            if (res!!.isKz()) {
                                Log.i("AAA", "MAP RES " + res.toString())
                                results.add(res)
                            }
                        }

                    dbHelper.insertSuggests(results)
                    results
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    suggests ->
                        contract?.onLoadComplete(suggests)
                }, {
                    error ->
                        contract?.onError(error.message)

                })
        DisposableManager.add(subscriptionSuggest!!)
    }

    fun getWeather(pos: String, weatherContract: ViewContract<WeatherData>) {
        subscriptionWeather = DataSource.getWeatherObservable(pos)
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    t: WeatherData? ->

                    weatherContract.onLoadComplete(t)
                },{
                    error ->
                    weatherContract.onError(error.message)
                })
        DisposableManager.add(subscriptionWeather!!)
    }

    fun subscribeContract(contract: ViewContract<List<Result>>){
        this.contract = contract
    }

    fun unsubscribeContract(){
        if(contract!=null){
            contract = null
        }
    }

    fun dispose(){
        Log.i("BBB","DISPOSE")
        DisposableManager.dispose()
    }





}