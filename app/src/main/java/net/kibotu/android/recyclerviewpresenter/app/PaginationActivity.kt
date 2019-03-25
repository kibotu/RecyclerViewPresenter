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
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            .setPrefetchDistance(1)
            .setEnablePlaceholders(false)
            .build()

        val liveData = LivePagedListBuilder<Int, SimpleDataSource.ViewModel<String>>(SimpleDataSource.Factory(), config).build()

        liveData.observe(this) {
            adapter.submitList(it)
        }
    }

    class FakePageListAdapter : PagedListAdapter<SimpleDataSource.ViewModel<String>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            RecyclerViewHolder(R.layout.photo_presenter_item, parent)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            with(holder.itemView) {

                GlideApp.with(this.context.applicationContext)
                    .load(getItem(position)!!.t)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(photo)
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SimpleDataSource.ViewModel<String>>() {
            override fun areItemsTheSame(oldItem: SimpleDataSource.ViewModel<String>, newItem: SimpleDataSource.ViewModel<String>): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: SimpleDataSource.ViewModel<String>, newItem: SimpleDataSource.ViewModel<String>): Boolean {
                return oldItem.uuid == newItem.uuid
            }
        }
    }
}