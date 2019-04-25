package kz.weather.myweatherapp.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable



class DisposableManager {

    companion object {

        private var compositeDisposable: CompositeDisposable? = null

        fun add(disposable: Disposable) {
            getCompositeDisposable().add(disposable)
        }

        fun dispose() {
            getCompositeDisposable().dispose()
        }

        private fun getCompositeDisposable(): CompositeDisposable {
            if (compositeDisposable == null || compositeDisposable!!.isDisposed) {
                compositeDisposable = CompositeDisposable()
            }
            return compositeDisposable!!
        }

    }

}