package net.kibotu.android.recyclerviewpresenter

import androidx.core.math.MathUtils.clamp
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlin.math.absoluteValue

class CircularDataSource<T>(private val data: List<PresenterModel<T>>) : PageKeyedDataSource<Int, PresenterModel<T>>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PresenterModel<T>>) {

        val list = data.take(params.requestedLoadSize)

        log { "[loadInitial] data=${data.size} result=${list.size} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}" }

        callback.onResult(list, 0, list.size)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterModel<T>>) {

        val toIndex = clamp(params.key + params.requestedLoadSize, 0, data.size)
        val list = data.subList(params.key, toIndex).toMutableList()
        var nextPage = params.key + list.size

        // append missing items to list of we're at the end
        val difference = (params.key + params.requestedLoadSize) - toIndex
        if (difference > 0) { // todo add loop if data.size is small
            list.addAll(data.subList(0, difference))
            nextPage = difference + 1
        }

        log { "[loadAfter] result=${list.size} from=${params.key} toIndex=$toIndex requestedLoadSize=${params.requestedLoadSize} difference=$difference nextPage=$nextPage" }

        callback.onResult(list, nextPage)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterModel<T>>) {

        val key = params.key.absoluteValue

        val toIndex = clamp(key + params.requestedLoadSize, 0, data.size)
        val list = data.asReversed().subList(key, toIndex).toMutableList()
        var nextPage = key + list.size

        // append missing items to list of we're at the end
        val difference = (key + params.requestedLoadSize) - toIndex
        if (difference > 0) { // todo add loop if data.size is small
            list.addAll(data.asReversed().subList(0, difference))
            nextPage = difference + 1
        }

        log { "[loadBefore] result=${list.size} from=$key toIndex=$toIndex requestedLoadSize=${params.requestedLoadSize} difference=$difference nextPage=$nextPage" }

        callback.onResult(list.asReversed(), -nextPage)
    }

    class Factory<T>(var data: List<PresenterModel<T>>) : DataSource.Factory<Int, PresenterModel<T>>() {

        private val dataSource by lazy { MutableLiveData<CircularDataSource<T>>() }

        override fun create() = CircularDataSource(data).also {
            dataSource.postValue(it)
        }

        fun invalidate() {
            dataSource.value?.invalidate()
        }
    }
}