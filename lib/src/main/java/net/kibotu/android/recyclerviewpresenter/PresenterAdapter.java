package net.kibotu.android.recyclerviewpresenter;

import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import net.kibotu.android.recyclerviewpresenter.v2.IBaseViewHolder;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class PresenterAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Actual data containing {@link T} and it's {@link Presenter} type.
     */
    @NonNull
    protected final ArrayList<Pair<T, Class>> data;

    /**
     * Reference to the {@link OnItemClickListener}.
     */
    @Nullable
    protected OnItemClickListener<T> onItemClickListener;

    /**
     * Reference to the {@link OnItemFocusChangeListener}.
     */
    @Nullable
    protected OnItemFocusChangeListener<T> onItemFocusChangeListener;

    /**
     * Reference to the {@link View.OnKeyListener}.
     */
    @Nullable
    protected View.OnKeyListener onKeyListener;

    /**
     * List of allocated concrete implementation and used {@link Presenter}.
     */
    @NonNull
    protected List<Presenter<T, ? extends RecyclerView.ViewHolder>> binderType;

    /**
     * Reference to the bound {@link RecyclerView}.
     */
    public RecyclerView recyclerView;

    /**
     * Indicator for when endless scrolling event should be thrown.
     */
    protected int endlessThreshold = 1;

    /**
     * Indicator of whether the endless scroll threshold has been reached.
     */
    protected boolean reachedThreshold;

    /**
     * Constructs the Adapter.
     */
    public PresenterAdapter() {
        this.data = new ArrayList<>();
        this.binderType = new ArrayList<>();
    }

    /**
     * Getter for {@link OnItemClickListener}.
     *
     * @return {@link OnItemClickListener}
     */
    @Nullable
    public OnItemClickListener<T> getOnItemClickListener() {
        return onItemClickListener;
    }

    /**
     * Setter for {@link OnItemClickListener}.
     *
     * @param onItemClickListener {@link OnItemClickListener}
     */
    public void setOnItemClickListener(@Nullable final OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Getter for {@link OnItemFocusChangeListener}.
     *
     * @return {@link OnItemFocusChangeListener}
     */
    @Nullable
    public OnItemFocusChangeListener<T> getOnItemFocusChangeListener() {
        return onItemFocusChangeListener;
    }

    /**
     * Setter for {@link OnItemFocusChangeListener}.
     *
     * @param onItemFocusChangeListener {@link OnItemFocusChangeListener}
     */
    public void setOnItemFocusChangeListener(@Nullable final OnItemFocusChangeListener<T> onItemFocusChangeListener) {
        this.onItemFocusChangeListener = onItemFocusChangeListener;
    }

    /**
     * Getter for {@link View.OnKeyListener}.
     *
     * @return {@link View.OnKeyListener}
     */
    @Nullable
    public View.OnKeyListener getOnKeyListener() {
        return onKeyListener;
    }

    /**
     * Setter for {@link View.OnKeyListener }.
     *
     * @param onKeyListener {@link View.OnKeyListener}
     */
    public void setOnKeyListener(@Nullable final View.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>Except this time we use viewType to take the concrete implementation of the {@link Presenter}.</p>
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return getDataBinder(viewType).onCreateViewHolder(parent);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>We take Also calls {@link IBaseViewHolder#onBindViewHolder()}.</p>
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof IBaseViewHolder)
            ((IBaseViewHolder) viewHolder).onBindViewHolder();
        getPresenterAt(position).bindViewHolder(viewHolder, get(position), position);
    }

    /**
     * Same as {@link #add(Object, Class)} except it adds at a specific index.
     *
     * @param index Adapter position.
     */
    public void add(final int index, @NonNull final T t, @NonNull final Class clazz) {
        data.add(index, new Pair<>(t, clazz));
        addIfNotExists(clazz);
    }

    /**
     * Adds {@link T} at the end of the adapter list and linking a concrete Presenter
     *
     * @param t     {@link T} as model.
     * @param clazz Concrete {@link Presenter} type.
     */
    public void add(@NonNull final T t, @NonNull final Class clazz) {
        data.add(new Pair<>(t, clazz));
        addIfNotExists(clazz);
    }

    /**
     * Allocates a concrete {@link Presenter} and adds it to the list once.
     *
     * @param clazz Concrete {@link Presenter} representing the view type.
     */
    @SuppressWarnings("unchecked")
    protected void addIfNotExists(@NonNull final Class clazz) {
        for (final Presenter<T, ? extends RecyclerView.ViewHolder> binderType : this.binderType)
            if (ClassExtensions.equals(binderType.getClass(), clazz))
                return;

        final Constructor<T> constructor = (Constructor<T>) clazz.getConstructors()[0];
        Presenter<T, ? extends RecyclerView.ViewHolder> instance = null;
        try {
            instance = (Presenter<T, ? extends RecyclerView.ViewHolder>) constructor.newInstance(this);
            binderType.add(instance);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (instance == null)
            throw new IllegalArgumentException(clazz.getCanonicalName() + " has no constructor with parameter: " + getClass().getCanonicalName());
    }

    /**
     * Returns {@link T} at adapter position.
     *
     * @param position Adapter position.
     * @return {@link T}
     */
    public T get(final int position) {
        return data.get(position).first;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns position of concrete {@link Presenter} at adapter position.
     * </p>
     *
     * @param position Adapter position.
     * @return {@link Presenter} position. Returns <code>-1</code> if there is none to be found.
     */
    @Override
    public int getItemViewType(final int position) {
        for (int i = 0; i < binderType.size(); ++i)
            if (ClassExtensions.equals(data.get(position).second, binderType.get(i).getClass()))
                return i;

        return -1;
    }

    /**
     * Returns a concrete {@link Presenter} based on view type.
     *
     * @param viewType {@link #getItemViewType(int)}
     * @return Concrete {@link Presenter}.
     */
    protected Presenter<T, ? extends RecyclerView.ViewHolder> getDataBinder(final int viewType) {
        return binderType.get(viewType);
    }

    /**
     * Returns the position of the concrete {@link Presenter} at adapter position.
     *
     * @param position Adapter position.
     * @return {@link Presenter}
     */
    protected Presenter /*<T, ? extends RecyclerView.ViewHolder>*/ getPresenterAt(final int position) {
        return binderType.get(getItemViewType(position));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * Clears the adapter and also removes cached views.
     * This is necessary otherwise different layouts will explode if you try to bind them to the wrong {@link RecyclerView.ViewHolder}.
     */
    public void clear() {
        binderType.clear();
        data.clear();
        removeAllViews();
        notifyDataSetChanged();
    }

    /**
     * Removes all Views from the {@link RecyclerView}.
     */
    public void removeAllViews() {
        if (recyclerView != null)
            recyclerView.removeAllViews();
    }

    /**
     * Used for logging purposes.
     */
    @NonNull
    public String tag() {
        return getClass().getSimpleName();
    }

    /**
     * {@inheritDoc}
     */
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    /**
     * {@inheritDoc}
     * <p>Also calls {@link IBaseViewHolder#onBindViewHolder()}.</p>
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        if (viewHolder instanceof IBaseViewHolder)
            ((IBaseViewHolder) viewHolder).onViewDetachedFromWindow();
    }

    /**
     * Retrieves {@link T} by using a {@link Comparator<T>}
     *
     * @param item       {@link T}
     * @param comparator Filter criteria.
     * @return First {@link T}.
     */
    public T getItemByComparator(@Nullable final T item, @NonNull final Comparator<T> comparator) {
        for (int i = 0; i < getItemCount(); ++i)
            if (comparator.compare(get(i), item) == 0)
                return get(i);
        return null;
    }

    /**
     * Returns  if adapter contains {@link T}
     *
     * @param item {@link T}
     * @return <code>true</code> if contained.
     */
    public boolean contains(@Nullable final T item) {
        for (int i = 0; i < getItemCount(); ++i)
            if (get(i).equals(item))
                return true;
        return false;
    }

    /**
     * Returns  if adapter contains {@link T}
     *
     * @param item {@link T}
     * @return <code>true</code> if contained.
     */
    public boolean contains(@Nullable final T item, @NonNull final Comparator<T> comparator) {
        for (int i = 0; i < getItemCount(); ++i)
            if (comparator.compare(get(i), item) == 0)
                return true;
        return false;
    }

    /**
     * Returns adapter position of {@link T}.
     *
     * @param item {@link T}
     * @return <code>-1</code> if not contained.
     */
    public int position(@Nullable final T item) {
        for (int i = 0; i < getItemCount(); ++i)
            if (get(i).equals(item))
                return i;
        return -1;
    }

    /**
     * Returns adapter position of {@link T}.
     *
     * @param item       {@link T}
     * @param comparator Filter criteria.
     * @return <code>-1</code> if not contained.
     */
    public int position(@Nullable final T item, @NonNull final Comparator<T> comparator) {
        for (int i = 0; i < getItemCount(); ++i)
            if (comparator.compare(get(i), item) == 0)
                return i;
        return -1;
    }

    /**
     * Updates a model {@link T} at adapter position.
     *
     * @param position Adapter position.
     * @param item     {@link T}
     */
    public void update(final int position, @Nullable final T item) {
        update(position, item);
    }

    /**
     * Updates a model {@link T} at adapter position.
     *
     * @param position Adapter position.
     * @param item     {@link T}
     */
    public void update(final int position, @Nullable final T item, boolean notify) {
        if (item == null)
            return;

        data.set(position, new Pair<>(item, data.get(position).second));
        if (notify)
            notifyItemChanged(position);
    }

    /**
     * Remove an item at adapter position.
     *
     * @param position Adapter position.
     */
    public void remove(final int position) {
        remove(position, true);
    }

    /**
     * Remove an item at adapter position.
     *
     * @param position Adapter position.
     */
    public void remove(final int position, boolean notify) {
        data.remove(position);
        if (notify)
            notifyItemRemoved(position);
    }

    /**
     * Sorting data.
     *
     * @param comparator - Criteria for sorting.
     */
    public void sortBy(Comparator<T> comparator) {
        Collections.sort(data, (left, right) -> comparator.compare(left.first, right.first));
    }

    /**
     * Default {@link Collections#sort(List)} sorting.
     *
     * @param adapter - Adapter where {@link T} requires to be implementing {@link Comparable}
     * @param <T>     - Model.
     */
    public static <T extends Comparable> void sort(PresenterAdapter<T> adapter) {
        Collections.sort(adapter.data, (o1, o2) -> o1.first.compareTo(o2.first));
    }

    @NonNull
    public ArrayList<Pair<T, Class>> getData() {
        return data;
    }
}