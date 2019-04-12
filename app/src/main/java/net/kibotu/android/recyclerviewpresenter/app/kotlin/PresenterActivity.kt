package net.kibotu.android.recyclerviewpresenter.app.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exozet.android.core.extensions.toast
import com.exozet.android.core.misc.createRandomImageUrl
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.RecyclerViewModel
import net.kibotu.android.recyclerviewpresenter.app.R

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class PresenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PresenterAdapter<RecyclerViewModel<*>>()
        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        adapter.onItemClick { item, view, position ->
            toast("$position. ${item.model}")
        }

        adapter.onFocusChange { item, view, hasFocus, position ->

        }

        for (i in 0 until 100) {
            adapter.append(item = RecyclerViewModel(createRandomImageUrl()), clazz = PhotoPresenter::class.java)
            adapter.append(item = RecyclerViewModel(createRandomImageUrl()), clazz = LabelPresenter::class.java)
            adapter.append(item = RecyclerViewModel(i), clazz = IntPresenter::class.java)
        }

        adapter.prepend(
            RecyclerViewModel(
                model = createRandomImageUrl(),
                onItemClickListener = { item, view, position ->
                    toast("$position. $item")
                }), LabelPresenter::class.java
        )

        adapter.update(0, RecyclerViewModel("https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png"))

        adapter.notifyDataSetChanged()
    }
}

