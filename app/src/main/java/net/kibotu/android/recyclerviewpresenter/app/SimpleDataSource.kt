package net.kibotu.android.recyclerviewpresenter.app

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import java.util.*

class SimpleDataSource : PageKeyedDataSource<Int, SimpleDataSource.ViewModel<String>>() {

    private val TAG = SimpleDataSource::class.java.simpleName

    private val data = mutableListOf<SimpleDataSource.ViewModel<String>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ViewModel<String>>) {
        Log.v(TAG, "[loadInitial] requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}")

        (0 until params.requestedLoadSize).map {
            val uri = FakeDataGenerator.createRandomImageUrl()
            data.add(ViewModel(uri))
        }

        callback.onResult(data, 0, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ViewModel<String>>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")

        (0 until params.requestedLoadSize).map {
            val uri = FakeDataGenerator.createRandomImageUrl()
            data.add(ViewModel(uri))
        }

        callback.onResult(data, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ViewModel<String>>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    class Factory : DataSource.Factory<Int, SimpleDataSource.ViewModel<String>>() {
        override fun create() = SimpleDataSource()
    }

    data class ViewModel<T>(val t: T, val uuid: String = UUID.randomUUID().toString())
}