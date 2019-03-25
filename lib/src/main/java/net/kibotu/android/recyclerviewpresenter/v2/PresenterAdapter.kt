package net.kibotu.android.recyclerviewpresenter.v2

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Constructor

open class PresenterAdapter<T : RecyclerViewModel<*>> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Actual data containing {@link T} and it's {@link Presenter} type.
     */
    protected val data: ArrayList<Pair<T, Class<*>>> = arrayListOf()

    /**
     * List of allocated concrete implementation and used [Presenter].
     */
    protected var binderType: ArrayList<Presenter<T>> = arrayListOf()

    // region Listener

    /**
     * Callback for [RecyclerView.ViewHolder.itemView.setOnClickListener].
     *
     * @param item  Model of the adapter.
     * @param view  View that has been clicked.
     * @param position Adapter position of the clicked element.
     */
    var onItemClick: ((item: T, view: View, position: Int) -> Unit)? = null

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
    var onFocusChange: ((item: T, view: View, hasFocus: Boolean, position: Int) -> Unit)? = null

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = RecyclerViewHolder(getDataBinder(viewType).layout, parent)

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
    override fun getItemViewType(position: Int) = binderType.indices.firstOrNull { data[position].second.classnameEqualTo(binderType[it].javaClass) }
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
     * Same as {@link #add(Object, Class)} except it adds at a specific index.
     *
     * @param position Adapter position.
     */
    fun <P : Presenter<T>> add(t: T, clazz: Class<P>, position: Int = 0) {
        data.add(position, Pair(t, clazz))
        addIfNotExists(clazz)
    }

    /**
     * Allocates a concrete [Presenter] and adds it to the list once.
     *
     * @param clazz Concrete [Presenter] representing the view type.
     */
    protected fun addIfNotExists(clazz: Class<out Presenter<T>>) {
        for (binderType in this.binderType)
            if (binderType::class.java.classnameEqualTo(clazz))
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
     * Returns [T] at adapter position.
     *
     * @param position Adapter position.
     * @return [T]
     */
    operator fun get(position: Int): T = data[position].first

    companion object {

        /**
         * Compares two classes by canonical name.
         *
         * @return `True` if they're equal.
         */
        internal fun Class<*>.classnameEqualTo(other: Class<*>): Boolean = this::class.java.canonicalName == other::class.java.canonicalName
    }

}