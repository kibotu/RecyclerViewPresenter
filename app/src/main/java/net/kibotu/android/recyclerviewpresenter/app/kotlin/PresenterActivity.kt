package net.kibotu.android.recyclerviewpresenter.app.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exozet.android.core.extensions.toast
import com.exozet.android.core.misc.createRandomImageUrl
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.app.R

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class PresenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PresenterAdapter<Any>()
        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        adapter.onItemClick { item, view, position ->
            toast("$position. ${item.model}")
        }

        adapter.onFocusChange { item, view, hasFocus, position ->

        }

        for (i in 0 until 100) {
            adapter.append(createRandomImageUrl(), PhotoPresenter::class.java)
            adapter.append(createRandomImageUrl(), LabelPresenter::class.java)
            adapter.append(i, IntPresenter::class.java)
        }

        adapter.prepend(createRandomImageUrl(), LabelPresenter::class.java) { item, view, position ->
            toast("$position. $item")
        }

        adapter.update(0, "https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png", LabelPresenter::class.java)

        adapter.notifyDataSetChanged()
    }
}