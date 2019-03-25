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
            .setPageSize(30)
            .setPrefetchDistance(3)
            .setEnablePlaceholders(false)
            .build()

        // http://androidkt.com/paging-library-datasource/
        val pagedKeyedDataSource = LivePagedListBuilder<Int, ViewModel<String>>(SimplePageKeyedDataSource.Factory(), config).build()
        val positionalDataSource = LivePagedListBuilder<Int, ViewModel<String>>(SimplePositionalDataSource.Factory(), config).build()

        positionalDataSource.observe(this) {
            adapter.submitList(it)
        }
    }

    class FakePageListAdapter : PagedListAdapter<ViewModel<String>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            RecyclerViewHolder(R.layout.photo_presenter_item, parent)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            with(holder.itemView) {

                GlideApp.with(this.context.applicationContext)
                    .load(getItem(position)!!.t)
                    .into(photo)
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ViewModel<String>>() {
            override fun areItemsTheSame(oldItem: ViewModel<String>, newItem: ViewModel<String>): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ViewModel<String>, newItem: ViewModel<String>): Boolean {
                return oldItem.uuid == newItem.uuid
            }
        }
    }
}