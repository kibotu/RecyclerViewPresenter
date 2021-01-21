package net.kibotu.android.recyclerviewpresenter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.*
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.Executors

open class PresenterAdapter : ListAdapter<PresenterViewModel<*>, RecyclerView.ViewHolder>(
    AsyncDifferConfig
        .Builder(PresenterViewModelDiffCallback())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
), Adapter {

    /**
     * Unique Identifier.
     */
    val uuid by lazy { UUID.randomUUID().toString().subSequence(0, 8) }

    // region recyclerview

    /**
     * Internal reference to currently attached [RecyclerView].
     */
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
     * Called when the current List is updated.
     * <p>
     * If a <code>null</code> List is passed to {@link #submitList(List)}, or no List has been
     * submitted, the current List is represented as an empty List.
     *
     * @param previousList List that was displayed previously.
     * @param currentList new List being displayed, will be empty if {@code null} was passed to
     *          {@link #submitList(List)}.
     *
     * @see #getCurrentList()
     */
    fun onCurrentListChanged(block: ((previousList: List<PresenterViewModel<*>>, currentList: List<PresenterViewModel<*>>) -> Unit)?) {
        onCurrentListChanged = block
    }

    /**
     * Called when the current List is updated.
     * <p>
     * If a <code>null</code> List is passed to {@link #submitList(List)}, or no List has been
     * submitted, the current List is represented as an empty List.
     *
     * @param previousList List that was displayed previously.
     * @param currentList new List being displayed, will be empty if {@code null} was passed to
     *          {@link #submitList(List)}.
     *
     * @see #getCurrentList()
     */
    protected var onCurrentListChanged: ((previousList: List<PresenterViewModel<*>>, currentList: List<PresenterViewModel<*>>) -> Unit)? = null

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

    // region circular

    /**
     * Scroll buffer size.
     */
    val scrollBufferSize: Int
        get() = 10000

    /**
     * Represents if adapter should be circular.
     *
     * Note: use [scrollToPosition] or [smoothScrollToPosition] instead of [LinearLayoutManager.scrollToPosition] when using circular adapter.
     */
    var isCircular: Boolean = false

    /**
     * {@inheritDoc}
     */
    override fun getItemCount(): Int = if (isCircular) scrollBufferSize else currentList.size

    /**
     * {@inheritDoc}
     */
    override fun getItem(position: Int): PresenterViewModel<*> = this[position]

    /**
     * {@inheritDoc}
     */
    operator fun get(position: Int): PresenterViewModel<*> = if (isCircular) currentList.getCircular(position) else currentList[position]

    /**
     * Returns true if adapter is empty.
     */
    val isEmpty: Boolean
        get() = currentList.isEmpty()

    override fun submitList(list: MutableList<PresenterViewModel<*>>?) {
        val isEmpty = isEmpty
        super.submitList(list?.toList())

        if (isEmpty && isCircular) {
            recyclerView?.post {
                scrollToPosition(0)
            }
        }
    }

    override fun submitList(list: MutableList<PresenterViewModel<*>>?, commitCallback: Runnable?) {
        val isEmpty = isEmpty
        super.submitList(list?.toList(), commitCallback)

        if (isEmpty && isCircular) {
            recyclerView?.post {
                scrollToPosition(0)
            }
        }
    }

    // endregion

    // region presenter

    /**
     * Holds all registered presenter.
     */
    protected val presenter = mutableListOf<Presenter<*>>()

    fun registerPresenter(presenter: Presenter<*>) {
        if (presenter.adapter != null)
            throw IllegalArgumentException("Presenter already registered to ${requireNotNull(presenter.adapter)::class.java}.")

        if (this.presenter.firstOrNull { it.layout == presenter.layout } != null)
            throw IllegalArgumentException("Layout '${resName(presenter.layout)}' already registered, each presenter layout needs to be unique.")

        this.presenter.add(presenter)
        presenter.adapter = this
    }

    fun unregisterPresenter(presenter: Presenter<*>) {
        presenter.adapter = null
        this.presenter.remove(presenter)
    }

    fun unregisterPresenter() {
        presenter.forEach { it.adapter = null }
        presenter.clear()
    }

    // endregion

    // region RecyclerView.Adapter

    /**
     * {@inheritDoc}
     *
     * Creates [RecyclerViewHolder] by its [Presenter].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = presenterByViewType(viewType).onCreateViewHolder(parent)

    /**
     * {@inheritDoc}
     *
     * Binds [RecyclerViewHolder] from its [Presenter].
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = getItem(position)

        onItemClick?.let { holder.itemView.setOnClickListener { it(item, it, position) } }

        onFocusChange?.let { holder.itemView.setOnFocusChangeListener { v, hasFocus -> it(item, v, hasFocus, position) } }

        presenterAtAdapterPosition(position).bindViewHolder(holder, item, null)
    }

    /**
     * {@inheritDoc}
     *
     * Binds [RecyclerViewHolder] from its [Presenter] with payload.
     */
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
    override fun onCurrentListChanged(previousList: MutableList<PresenterViewModel<*>>, currentList: MutableList<PresenterViewModel<*>>) {
        super.onCurrentListChanged(previousList, currentList)
        onCurrentListChanged?.invoke(previousList, currentList)
    }

    fun removeAllViews() {
        recyclerView?.layoutManager?.removeAllViews()
        recyclerView?.removeAllViews()
    }

    fun clear() {
        removeAllViews()
        notifyDataSetChanged()
    }

    // endregion

    // region scrolling

    fun scrollToPosition(position: Int) {
        if (isCircular)
            recyclerView?.scrollToPosition(((scrollBufferSize / 2) - ((scrollBufferSize / 2) % currentList.size)))
        else
            recyclerView?.scrollToPosition(position)
    }

    fun smoothScrollToPosition(position: Int) {
        if (isCircular)
            recyclerView?.smoothScrollToPosition(((scrollBufferSize / 2) - ((scrollBufferSize / 2) % currentList.size)) + position)
        else
            recyclerView?.smoothScrollToPosition(position)
    }

    // endregion
}