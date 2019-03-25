package net.kibotu.android.recyclerviewpresenter.app

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.exozet.android.core.extensions.TAG

class SimplePageKeyedDataSource : PageKeyedDataSource<Int, ViewModel<String>>() {

    private val data = mutableListOf<ViewModel<String>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ViewModel<String>>) {
        Log.v(TAG, "[loadInitial] requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}")

        (0 until params.requestedLoadSize).map {
            data.add(ViewModel(FakeDataGenerator.createRandomImageUrl()))
        }

        callback.onResult(data, null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ViewModel<String>>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize} data=${data.size}")

        val list = (0 until params.requestedLoadSize).map {
            ViewModel(FakeDataGenerator.createRandomImageUrl())
        }

        Log.v(TAG, "[loadRange] list=${list.size} data=${data.size}")

        data.addAll(list)

        callback.onResult(data, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ViewModel<String>>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    class Factory : DataSource.Factory<Int, ViewModel<String>>() {

        val dataSource by lazy { MutableLiveData<SimplePageKeyedDataSource>() }

        override fun create(): SimplePageKeyedDataSource {
            val source = SimplePageKeyedDataSource()
            dataSource.postValue(source)
            return source
        }
    }
}