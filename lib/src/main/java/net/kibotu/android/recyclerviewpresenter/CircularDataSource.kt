package net.kibotu.android.recyclerviewpresenter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import net.kibotu.android.recyclerviewpresenter.cirkle.circular
import java.util.*

class CircularDataSource<T>(private val data: List<PresenterViewModel<T>>, val generateUuid: Boolean = true) : PageKeyedDataSource<Int, PresenterViewModel<T>>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PresenterViewModel<T>>) {

        val fromIndex = 0
        val toIndex = (fromIndex + params.requestedLoadSize - 1)
        val previousKey = fromIndex - 1
        val nextKey = toIndex + 1

        val list = data.circular().subList(fromIndex, toIndex + 1)

        setNewUuid(list, fromIndex, toIndex)

        log { "[loadInitial] data=${data.size} result=${list.size} from=$fromIndex toIndex=$toIndex previousKey=$previousKey nextKey=$nextKey requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled} result=${list.map { it.model }}" }

        callback.onResult(list, previousKey, nextKey)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterViewModel<T>>) {

        val fromIndex = params.key
        val toIndex = (fromIndex + params.requestedLoadSize - 1)
        val nextKey = toIndex + 1

        val list = data.circular().subList(fromIndex, toIndex + 1)

        setNewUuid(list, fromIndex, toIndex)

        log { "[loadAfter] result=${list.size} from=$fromIndex toIndex=$toIndex nextKey=$nextKey requestedLoadSize=${params.requestedLoadSize} result=${list.map { it.model }}" }

        callback.onResult(list, nextKey)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterViewModel<T>>) {

        val fromIndex = params.key
        val toIndex = (fromIndex - params.requestedLoadSize - 1)
        val previousKey = fromIndex - 1

        val list = data.circular().subList(fromIndex, toIndex + 1).reversed()

        setNewUuid(list, fromIndex, toIndex)

        log { "[loadBefore] result=${list.size} from=$fromIndex toIndex=$toIndex previousKey=$previousKey requestedLoadSize=${params.requestedLoadSize} result=${list.map { it.model }}" }

        callback.onResult(list, previousKey)
    }

    private fun setNewUuid(list: List<PresenterViewModel<T>>, fromIndex: Int, toIndex: Int) {
        if (!generateUuid)
            return

        list.forEachIndexed { index, item ->
            item.uuid = UUID.randomUUID().toString() // (fromIndex - toIndex + index).toString()
        }
    }

    class Factory<T>(var data: List<PresenterViewModel<T>>, val generateUuid: Boolean = true) : DataSource.Factory<Int, PresenterViewModel<T>>() {

        private val dataSource by lazy { MutableLiveData<CircularDataSource<T>>() }

        override fun create() = CircularDataSource(data, generateUuid).also {
            dataSource.postValue(it)
        }

        fun invalidate() {
            dataSource.value?.invalidate()
        }
    }
}