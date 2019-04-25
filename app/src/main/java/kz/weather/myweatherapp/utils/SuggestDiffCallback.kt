package kz.weather.myweatherapp.utils

import androidx.recyclerview.widget.DiffUtil
import kz.weather.myweatherapp.models.Result

class SuggestDiffCallback(val oldResults: List<Result>, val newResults: List<Result>): DiffUtil.Callback() {


    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        val oldResult: Result = oldResults.get(oldPos)
        val newResult: Result = newResults.get(newPos)
        return oldResult.geoid!!.equals(newResult.geoid!!)
    }

    override fun getOldListSize(): Int {
        return oldResults.size
    }

    override fun getNewListSize(): Int {
        return newResults.size
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        val oldResult: Result = oldResults.get(oldPos)
        val newResult: Result = newResults.get(newPos)
        return  oldResult.title!!.equals(newResult.title!!)
    }

}