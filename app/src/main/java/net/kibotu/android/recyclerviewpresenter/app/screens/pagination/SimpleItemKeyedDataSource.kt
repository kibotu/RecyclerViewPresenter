package net.kibotu.android.recyclerviewpresenter.app.screens.pagination

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.createRandomImageUrl
import net.kibotu.logger.TAG

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class SimpleItemKeyedDataSource : ItemKeyedDataSource<String, PresenterViewModel<String>>() {

    private val data = mutableListOf<PresenterViewModel<String>>()

    @LayoutRes
    val layout = R.layout.photo_presenter_item

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<PresenterViewModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedInitialKey=${params.requestedInitialKey} data=${data.size} requestedLoadSize=${params.requestedLoadSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (0 until params.requestedLoadSize).map {
            data.add(PresenterViewModel(createRandomImageUrl(), layout))
        }

        callback.onResult(data)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<PresenterViewModel<String>>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize} data=${data.size}")

        (0 until params.requestedLoadSize).map {
            data.add(PresenterViewModel(createRandomImageUrl(), layout))
        }

        callback.onResult(data)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<PresenterViewModel<String>>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    override fun getKey(item: PresenterViewModel<String>): String = item.uuid

    class Factory : DataSource.Factory<String, PresenterViewModel<String>>() {

        lateinit var dataSource: SimpleItemKeyedDataSource

        override fun create(): SimpleItemKeyedDataSource {
            dataSource = SimpleItemKeyedDataSource()
            return dataSource
        }
    }
}