package net.kibotu.android.recyclerviewpresenter.app.v2

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.exozet.android.core.misc.createRandomImageUrl
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.TAG
import kotlin.random.Random

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class SimplePositionalDataSource : PositionalDataSource<PresenterModel<String>>() {

    private val data = mutableListOf<PresenterModel<String>>()

    private val random by lazy { Random }

    @get:LayoutRes
    val layout
        get() = when (random.nextFloat()) {
            in 0f..0.33f -> R.layout.photo_presenter_item
            in 0.33f..0.66f -> R.layout.number_presenter_item
            else -> R.layout.label_presenter_item
        }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PresenterModel<String>>) {
        Log.v(TAG, "[loadRange] startPosition=${params.startPosition} data=${data.size} loadSize=${params.loadSize}")

        val dif = data.size - params.startPosition

        val list = (dif until params.loadSize).map {
            PresenterModel(createRandomImageUrl(), layout)
        }.toList()

        data.addAll(list)

        Log.v(TAG, "[loadRange] dif=$dif list=${list.size} data=${data.size}")

        callback.onResult(data.subList(params.startPosition, params.startPosition + params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<PresenterModel<String>>) {
        Log.v(
            TAG,
            "[loadInitial] requestedStartPosition=${params.requestedStartPosition} data=${data.size} pageSize=${params.pageSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}"
        )

        (params.requestedStartPosition until params.requestedLoadSize).map {
            val uri = createRandomImageUrl()
            data.add(PresenterModel(uri, layout))
        }

        callback.onResult(data, 0, data.size)
    }

    class Factory : DataSource.Factory<Int, PresenterModel<String>>() {

        lateinit var dataSource: SimplePositionalDataSource

        override fun create(): SimplePositionalDataSource {
            dataSource = SimplePositionalDataSource()
            return dataSource
        }
    }
}