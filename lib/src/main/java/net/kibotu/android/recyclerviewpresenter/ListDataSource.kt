package net.kibotu.android.recyclerviewpresenter

import androidx.core.math.MathUtils.clamp
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class ListDataSource<T>(private val data: List<PresenterModel<T>>) : PositionalDataSource<PresenterModel<T>>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PresenterModel<T>>) {
        // new start position is larger than list size
        if (params.startPosition >= data.size) {
            log { "[loadRange] " }
            return
        }

        val toIndex = clamp(params.startPosition + params.loadSize, 0, data.size)

        log { "[loadRange] from=${params.startPosition} to=$toIndex data=${data.size} loadSize=${params.loadSize}" }

        val list = data.subList(params.startPosition, toIndex)

        log { "[loadRange] list=${list.size} data=${data.size}" }

        callback.onResult(list)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<PresenterModel<T>>) {
        log { "[loadInitial] requestedStartPosition=${params.requestedStartPosition} data=${data.size} pageSize=${params.pageSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}" }

        val list = data.take(params.requestedLoadSize)

        callback.onResult(list, 0, list.size)
    }

    class Factory<T>(var data: List<PresenterModel<T>>) : DataSource.Factory<Int, PresenterModel<T>>() {

        private val dataSource by lazy { MutableLiveData<ListDataSource<T>>() }

        override fun create() = ListDataSource(data).also {
            dataSource.postValue(it)
        }

        fun invalidate() {
            dataSource.value?.invalidate()
        }
    }
}