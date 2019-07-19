package net.kibotu.android.recyclerviewpresenter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference


open class PresenterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Adapter {

    /**
     * Actual data.
     */
    protected val data: ArrayList<PresenterModel<*>> = arrayListOf()

    /**
     * Holds all registered presenter.
     */
    protected val presenter = mutableListOf<Presenter<*>>()

    fun registerPresenter(presenter: Presenter<*>) {
        if (this.presenter.firstOrNull { it.layout == presenter.layout } != null)
            throw IllegalArgumentException("Layout already registered, each presenter layout needs to be unique.")
        this.presenter.add(presenter)
    }

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
            _recyclerView = WeakReference(value)
        }

    /**
     * Represents if adapter should be circular.
     */
    var isCircular: Boolean = false

    // region RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = presenterByViewType(viewType).onCreateViewHolder(parent)

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

    var maxSizeFactor = 100

    internal val max
        get() = data.size * maxSizeFactor

    override fun getItemCount(): Int = if (isCircular) max else data.size

    operator fun get(position: Int) = if (isCircular) data[position % data.size] else data[position]

    fun getItem(position: Int) = this[position]

    /**
     * Returns [LayoutRes] at adapter position.
     */
    @LayoutRes
    override fun getItemViewType(position: Int): Int = getItem(position).layout

    /**
     * Returns presenter at adapter position.
     */
    protected fun presenterAtAdapterPosition(position: Int) = with(getItemViewType(position)) { presenter.first { it.layout == this@with } }

    /**
     * Returns presenter by [LayoutRes] .
     */
    protected fun presenterByViewType(viewType: Int) = presenter.first { it.layout == viewType }

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
     */
    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
        super.onViewRecycled(viewHolder)
        if (viewHolder is IBaseViewHolder)
            (viewHolder as IBaseViewHolder).onViewRecycled()
    }

    /**
     * {@inheritDoc}
     */
    override fun onFailedToRecycleView(viewHolder: RecyclerView.ViewHolder): Boolean {
        return if (viewHolder is IBaseViewHolder)
            (viewHolder as IBaseViewHolder).onFailedToRecycleView()
        else
            super.onFailedToRecycleView(viewHolder)
    }

    /**
     * {@inheritDoc}
     *
     * Also calls [IBaseViewHolder.onViewAttachedToWindow].
     */
    override fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(viewHolder)
        if (viewHolder is IBaseViewHolder)
            (viewHolder as IBaseViewHolder).onViewAttachedToWindow()
    }

    /**
     * {@inheritDoc}
     *
     * Also calls [IBaseViewHolder.onViewDetachedFromWindow].
     */
    override fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(viewHolder)
        if (viewHolder is IBaseViewHolder)
            (viewHolder as IBaseViewHolder).onViewDetachedFromWindow()
    }

    fun clear() {
        presenter.clear()
        removeAllViews()
        notifyDataSetChanged()
    }

    /**
     * {@inheritDoc}
     */
    protected fun removeAllViews() {
        recyclerView?.removeAllViews()
    }

    // endregion

    @JvmOverloads
    fun submitList(items: List<PresenterModel<*>>, scrollToTop: Boolean = false) {
        val initial = isCircular && data.isNotEmpty()

        val diffResult = DiffUtil.calculateDiff(PresenterModelDiffCallback(data, items))
        diffResult.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(items)

        if (initial || scrollToTop)
            scrollToPosition(0)
    }

    fun scrollToPosition(position: Int) {
        if (isCircular)
            recyclerView?.scrollToPosition(((max / 2) - ((max / 2) % data.size)))
        else
            recyclerView?.scrollToPosition(position)
    }

    fun smoothScrollToPosition(position: Int) {
        if (isCircular)
            recyclerView?.smoothScrollToPosition(((max / 2) - ((max / 2) % data.size)) + position)
        else
            recyclerView?.smoothScrollToPosition(position)
    }
}