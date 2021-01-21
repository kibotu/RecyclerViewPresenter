package net.kibotu.android.recyclerviewpresenter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

open class PresenterPageListAdapter<T> : PagedListAdapter<PresenterModel<T>, RecyclerView.ViewHolder>(ItemCallback<T>()), Adapter {

    /**
     * Holds all registered presenter.
     */
    protected val presenter = mutableListOf<Presenter<*>>()

    fun registerPresenter(presenter: Presenter<*>) {
        require(this.presenter.firstOrNull { it.layout == presenter.layout } == null) { "Layout already registered, each presenter layout needs to be unique." }
        this.presenter.add(presenter)
    }

    fun unregisterPresenter() = presenter.clear()

    // region Listener

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnClickListener].
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    protected var onItemClick: ((item: PresenterModel<*>, view: View, position: Int) -> Unit)? = null

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnClickListener].
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    fun onItemClick(block: ((item: PresenterModel<*>, view: View, position: Int) -> Unit)?) {
        onItemClick = block
    }

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnFocusChangeListener].
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus `True` if it is focused.
     */
    protected var onFocusChange: ((item: PresenterModel<*>, view: View, hasFocus: Boolean, position: Int) -> Unit)? = null

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnFocusChangeListener].
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus `True` if it is focused.
     */
    fun onFocusChange(block: ((item: PresenterModel<*>, view: View, hasFocus: Boolean, position: Int) -> Unit)?) {
        onFocusChange = block
    }

    // endregion

    protected var _recyclerView: WeakReference<RecyclerView>? = null

    /**
     * Reference to the bound [RecyclerView].
     */
    override var recyclerView
        get() = _recyclerView?.get()
        set(value) {
            if (value != null)
                _recyclerView = WeakReference(value)
            else
                _recyclerView = null
        }

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): RecyclerView.ViewHolder = presenterByViewType(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = this[position]

        onItemClick?.let { holder.itemView.setOnClickListener { it(item, it, position) } }

        onFocusChange?.let { holder.itemView.setOnFocusChangeListener { v, hasFocus -> it(item, v, hasFocus, position) } }

        presenterAtAdapterPosition(position).bindViewHolder(holder, item, position, null, this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {

        val item = this[position]

        onItemClick?.let { holder.itemView.setOnClickListener { it(item, it, position) } }

        onFocusChange?.let { holder.itemView.setOnFocusChangeListener { v, hasFocus -> it(item, v, hasFocus, position) } }

        presenterAtAdapterPosition(position).bindViewHolder(holder, item, position, payloads, this)
    }

    operator fun get(position: Int) = requireNotNull(getItem(position))

    /**
     * Returns presenter at adapter position.
     */
    protected fun presenterAtAdapterPosition(position: Int) = with(getItemViewType(position)) { presenter.first { it.layout == this@with } }

    /**
     * Returns presenter by [LayoutRes] .
     */
    protected fun presenterByViewType(viewType: Int) = presenter.first { it.layout == viewType }

    /**
     * Returns [LayoutRes] at adapter position.
     */
    @LayoutRes
    override fun getItemViewType(position: Int): Int = getItem(position)?.layout ?: -1

    fun clear() {
        removeAllViews()
        notifyDataSetChanged()
    }

    /**
     * {@inheritDoc}
     */
    protected fun removeAllViews() {
        recyclerView?.layoutManager?.removeAllViews()
        recyclerView?.removeAllViews()
    }

    /**
     * {@inheritDoc}
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    /**
     * {@inheritDoc}
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
    }

    /**
     * {@inheritDoc}
     *
     * Also calls [IBaseViewHolder.onBindViewHolder].
     */
    override fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(viewHolder)
        if (viewHolder is IBaseViewHolder)
            (viewHolder as IBaseViewHolder).onViewDetachedFromWindow()
    }

    /**
     * {@inheritDoc}
     *
     * Also calls [IBaseViewHolder.onViewDetachedFromWindow].
     */
    override fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(viewHolder)
        if (viewHolder is IBaseViewHolder)
            (viewHolder as IBaseViewHolder).onViewAttachedToWindow()
    }

    /**
     * {@inheritDoc}
     */
    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is IBaseViewHolder) {
            (viewHolder as IBaseViewHolder).onViewRecycled()
            viewHolder.itemView.setOnClickListener(null)
            viewHolder.itemView.onFocusChangeListener = null
        }
        super.onViewRecycled(viewHolder)
    }

    open class ItemCallback<T> : DiffUtil.ItemCallback<PresenterModel<T>>() {

        override fun areContentsTheSame(oldItem: PresenterModel<T>, newItem: PresenterModel<T>): Boolean = oldItem.uuid == newItem.uuid

        override fun areItemsTheSame(oldItem: PresenterModel<T>, newItem: PresenterModel<T>): Boolean = oldItem.model == newItem.model

        override fun getChangePayload(oldItem: PresenterModel<T>, newItem: PresenterModel<T>): Any? = oldItem.changedPayload(oldItem.model as Any) ?: super.getChangePayload(oldItem, newItem)
    }

}