package net.kibotu.android.recyclerviewpresenter.app.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exozet.android.core.misc.createRandomImageUrl
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.logger.snack
import java.util.*

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class PresenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PresenterAdapter()
        adapter.registerPresenter(PhotoPresenter())
        adapter.registerPresenter(LabelPresenter())
        adapter.registerPresenter(NumberPresenter())

        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        adapter.onItemClick { item, view, position ->
            snack("$position. ${item.model}")
        }

        adapter.onFocusChange { item, view, hasFocus, position ->

        }

        val items = ArrayList<PresenterModel<String>>()

        for (i in 0..99) {
            items.add(PresenterModel(createRandomImageUrl(), R.layout.photo_presenter_item))
            items.add(PresenterModel(createRandomImageUrl(), R.layout.label_presenter_item) { item, view, position ->
                snack("$position: $item")
            })
            items.add(PresenterModel(createRandomImageUrl(), R.layout.number_presenter_item))
        }

        items.shuffle()

        adapter.isCircular = true
        adapter.submitList(items)

        swipeRefresh.setOnRefreshListener {
            items.shuffle()
            adapter.submitList(items)
            swipeRefresh.isRefreshing = false
        }
    }
}