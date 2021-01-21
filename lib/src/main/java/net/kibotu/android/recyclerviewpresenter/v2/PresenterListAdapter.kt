package net.kibotu.android.recyclerviewpresenter.v2

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.PresenterModel
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

open class PresenterListAdapter : ListAdapter<PresenterViewModel<*>, RecyclerView.ViewHolder>(
    AsyncDifferConfig
        .Builder(PresenterModelDiffCallback())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
), Adapter {

    // region recyclerview

    private var _recyclerView: WeakReference<RecyclerView>? = null

    /**
     * Reference to the bound [RecyclerView].
     */
    override var recyclerView: RecyclerView?
        get() = _recyclerView?.get()
        set(value) {
            _recyclerView = if (value != null)
                WeakReference(value)
            else
                null
        }

    // endregion

    // region Listener

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnClickListener].
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    protected var onItemClick: ((item: PresenterViewModel<*>, view: View, position: Int) -> Unit)? = null

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnClickListener].
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    fun onItemClick(block: ((item: PresenterViewModel<*>, view: View, position: Int) -> Unit)?) {
        onItemClick = block
    }

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnFocusChangeListener].
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus `True` if it is focused.
     */
    protected var onFocusChange: ((item: PresenterViewModel<*>, view: View, hasFocus: Boolean, position: Int) -> Unit)? = null

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnFocusChangeListener].
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus `True` if it is focused.
     */
    fun onFocusChange(block: ((item: PresenterViewModel<*>, view: View, hasFocus: Boolean, position: Int) -> Unit)?) {
        onFocusChange = block
    }

    // endregion


    // region presenter

    /**
     * Holds all registered presenter.
     */
    protected val presenter = mutableListOf<Presenter<*>>()

    fun registerPresenter(presenter: Presenter<*>) {
        if (this.presenter.firstOrNull { it.layout == presenter.layout } != null)
            throw IllegalArgumentException("Layout '${resName(presenter.layout)}' already registered, each presenter layout needs to be unique.")
        this.presenter.add(presenter)
        if (presenter.adapter != null)
            throw IllegalArgumentException("Presenter already registered to ${presenter.adapter!!::class.java}.")
        presenter.adapter = this
    }

    fun unregisterPresenter(presenter: Presenter<*>) = this.presenter.remove(presenter)

    fun unregisterPresenter() = presenter.clear()

    // endregion

    // region RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = presenterByViewType(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = getItem(position)

        onItemClick?.let { holder.itemView.setOnClickListener { it(item, it, position) } }

        onFocusChange?.let { holder.itemView.setOnFocusChangeListener { v, hasFocus -> it(item, v, hasFocus, position) } }

        presenterAtAdapterPosition(position).bindViewHolder(holder, item, null)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {

        val item = getItem(position)

        onItemClick?.let { holder.itemView.setOnClickListener { it(item, it, position) } }

        onFocusChange?.let { holder.itemView.setOnFocusChangeListener { v, hasFocus -> it(item, v, hasFocus, position) } }

        presenterAtAdapterPosition(position).bindViewHolder(holder, item, payloads)
    }

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
    protected fun presenterByViewType(viewType: Int): Presenter<*> = this.presenter.firstOrNull { it.layout == viewType }
        ?: throw IllegalArgumentException("No presenter registered for '${resName(viewType)}'. Please register presenter using adapter#registerPresenter.")

    /**
     * {@inheritDoc}
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    /**
     * {@inheritDoc}
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
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

    /**
     * {@inheritDoc}
     */
    fun removeAllViews() {
        recyclerView?.layoutManager?.removeAllViews()
        recyclerView?.removeAllViews()
    }

    fun clear() {
        removeAllViews()
        notifyDataSetChanged()
    }

    // endregion
}