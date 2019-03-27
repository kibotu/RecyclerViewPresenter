package net.kibotu.android.recyclerviewpresenter.app.pagination

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.exozet.android.core.extensions.TAG
import net.kibotu.android.recyclerviewpresenter.RecyclerViewModel
import net.kibotu.android.recyclerviewpresenter.app.misc.FakeDataGenerator

class SimpleItemKeyedDataSource : ItemKeyedDataSource<String, RecyclerViewModel<String>>() {

    private val data = mutableListOf<RecyclerViewModel<String>>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RecyclerViewModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedInitialKey=${params.requestedInitialKey} data=${data.size} requestedLoadSize=${params.requestedLoadSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (0 until params.requestedLoadSize).map {
            data.add(RecyclerViewModel(FakeDataGenerator.createRandomImageUrl()))
        }

        callback.onResult(data)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RecyclerViewModel<String>>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize} data=${data.size}")

        (0 until params.requestedLoadSize).map {
            data.add(RecyclerViewModel(FakeDataGenerator.createRandomImageUrl()))
        }

        callback.onResult(data)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RecyclerViewModel<String>>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    override fun getKey(item: RecyclerViewModel<String>): String = item.uuid

    class Factory : DataSource.Factory<String, RecyclerViewModel<String>>() {

        lateinit var dataSource: SimpleItemKeyedDataSource

        override fun create(): SimpleItemKeyedDataSource {
            dataSource = SimpleItemKeyedDataSource()
            return dataSource
        }
    }
}