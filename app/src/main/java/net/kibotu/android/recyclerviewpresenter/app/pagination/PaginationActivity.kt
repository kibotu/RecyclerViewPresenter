package net.kibotu.android.recyclerviewpresenter.app.pagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterPageListAdapter
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.decorateWithAlphaScaleAdapter
import net.kibotu.logger.Logger.logv

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 *
 * https://guides.codepath.com/android/Paging-Library-Guide
 */
class PaginationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val adapter = PresenterPageListAdapter<String>()
        adapter.registerPresenter(PhotoPresenter())

        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter.decorateWithAlphaScaleAdapter()

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setPrefetchDistance(3)
            .setEnablePlaceholders(false)
            .build()

        // http://androidkt.com/paging-library-datasource/
        val pageKeyedDataSourceFactory = SimplePageKeyedDataSource.Factory()
        val pageKeyedDataSource = LivePagedListBuilder(pageKeyedDataSourceFactory, config).build()
        val positionalDataSourceFactory = SimplePositionalDataSource.Factory()
        val positionalDataSource = LivePagedListBuilder(positionalDataSourceFactory, config).build()
        val itemKeyedDataSourceFactory = SimpleItemKeyedDataSource.Factory()
        val itemKeyedDataSource = LivePagedListBuilder(itemKeyedDataSourceFactory, config).build()


        positionalDataSource.observe(this, Observer {
            logv("positionalDataSource data: ${it.size}")
            adapter.submitList(it)
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            //            pageKeyedDataSourceFactory.dataSource.value?.invalidate()
            positionalDataSourceFactory.dataSource.invalidate()
//            itemKeyedDataSourceFactory.dataSource.invalidate()
        }
    }
}