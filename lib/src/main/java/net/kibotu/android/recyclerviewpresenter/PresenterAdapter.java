package net.kibotu.android.recyclerviewpresenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jan Rabe on 09/09/15.
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
     * Listener for when we have reached the end.
     */
    protected OnEndlessListener onEndlessListener;

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
     * <p>We take Also calls {@link BaseViewHolder#onBindViewHolder()}.</p>
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof BaseViewHolder)
            ((BaseViewHolder) viewHolder).onBindViewHolder();
        getPresenterAt(position).bindViewHolder(viewHolder, get(position), position);

        if (onEndlessListener != null && !reachedThreshold && position >= getItemCount() - getEndlessThreshold()) {
            reachedThreshold = true;
            onEndlessListener.onReachThreshold(this);
        }
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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    /**
     * {@inheritDoc}
     * <p>Also calls {@link BaseViewHolder#onBindViewHolder()}.</p>
     */
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        if (viewHolder instanceof BaseViewHolder)
            ((BaseViewHolder) viewHolder).onViewDetachedFromWindow();
    }

    /**
     * Indicator for when for the endless scroll event should be fired.
     *
     * @return {@link #endlessThreshold}
     */
    public int getEndlessThreshold() {
        return endlessThreshold;
    }

    /**
     * Sets the threshold when the endless scroll event should be fired.
     *
     * @param endlessThreshold {@link #endlessThreshold}
     */
    public void setEndlessThreshold(int endlessThreshold) {
        this.endlessThreshold = endlessThreshold;
    }

    /**
     * Gets event listener for endless scroll event.
     *
     * @return {@link #onEndlessListener}
     */
    public OnEndlessListener getOnEndlessListener() {
        return onEndlessListener;
    }

    /**
     * Sets event listener for endless scroll event.
     *
     * @return {@link #onEndlessListener}
     */
    public void setOnEndlessListener(OnEndlessListener onEndlessListener) {
        this.onEndlessListener = onEndlessListener;
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
        if (item == null)
            return;

        data.set(position, new Pair<>(item, data.get(position).second));
        notifyItemChanged(position);
    }
}