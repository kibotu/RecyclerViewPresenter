package net.kibotu.android.recyclerviewpresenter.app.screens.nested

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R

class NestedActivity : AppCompatActivity() {

    val adapter = PresenterAdapter()

    private val list: RecyclerView
        get() = findViewById(R.id.list)

    private val swipeRefresh: SwipeRefreshLayout
        get() = findViewById(R.id.swipeRefresh)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addResultList()
    }

    private fun addResultList() {

        list.layoutManager = LinearLayoutManager(this)
        list.setRecycledViewPool(RecyclerView.RecycledViewPool())
        list.setHasFixedSize(true)

        adapter.registerPresenter(RowPresenter())
        adapter.registerPresenter(HorizontalListPresenter())
        list.adapter = adapter

        addResults(100)


        // ignore for now
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }
    }

    private fun addResults(amount: Int) {

        val images = (0 until amount).map {
            "https://via.placeholder.com/2%02d/300/".format(it)
        }

        val items: MutableList<PresenterViewModel<*>> = images.map {
            PresenterViewModel(Row(it, "$it label"), uuid = it, layout = R.layout.item_icon_with_label)
        }.toMutableList()

        items.add(0, PresenterViewModel(HorizontalListItems(images.map {
            Column(it)
        }), R.layout.item_horizontal_list))

        adapter.submitList(items)
    }
}