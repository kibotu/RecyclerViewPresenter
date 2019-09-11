package net.kibotu.android.recyclerviewpresenter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import net.kibotu.android.recyclerviewpresenter.cirkle.circular

class CircularDataSource<T>(private val data: List<PresenterModel<T>>) : PageKeyedDataSource<Int, PresenterModel<T>>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PresenterModel<T>>) {

        val fromIndex = 0
        val toIndex = (fromIndex + params.requestedLoadSize)

        val list = data.circular().subList(fromIndex, toIndex)

        log { "[loadInitial] data=${data.size} result=${list.size} from=$fromIndex toIndex=$toIndex requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled} previous=0 adjacentPageKey=${list.size}" }

        callback.onResult(list, 0, list.size + 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterModel<T>>) {

        val fromIndex = params.key
        val toIndex = (fromIndex + params.requestedLoadSize)

        val list = data.circular().subList(fromIndex, toIndex)

        log { "[loadAfter] result=${list.size} from=$fromIndex toIndex=$toIndex requestedLoadSize=${params.requestedLoadSize}" }

        callback.onResult(list, list.size + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterModel<T>>) {

        val fromIndex = params.key
        val toIndex = (fromIndex - params.requestedLoadSize)

        val list = data.circular().subList(fromIndex, toIndex)

        log { "[loadBefore] result=${list.size} from=$fromIndex toIndex=$toIndex requestedLoadSize=${params.requestedLoadSize}" }

        callback.onResult(list.asReversed(), fromIndex - 1)
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