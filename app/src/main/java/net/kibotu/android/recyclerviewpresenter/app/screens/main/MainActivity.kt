package net.kibotu.android.recyclerviewpresenter.app.screens.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.startActivity
import net.kibotu.android.recyclerviewpresenter.app.screens.kotlin.PresenterActivity
import net.kibotu.android.recyclerviewpresenter.app.screens.listadapter.ListPresenterActivity
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

        val items = listOf(
            PresenterModel("Kotlin", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<PresenterActivity>()
            }),
            PresenterModel("Java", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<JavaPresenterActivity>()
            }),
            PresenterModel("Pagination", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<PaginationActivity>()
            }),
            PresenterModel("Circular", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<V2Activity>()
            }),
            PresenterModel("Nested Scrolling", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<NestedActivity>()
            }),
            PresenterModel("ListPresenterAdapter", R.layout.item_button, onItemClickListener = { _, _, _ ->
                startActivity<ListPresenterActivity>()
            }),
        )

        adapter.submitList(items)
        // startActivity<ListPresenterActivity>()
    }
}
