package net.kibotu.android.recyclerviewpresenter.app.screens.listadapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.v2.PresenterListAdapter
import net.kibotu.android.recyclerviewpresenter.v2.PresenterViewModel
import net.kibotu.logger.Logger
import net.kibotu.logger.snack

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class ListPresenterActivity : AppCompatActivity(R.layout.activity_main) {

    private val list: RecyclerView
        get() = findViewById(R.id.list)

    private val swipeRefresh: SwipeRefreshLayout
        get() = findViewById(R.id.swipeRefresh)

    private val adapter = PresenterListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter
        adapter.registerPresenter(PhotoPresenter())
        adapter.registerPresenter(LabelPresenter())
        adapter.registerPresenter(NumberPresenter())

        adapter.onItemClick { item, view, position ->
            snack("$position. ${item.model}")
            Logger.v("onItemClick $position")
        }

        adapter.onFocusChange { item, view, hasFocus, position ->
            Logger.v("onFocusChange $position")
        }

        adapter.onCurrentListChanged { previousList, currentList ->
            Logger.v("onCurrentListChanged ${previousList.size} -> ${currentList.size}")
        }

        adapter.isCircular = true

        val items = createItems()

        adapter.submitList(items)

        swipeRefresh.setOnRefreshListener {
            items.shuffle()
            adapter.submitList(items)
            swipeRefresh.isRefreshing = false
        }
    }

    private fun createItems() = mutableListOf<PresenterViewModel<*>>().apply {

        for (i in 0..100) {
            add(PresenterViewModel(i, R.layout.number_presenter_item))
//            add(PresenterViewModel(createRandomImageUrl(), R.layout.photo_presenter_item))
//            add(PresenterViewModel(createRandomImageUrl(), R.layout.label_presenter_item))
        }

//        shuffle()
    }

    override fun onDestroy() {
        adapter.clear()
        super.onDestroy()
    }
}