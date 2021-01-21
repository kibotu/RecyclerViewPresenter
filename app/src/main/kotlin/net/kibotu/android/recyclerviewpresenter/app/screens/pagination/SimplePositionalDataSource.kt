package net.kibotu.android.recyclerviewpresenter.app.screens.pagination

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.createRandomImageUrl
import net.kibotu.logger.TAG

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class SimplePositionalDataSource : PositionalDataSource<PresenterViewModel<String>>() {

    private val data = mutableListOf<PresenterViewModel<String>>()

    @LayoutRes
    val layout = R.layout.photo_presenter_item

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PresenterViewModel<String>>) {
        Log.v(TAG, "[loadRange] startPosition=${params.startPosition} data=${data.size} loadSize=${params.loadSize}")

        val dif = data.size - params.startPosition

        val list = (dif until params.loadSize).map {
            PresenterViewModel(createRandomImageUrl(), layout)
        }.toList()

        data.addAll(list)

        Log.v(TAG, "[loadRange] dif=$dif list=${list.size} data=${data.size}")

        callback.onResult(data.subList(params.startPosition, params.startPosition + params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<PresenterViewModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedStartPosition=${params.requestedStartPosition} data=${data.size} pageSize=${params.pageSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (params.requestedStartPosition until params.requestedLoadSize).map {
            val uri = createRandomImageUrl()
            data.add(PresenterViewModel(uri, layout))
        }

        callback.onResult(data, 0, data.size)
    }

    class Factory : DataSource.Factory<Int, PresenterViewModel<String>>() {

        lateinit var dataSource: SimplePositionalDataSource

        override fun create(): SimplePositionalDataSource {
            dataSource = SimplePositionalDataSource()
            return dataSource
        }
    }
}