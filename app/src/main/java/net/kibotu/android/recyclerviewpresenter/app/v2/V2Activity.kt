package net.kibotu.android.recyclerviewpresenter.app.v2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterPageListAdapter
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.kotlin.LabelPresenter
import net.kibotu.android.recyclerviewpresenter.app.kotlin.NumberPresenter
import net.kibotu.android.recyclerviewpresenter.app.kotlin.PhotoPresenter
import net.kibotu.logger.Logger.logv
import net.kibotu.logger.snack
import java.text.MessageFormat.format


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class V2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<RecyclerView>(R.id.list)

        val adapter = PresenterPageListAdapter<String>()
        adapter.registerPresenter(PhotoPresenter())
        adapter.registerPresenter(LabelPresenter())
        adapter.registerPresenter(NumberPresenter())

        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        adapter.onItemClick { item, rowView, position ->
            snack(format("{0}. {1}", position, item))
            Unit
        }

        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setPrefetchDistance(3)
            .setEnablePlaceholders(false)
            .build()

        val positionalDataSourceFactory = SimplePositionalDataSource.Factory()
        val positionalDataSource = LivePagedListBuilder(positionalDataSourceFactory, config).build()

        positionalDataSource.observe(this, Observer {
            logv("positionalDataSource data: ${it.size}")
            adapter.submitList(it)
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            positionalDataSourceFactory.dataSource.invalidate()
        }
    }
}