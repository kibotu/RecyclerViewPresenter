package net.kibotu.android.recyclerviewpresenter.app.nested

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R

class NestedActivity : AppCompatActivity() {

    val adapter = PresenterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addResultList()
    }

    private fun addResultList() {

        list.layoutManager = LinearLayoutManager(this)

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
            "https://lorempixel.com/2%02d/300/".format(it)
        }

        val items = images.map {
            PresenterModel<Any>(Row(it, "$it label"), uuid = it, layout = R.layout.item_icon_with_label)
        }.toMutableList()

        items.add(0, PresenterModel(HorizontalListItems(images.map {
            Column(it)
        }), R.layout.item_horizontal_list))

        adapter.submitList(items)
    }
}