package net.kibotu.android.recyclerviewpresenter.v2

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.kibotu.android.recyclerviewpresenter.IPresenterAdapter
import net.kibotu.android.recyclerviewpresenter.v1.PresenterAdapter
import java.lang.reflect.Constructor
import java.util.*

open class PresenterAdapter<T : RecyclerViewModel<*>> : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IPresenterAdapter<T> {

    /**
     * Actual data containing {@link T} and it's {@link Presenter} type.
     */
    protected val data: ArrayList<Pair<T, Class<*>>> = arrayListOf()

    /**
     * List of allocated concrete implementation and used [Presenter].
     */
    protected var binderType: ArrayList<Presenter<T>> = arrayListOf()

    /**
     * Factory for [RecyclerView.ViewHolder]
     */
    var viewHolderFactory: ((parent: ViewGroup, layout: Int) -> RecyclerView.ViewHolder) = { parent, layout -> RecyclerViewHolder(parent, layout) }

    // region Listener

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnClickListener].
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    protected var onItemClick: ((item: T, view: View, position: Int) -> Unit)? = null

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnClickListener].
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    fun onItemClick(block: ((item: T, view: View, position: Int) -> Unit)?) {
        onItemClick = block
    }

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnFocusChangeListener].
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus `True` if it is focused.
     */
    protected var onFocusChange: ((item: T, view: View, hasFocus: Boolean, position: Int) -> Unit)? = null

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnFocusChangeListener].
     *
     * @param item     Model of the adapter.
     * @param view     View that has been clicked.
     * @param hasFocus `True` if it is focused.
     */
    fun onFocusChange(block: ((item: T, view: View, hasFocus: Boolean, position: Int) -> Unit)?) {
        onFocusChange = block
    }

    /**
     * Reference to the bound [RecyclerView].
     */
    var recyclerView: RecyclerView? = null

    // endregion

    // region RecyclerView.Adapter

    /**
     * {@inheritDoc}
     */
    override fun getItemCount(): Int = data.size

    /**
     * {@inheritDoc}
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = viewHolderFactory(parent, getDataBinder(viewType).layout)

    /**
     * {@inheritDoc}
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IBaseViewHolder) {
            holder.onBindViewHolder()
        }

        val item = get(position)

        onItemClick?.let { holder.itemView.setOnClickListener { it(item, it, position) } }

        onFocusChange?.let { holder.itemView.setOnFocusChangeListener { v, hasFocus -> it(item, v, hasFocus, position) } }

        getPresenterAt(position).bindViewHolder(holder, item, position)
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
        this.recyclerView = recyclerView
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

    // region Presenter

    /**
     * {@inheritDoc}
     *
     * Returns position of concrete [Presenter] at adapter position.
     *
     * @param position Adapter position.
     * @return [Presenter] position. Returns `-1` if there is none to be found.
     */
    override fun getItemViewType(position: Int) = binderType.indices.firstOrNull { equalsTo(data[position].second, binderType[it]::class.java) }
        ?: -1

    /**
     * Returns a concrete [Presenter] based on view type.
     *
     * @param viewType [.getItemViewType]
     * @return Concrete [Presenter].
     */
    protected fun getDataBinder(viewType: Int): Presenter<T> = binderType[viewType]

    /**
     * Returns the position of the concrete [Presenter] at adapter position.
     *
     * @param position Adapter position.
     * @return [Presenter]
     */
    protected fun getPresenterAt(position: Int): Presenter<T> = binderType[getItemViewType(position)]

    // endregion


    /**
     * Allocates a concrete [Presenter] and adds it to the list once.
     *
     * @param clazz Concrete [Presenter] representing the view type.
     */
    protected fun <P : Presenter<T>> addIfNotExists(clazz: Class<P>) {
        if (binderType.any { equalsTo(it::class.java, clazz) })
            return

        val constructor = clazz.constructors[0] as Constructor<*>
        var instance: Presenter<T>? = null
        try {
            instance = constructor.newInstance() as Presenter<T>
            instance.adapter = this
            binderType.add(instance)
        } catch (e: Exception) {
            if (debug)
                e.printStackTrace()
        }

        if (instance == null)
            throw IllegalArgumentException(clazz.canonicalName + " has no constructor with parameter: " + javaClass.canonicalName)
    }

    /**
     * {@inheritDoc}
     */
    override fun <P : Presenter<T>> add(position: Int, item: T, clazz: Class<P>) {
        data.add(position, Pair(item, clazz))
        addIfNotExists(clazz)
    }

    /**
     * {@inheritDoc}
     */
    override fun <P : Presenter<T>> prepend(item: T, clazz: Class<P>) = add(0, item, clazz)

    /**
     * {@inheritDoc}
     */
    override fun <P : Presenter<T>> append(item: T, clazz: Class<P>) = add(itemCount, item, clazz)

    /**
     * {@inheritDoc}
     */
    override fun clear() {
        binderType.clear()
        data.clear()
        removeAllViews()
        notifyDataSetChanged()
    }

    /**
     * {@inheritDoc}
     */
    override fun removeAllViews() {
        recyclerView?.removeAllViews()
    }

    /**
     * {@inheritDoc}
     */
    override fun contains(item: T) = (0 until itemCount).any { get(it) == item }

    /**
     * {@inheritDoc}
     */
    override fun contains(item: T, comparator: (first: T, second: T) -> Boolean) = (0 until itemCount).any { comparator(get(it), item) }

    /**
     * {@inheritDoc}
     */
    override operator fun get(position: Int): T = data[position].first

    /**
     * {@inheritDoc}
     */
    override fun get(item: T, comparator: (first: T, second: T) -> Boolean): T? {
        for (it in 0 until itemCount) {
            val t = get(it)
            if (comparator(t, item))
                return t
        }
        return null
    }

    /**
     * {@inheritDoc}
     */
    override fun position(item: T): Int = (0 until itemCount).firstOrNull { get(it) == item }
        ?: -1

    /**
     * {@inheritDoc}
     */
    override fun position(item: T, comparator: (first: T, second: T) -> Boolean): Int = (0 until itemCount).firstOrNull { comparator(get(it), item) }
        ?: -1

    /**
     * {@inheritDoc}
     */
    override fun update(position: Int, item: T) = update(position, item, false)

    /**
     * {@inheritDoc}
     */
    override fun update(position: Int, item: T, notify: Boolean) {
        data[position] = Pair(item, data[position].second)
        if (notify)
            notifyItemChanged(position)
    }

    /**
     * {@inheritDoc}
     */
    override fun remove(position: Int) = remove(position, false)

    /**
     * {@inheritDoc}
     */
    override fun remove(position: Int, notify: Boolean) {
        data.removeAt(position)
        if (notify)
            notifyItemRemoved(position)
    }

    /**
     * {@inheritDoc}
     */
    override fun sortBy2(comparator: Comparator<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

  override  fun sortBy(comparator: Comparator<T>) {

//        data.sortWith{ Comparator { o1: T, o2: T ->  comparator.compare(o1) } }
//
//        Collections.sort(data, { left, right -> comparator.compare(left.first, right.first) })
    }


    /**
     * {@inheritDoc}
     */
    override fun <T : Comparable<*>> sort(adapter: PresenterAdapter<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal companion object {

        /**
         * Compares two classes by canonical name.
         *
         * @return `True` if they're equal.
         */
        internal fun equalsTo(first: Class<*>, second: Class<*>): Boolean = first.canonicalName == second.canonicalName
    }
}