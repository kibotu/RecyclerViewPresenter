package net.kibotu.android.recyclerviewpresenter.app.v2

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.exozet.android.core.extensions.clamp
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.logger.Logger.logv

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class SimplePositionalDataSource : PositionalDataSource<PresenterModel<String>>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PresenterModel<String>>) {
        // new start position is larger than list size
        if (params.startPosition >= FakeData.cache.size) {
            logv("[loadRange] ")
            return
        }

        val toIndex = (params.startPosition + params.loadSize).clamp(0, FakeData.cache.lastIndex)

        logv("[loadRange] from=${params.startPosition} to=$toIndex data=${FakeData.cache.size} loadSize=${params.loadSize}")

        val list = FakeData.cache.subList(params.startPosition, toIndex)

        logv("[loadRange] list=${list.size} data=${FakeData.cache.size}")

        callback.onResult(list)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<PresenterModel<String>>) {
        logv("[loadInitial] requestedStartPosition=${params.requestedStartPosition} data=${FakeData.cache.size} pageSize=${params.pageSize} requestedLoadSize=${params.requestedLoadSize} placeholdersEnabled=${params.placeholdersEnabled}")

        val list = FakeData.cache.take(params.requestedLoadSize)

        callback.onResult(list, 0, list.size)
    }

    class Factory : DataSource.Factory<Int, PresenterModel<String>>() {

        lateinit var dataSource: SimplePositionalDataSource

        override fun create(): SimplePositionalDataSource {
            dataSource = SimplePositionalDataSource()
            return dataSource
        }
    }
}