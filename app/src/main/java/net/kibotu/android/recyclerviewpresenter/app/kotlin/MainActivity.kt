package net.kibotu.android.recyclerviewpresenter.app.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exozet.android.core.extensions.toast
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.RecyclerViewModel
import net.kibotu.android.recyclerviewpresenter.app.R
import net.kibotu.android.recyclerviewpresenter.app.misc.FakeDataGenerator.createRandomImageUrl

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PresenterAdapter<RecyclerViewModel<String>>()
        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        adapter.onItemClick { item, view, position ->
            toast("$position. ${item.model}")
        }

        adapter.onFocusChange { item, view, hasFocus, position ->

        }

        for (i in 0 until 100) {
            adapter.append(RecyclerViewModel(createRandomImageUrl()), PhotoPresenter::class.java)
            adapter.append(RecyclerViewModel(createRandomImageUrl()), LabelPresenter::class.java)
        }

        adapter.update(0, RecyclerViewModel("https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png"))

        adapter.notifyDataSetChanged()
    }
}