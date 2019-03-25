package net.kibotu.android.recyclerviewpresenter.app

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.exozet.android.core.extensions.TAG

class SimplePositionalDataSource : PositionalDataSource<ViewModel<String>>() {

    private val data = mutableListOf<ViewModel<String>>()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ViewModel<String>>) {
        Log.v(TAG, "[loadRange] startPosition=${params.startPosition} data=${data.size} loadSize=${params.loadSize}")

        val dif = data.size - params.startPosition

        val list = (dif until params.loadSize).map {
            ViewModel(FakeDataGenerator.createRandomImageUrl())
        }.toList()

        data.addAll(list)

        Log.v(TAG, "[loadRange] dif=$dif list=${list.size} data=${data.size}")

        callback.onResult(data.subList(params.startPosition, params.startPosition + params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<ViewModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedStartPosition=${params.requestedStartPosition} data=${data.size} pageSize=${params.pageSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (params.requestedStartPosition until params.requestedLoadSize).map {
            val uri = FakeDataGenerator.createRandomImageUrl()
            data.add(ViewModel(uri))
        }

        callback.onResult(data, 0, data.size)
    }

    class Factory : DataSource.Factory<Int, ViewModel<String>>() {

        lateinit var dataSource: SimplePositionalDataSource

        override fun create(): SimplePositionalDataSource {
            dataSource = SimplePositionalDataSource()
            return dataSource
        }
    }
}