package kz.weather.myweatherapp.ui

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import io.reactivex.disposables.Disposable
import kz.weather.myweatherapp.*
import kz.weather.myweatherapp.adapter.SuggestAdapter
import kz.weather.myweatherapp.models.Result
import kz.weather.myweatherapp.ui.base.BaseActivity
import kz.weather.myweatherapp.utils.SuggestDiffCallback
import kz.weather.myweatherapp.utils.getCurrentRequest
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity(), ViewContract<List<Result>> {


    override fun getContentView(): Int {
        return R.layout.activity_main
    }
    @BindView(R.id.suggestList)
    lateinit var suggestList: androidx.recyclerview.widget.RecyclerView
    @BindView(R.id.citySearchView)
    lateinit var citySearchView: SearchView
    lateinit var searchDisposable: Disposable
    lateinit var suggestAdapter: SuggestAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.suggestAdapter = SuggestAdapter(applicationContext)
        suggestList.adapter = suggestAdapter
        suggestList.layoutManager = LinearLayoutManager(this)
        citySearchView.setQuery(getCurrentRequest(), false)
        searchDisposable = citySearchView.queryTextChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter {
                    t ->
                        if (t.length >= 2) {
                            true
                        } else {
                            helper.getSuggest("")

                            false
                        }
                }
                .map { t -> t.toString() }
                .subscribe { query -> helper.getSuggest(query)
                }
    }

    override fun onLoadComplete(suggests: List<Result>?) {
        suggests?.let {
            val suggestDiffCallback = SuggestDiffCallback(suggestAdapter.getData(), it)
            val suggestDiffResult: DiffUtil.DiffResult  = DiffUtil.calculateDiff(suggestDiffCallback, true)
            suggestAdapter.addItems(it)
            suggestDiffResult.dispatchUpdatesTo(suggestAdapter)
        }


    }

    override fun onError(message: String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        helper.subscribeContract(this)



    }
    override fun onStop() {
        super.onStop()
        helper.unsubscribeContract()

    }

    override fun onDestroy() {
        super.onDestroy()
        helper.dispose()

        if(!searchDisposable.isDisposed){
            searchDisposable.dispose()
        }
    }

}
