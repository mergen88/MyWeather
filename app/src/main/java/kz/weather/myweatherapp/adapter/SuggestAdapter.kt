package kz.weather.myweatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.disposables.Disposable
import kz.weather.myweatherapp.Helper
import kz.weather.myweatherapp.R
import kz.weather.myweatherapp.ViewContract
import kz.weather.myweatherapp.application.App
import kz.weather.myweatherapp.models.Result
import kz.weather.myweatherapp.models.WeatherData
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.ArrayList

class SuggestAdapter(context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    var suggests: ArrayList<Result> = ArrayList<Result>()
    var context: Context? = null

    init {
        this.context = context
    }
    fun addItems(items: List<Result>){
        this.suggests = ArrayList(items)

    }

    fun clearList(){
        this.suggests.clear()
        this.notifyDataSetChanged()
    }

    fun getData() :List<Result>{
        return this.suggests
    }

    override fun getItemCount(): Int {
        return suggests.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(suggests.get(position))
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.suggest_item, viewGroup, false), context!!)
    }

}
class ViewHolder(itemView: View, val context: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), KoinComponent {

    @BindView(R.id.sugTitle)
    lateinit var sugTitle: TextView

    @BindView(R.id.sugDescription)
    lateinit var sugDescription: TextView

    @BindView(R.id.weatherInfo)
    lateinit var weatherInfo: TextView

    @BindView(R.id.weatherIcon)
    lateinit var weatherIcon: ImageView

    @BindView(R.id.weatherTemp)
    lateinit var weatherTemp: TextView

    @BindView(R.id.weatherProgress)
    lateinit var weatherProgress: ProgressBar

    val helper: Helper by inject()

    private var subscription: Disposable? = null

    init {
        super.itemView
        ButterKnife.bind(this, itemView)
    }

    fun bind(suggest: Result){
        showWeather(false)
        sugTitle.text = suggest.title
        sugDescription.text = suggest.subtitle

        loadWeather(suggest.pos!!)
    }
    fun loadWeather(pos: String){
          helper.getWeather(pos, object: ViewContract<WeatherData>{
              override fun onLoadComplete(weatherData: WeatherData?) {
                  weatherData?.let {
                      val clouds = it.clouds
                      val main = it.main
                      val weather = it.weather
                      if (clouds != null && main != null && weather != null) {
                          val temp = main.temp!!
                          weatherInfo.text = context.getString(R.string.weatherInfo).format(clouds.all, main.humidity)
                          weatherTemp.text = if (temp > 0) "+".plus(temp) else "".plus(temp)
                          weatherIcon.setImageResource(context.resources.getIdentifier("ic_" + weather!![0].icon, "drawable", context.applicationContext.packageName))
                          showWeather(true)
                      }
                  }
              }

              override fun onError(message: String?) {
              }
          })

    }
    fun showWeather(show: Boolean){
        weatherProgress.visibility = if (show) View.GONE else View.VISIBLE
        weatherTemp.visibility = if (show) View.VISIBLE else View.GONE
        weatherIcon.visibility = if (show) View.VISIBLE else View.GONE
        weatherInfo.visibility = if (show) View.VISIBLE else View.GONE
    }

}