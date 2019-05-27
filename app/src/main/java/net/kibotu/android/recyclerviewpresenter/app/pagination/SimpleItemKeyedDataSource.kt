package net.kibotu.android.recyclerviewpresenter.app.pagination

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.exozet.android.core.misc.createRandomImageUrl
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.TAG

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class SimpleItemKeyedDataSource : ItemKeyedDataSource<String, PresenterModel<String>>() {

    private val data = mutableListOf<PresenterModel<String>>()

    @LayoutRes
    val layout = R.layout.photo_presenter_item

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<PresenterModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedInitialKey=${params.requestedInitialKey} data=${data.size} requestedLoadSize=${params.requestedLoadSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (0 until params.requestedLoadSize).map {
            data.add(PresenterModel(createRandomImageUrl(), layout))
        }

        callback.onResult(data)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<PresenterModel<String>>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize} data=${data.size}")

        (0 until params.requestedLoadSize).map {
            data.add(PresenterModel(createRandomImageUrl(), layout))
        }

        callback.onResult(data)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<PresenterModel<String>>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    override fun getKey(item: PresenterModel<String>): String = item.uuid

    class Factory : DataSource.Factory<String, PresenterModel<String>>() {

        lateinit var dataSource: SimpleItemKeyedDataSource

        override fun create(): SimpleItemKeyedDataSource {
            dataSource = SimpleItemKeyedDataSource()
            return dataSource
        }
    }
}