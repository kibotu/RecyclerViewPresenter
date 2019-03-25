package net.kibotu.android.recyclerviewpresenter.app

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.android.recyclerviewpresenter.app.FakeDataGenerator.createRandomImageUrl
import net.kibotu.android.recyclerviewpresenter.v2.PresenterAdapter
import net.kibotu.android.recyclerviewpresenter.v2.RecyclerViewModel


class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PresenterAdapter<RecyclerViewModel<String>>()
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        adapter.onItemClick { item, view, position ->
            toast("$position. ${item.model}")
        }

        for (i in 0 until 100) {
            adapter.add(RecyclerViewModel(createRandomImageUrl()), ImagePresenter::class.java)
        }

        // sorting
        // PresenterAdapter.sort(adapter);
        // sort if model doesn't implement Comparable
        // adapter.sortBy((o1, o2) -> o1.compareTo(o2));

        //        adapter.update(0, "https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png");

        adapter.notifyDataSetChanged()
    }

    fun toast(message: String?) {
        if (isEmpty(message))
            return

        runOnUiThread {
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 100)
            toast.show()
        }
    }
}