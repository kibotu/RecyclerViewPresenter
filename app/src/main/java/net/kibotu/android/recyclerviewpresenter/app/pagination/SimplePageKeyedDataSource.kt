package net.kibotu.android.recyclerviewpresenter.app.pagination

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.createRandomImageUrl
import net.kibotu.logger.TAG

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class SimplePageKeyedDataSource : PageKeyedDataSource<Int, PresenterModel<String>>() {

    private val data = mutableListOf<PresenterModel<String>>()

    @LayoutRes
    val layout = R.layout.photo_presenter_item

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PresenterModel<String>>) {
        Log.v(TAG, "[loadInitial] requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}")

        (0 until params.requestedLoadSize).map {
            data.add(PresenterModel(createRandomImageUrl(), layout))
        }

        callback.onResult(data, null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterModel<String>>) {
        Log.v(TAG, "[loadAfter] key=${params.key} requestedLoadSize=${params.requestedLoadSize} data=${data.size}")

        val list = (0 until params.requestedLoadSize).map {
            PresenterModel(createRandomImageUrl(), layout)
        }

        Log.v(TAG, "[loadRange] list=${list.size} data=${data.size}")

        data.addAll(list)

        callback.onResult(data, params.key + list.size)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PresenterModel<String>>) {
        Log.v(TAG, "[loadBefore] key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
    }

    class Factory : DataSource.Factory<Int, PresenterModel<String>>() {

        val dataSource by lazy { MutableLiveData<SimplePageKeyedDataSource>() }

        override fun create(): SimplePageKeyedDataSource {
            val source = SimplePageKeyedDataSource()
            dataSource.postValue(source)
            return source
        }
    }
}