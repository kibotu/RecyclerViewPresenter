package net.kibotu.android.recyclerviewpresenter.app.v2

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.exozet.android.core.extensions.clamp
import com.exozet.android.core.misc.createRandomImageUrl
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.Logger.logv
import kotlin.random.Random

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class SimplePositionalDataSource : PositionalDataSource<PresenterModel<String>>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PresenterModel<String>>) {
        // new start position is larger than list size
        if (params.startPosition >= cache.size) {
            logv("[loadRange] ")
            return
        }

        val toIndex = (params.startPosition + params.loadSize).clamp(0, cache.lastIndex)

        logv("[loadRange] from=${params.startPosition} to=$toIndex data=${cache.size} loadSize=${params.loadSize}")

        val list = cache.subList(params.startPosition, toIndex)

        logv("[loadRange] list=${list.size} data=${cache.size}")

        callback.onResult(list)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<PresenterModel<String>>) {
        logv("[loadInitial] requestedStartPosition=${params.requestedStartPosition} data=${cache.size} pageSize=${params.pageSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}")

        val list = cache.take(params.requestedLoadSize)

        callback.onResult(list, 0, list.size)
    }

    class Factory : DataSource.Factory<Int, PresenterModel<String>>() {

        lateinit var dataSource: SimplePositionalDataSource

        override fun create(): SimplePositionalDataSource {
            dataSource = SimplePositionalDataSource()
            return dataSource
        }
    }

    companion object {

        private val random by lazy { Random }

        @get:LayoutRes
        val layout
            get() = when (random.nextFloat()) {
//                in 0f..0.33f -> R.layout.photo_presenter_item
//                in 0.33f..0.66f -> R.layout.number_presenter_item
                else -> R.layout.photo_presenter_item
            }

        val cache = (0 until 100).map {
            val uri = createRandomImageUrl()

            PresenterModel(uri, layout, changedPayload = { new, old ->
                if (new != old)
                    Bundle().apply {
                        putString("TEXT_CHANGED_TO", new)
                    }
                else
                    null
            })
        }.toList()
    }
}