package net.kibotu.android.recyclerviewpresenter.v2;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {

    T create();

    void add(T item);

    void add(List<T> item);

    void update(T item);

    void remove(T item);

    void remove(Predicate<T> match);
}