package net.kibotu.android.recyclerviewpresenter.app

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pagination.*
import kotlinx.android.synthetic.main.photo_presenter_item.view.*
import net.kibotu.android.recyclerviewpresenter.RecyclerViewHolder

class PaginationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pagination)

        val adapter = FakePageListAdapter()
        list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        list.adapter = adapter

        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .build()

        val liveData = LivePagedListBuilder<Int, String>(SimpleDataSource.Factory(), config).build()

        liveData.observe(this) {
            adapter.submitList(it)
        }
    }

    class FakePageListAdapter : PagedListAdapter<String, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = RecyclerViewHolder(R.layout.photo_presenter_item, parent)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            with(holder.itemView) {
                Glide.with(this).load(getItem(position)).into(photo)
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}