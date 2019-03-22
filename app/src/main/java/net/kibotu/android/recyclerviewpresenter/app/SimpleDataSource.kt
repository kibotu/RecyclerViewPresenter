package net.kibotu.android.recyclerviewpresenter.app

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource

class SimpleDataSource : PageKeyedDataSource<Int, String>() {

    private val TAG = SimpleDataSource::class.java.simpleName

    private val data = mutableListOf<String>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, String>) {
        Log.v(TAG, "[loadInitial] requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}")

        (0 until params.requestedLoadSize).map {
            val uri = FakeDataGenerator.createRandomImageUrl()
            data.add(uri)
        }

        callback.onResult(data, 0, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")

        (0 until params.requestedLoadSize).map {
            val uri = FakeDataGenerator.createRandomImageUrl()
            data.add(uri)
        }

        callback.onResult(data, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    class Factory : DataSource.Factory<Int, String>() {
        override fun create() = SimpleDataSource()
    }
}