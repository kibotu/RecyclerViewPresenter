package net.kibotu.android.recyclerviewpresenter.v1;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class ViewModel<T> {

    public T model;
    public OnItemClickListener<ViewModel<T>> onItemClickListener;

    public ViewModel<T> setModel(T model) {
        this.model = model;
        return this;
    }

    public ViewModel<T> setOnItemClickListener(OnItemClickListener<ViewModel<T>> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewModel<?> viewModel = (ViewModel<?>) o;

        if (model != null ? !model.equals(viewModel.model) : viewModel.model != null) return false;
        return onItemClickListener != null ? onItemClickListener.equals(viewModel.onItemClickListener) : viewModel.onItemClickListener == null;

    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (onItemClickListener != null ? onItemClickListener.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ViewModel{" +
                "model=" + model +
                ", onItemClickListener=" + onItemClickListener +
                '}';
    }
}