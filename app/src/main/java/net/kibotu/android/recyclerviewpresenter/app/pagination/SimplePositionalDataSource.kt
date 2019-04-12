package net.kibotu.android.recyclerviewpresenter.app.pagination

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.exozet.android.core.misc.createRandomImageUrl
import net.kibotu.android.recyclerviewpresenter.RecyclerViewModel
import net.kibotu.logger.TAG

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class SimplePositionalDataSource : PositionalDataSource<RecyclerViewModel<String>>() {

    private val data = mutableListOf<RecyclerViewModel<String>>()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<RecyclerViewModel<String>>) {
        Log.v(TAG, "[loadRange] startPosition=${params.startPosition} data=${data.size} loadSize=${params.loadSize}")

        val dif = data.size - params.startPosition

        val list = (dif until params.loadSize).map {
            RecyclerViewModel(createRandomImageUrl())
        }.toList()

        data.addAll(list)

        Log.v(TAG, "[loadRange] dif=$dif list=${list.size} data=${data.size}")

        callback.onResult(data.subList(params.startPosition, params.startPosition + params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<RecyclerViewModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedStartPosition=${params.requestedStartPosition} data=${data.size} pageSize=${params.pageSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (params.requestedStartPosition until params.requestedLoadSize).map {
            val uri = createRandomImageUrl()
            data.add(RecyclerViewModel(uri))
        }

        callback.onResult(data, 0, data.size)
    }

    class Factory : DataSource.Factory<Int, RecyclerViewModel<String>>() {

        lateinit var dataSource: SimplePositionalDataSource

        override fun create(): SimplePositionalDataSource {
            dataSource = SimplePositionalDataSource()
            return dataSource
        }
    }
}