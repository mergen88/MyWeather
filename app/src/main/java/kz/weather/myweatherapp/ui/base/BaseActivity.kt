package kz.weather.myweatherapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import butterknife.ButterKnife
import kz.weather.myweatherapp.Helper
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    protected val helper: Helper by inject()
    protected abstract fun getContentView(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        ButterKnife.bind(this)
    }
}
