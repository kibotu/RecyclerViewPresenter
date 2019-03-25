package net.kibotu.android.recyclerviewpresenter.app

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.exozet.android.core.extensions.TAG

class SimpleItemKeyedDataSource : ItemKeyedDataSource<String, ViewModel<String>>() {

    private val data = mutableListOf<ViewModel<String>>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<ViewModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedInitialKey=${params.requestedInitialKey} data=${data.size} requestedLoadSize=${params.requestedLoadSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (0 until params.requestedLoadSize).map {
            data.add(ViewModel(FakeDataGenerator.createRandomImageUrl()))
        }

        callback.onResult(data)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ViewModel<String>>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize} data=${data.size}")

        (0 until params.requestedLoadSize).map {
            data.add(ViewModel(FakeDataGenerator.createRandomImageUrl()))
        }

        callback.onResult(data)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ViewModel<String>>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    override fun getKey(item: ViewModel<String>): String = item.uuid

    class Factory : DataSource.Factory<String, ViewModel<String>>() {

        lateinit var dataSource: SimpleItemKeyedDataSource

        override fun create(): SimpleItemKeyedDataSource {
            dataSource = SimpleItemKeyedDataSource()
            return dataSource
        }
    }
}