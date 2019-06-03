package net.kibotu.android.recyclerviewpresenter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


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

    /**
     * Reference to the bound [RecyclerView].
     */
    var recyclerView: RecyclerView? = null

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

    override fun getItemCount(): Int = data.size

    operator fun get(position: Int) = data[position]

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
        this.recyclerView = recyclerView
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
     * Also calls [IBaseViewHolder.onBindViewHolder].
     */
    override fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(viewHolder)
        if (viewHolder is IBaseViewHolder)
            (viewHolder as IBaseViewHolder).onViewDetachedFromWindow()
    }

    // endregion

    @JvmOverloads
    fun submitList(items: List<PresenterModel<*>>, scrollToTop: Boolean = true) {
        val diffResult = DiffUtil.calculateDiff(PresenterModelDiffCallback(data, items))
        diffResult.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(items)
        if (scrollToTop)
            recyclerView?.scrollToPosition(0)
    }
}