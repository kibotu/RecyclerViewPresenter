package net.kibotu.android.recyclerviewpresenter.app.v2

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exozet.android.core.misc.createRandomImageUrl
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.CircularDataSource
import net.kibotu.android.recyclerviewpresenter.ListDataSource
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.PresenterPageListAdapter
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.kotlin.LabelPresenter
import net.kibotu.android.recyclerviewpresenter.app.kotlin.NumberPresenter
import net.kibotu.android.recyclerviewpresenter.app.kotlin.PhotoPresenter
import net.kibotu.logger.Logger.logv
import net.kibotu.logger.logv
import net.kibotu.logger.snack
import java.text.MessageFormat.format
import kotlin.random.Random


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class V2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PresenterPageListAdapter<String>()
        adapter.registerPresenter(PhotoPresenter())
        adapter.registerPresenter(LabelPresenter())
        adapter.registerPresenter(NumberPresenter())

        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        adapter.onItemClick { item, rowView, position ->
            snack(format("{0}. {1}", position, item))
        }

        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setPrefetchDistance(3)
            .setEnablePlaceholders(false)
            .build()

//        simplePositionalDataSource(config, adapter)
        circularDataSource(config, adapter)
    }

    private fun circularDataSource(config: PagedList.Config, adapter: PresenterPageListAdapter<String>) {

        swipeRefresh.isRefreshing = true

        val dataSourceFactory = CircularDataSource.Factory(FakeData.cache)
        val dataSourceSource = LivePagedListBuilder(dataSourceFactory, config).build()

        dataSourceSource.observe(this, Observer {
            logv("circularDataSource data: ${it.size}")
            adapter.submitList(it)
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            dataSourceFactory.invalidate()
        }
    }

    private fun simplePositionalDataSource(
        config: PagedList.Config,
        adapter: PresenterPageListAdapter<String>
    ) {

        swipeRefresh.isRefreshing = true

        val dataSourceFactory = ListDataSource.Factory(FakeData.cache)
        val dataSourceSource = LivePagedListBuilder(dataSourceFactory, config).build()

        dataSourceSource.observe(this, Observer {
            logv("positionalDataSource data: ${it.size}")
            adapter.submitList(it)
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            dataSourceFactory.invalidate()
        }
    }
}

object FakeData {

    private val random by lazy { Random }

    @get:LayoutRes
    val layout
        get() = when (random.nextFloat()) {
//            in 0f..0.33f -> R.layout.photo_presenter_item
//            in 0.33f..0.66f -> R.layout.number_presenter_item
            else -> R.layout.label_presenter_item
        }

    val cache = (0 until 10).map {
        val uri = "$it"
//        val uri = createRandomImageUrl()

        PresenterModel(uri, layout, uuid = it.toString(), changedPayload = { new, old ->
            if (new != old)
                Bundle().apply {
                    putString("TEXT_CHANGED_TO", new)
                }
            else
                null
        })
    }.toList()
}