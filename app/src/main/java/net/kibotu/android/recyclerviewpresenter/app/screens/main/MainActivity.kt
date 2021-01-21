package net.kibotu.android.recyclerviewpresenter.app.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.startActivity
import net.kibotu.android.recyclerviewpresenter.app.screens.circular.CircularPresenterActivity
import net.kibotu.android.recyclerviewpresenter.app.screens.kotlin.PresenterActivity
import net.kibotu.android.recyclerviewpresenter.app.screens.nested.NestedActivity
import net.kibotu.android.recyclerviewpresenter.app.screens.pagination.PaginationActivity
import net.kibotu.android.recyclerviewpresenter.app.screens.v2.V2Activity

typealias JavaPresenterActivity = net.kibotu.android.recyclerviewpresenter.app.java.PresenterActivity

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val adapter = PresenterAdapter()

    private val list: RecyclerView
        get() = findViewById(R.id.list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.registerPresenter(ButtonPresenter())
        list.adapter = adapter

        val items: MutableList<PresenterViewModel<*>> = mutableListOf(
            PresenterViewModel("Kotlin", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<PresenterActivity>()
            }),
            PresenterViewModel("Java", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<JavaPresenterActivity>()
            }),
            PresenterViewModel("Circular Scrolling", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<CircularPresenterActivity>()
            }),
            PresenterViewModel("Pagination", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<PaginationActivity>()
            }),
            PresenterViewModel("Nested Scrolling", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<NestedActivity>()
            })
        )

        adapter.submitList(items)

        // start default use case
        startActivity<CircularPresenterActivity>()
    }
}
