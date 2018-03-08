package net.kibotu.android.recyclerviewpresenter;

class SynchronizedValue<T> {

    private T t = null;
    private final Object lock = new Object();

    public void set(T t) {
        synchronized (lock) {
            this.t = t;
        }
    }

    public T get() {
        synchronized (lock) {
            return t;
        }
    }

    @Override
    public String toString() {
        return "SynchronizedValue{" +
                "t=" + get() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SynchronizedValue<?> that = (SynchronizedValue<?>) o;

        if (t != null ? !t.equals(that.t) : that.t != null) return false;
        return lock != null ? lock.equals(that.lock) : that.lock == null;

    }

    @Override
    public int hashCode() {
        int result = t != null ? t.hashCode() : 0;
        result = 31 * result + (lock != null ? lock.hashCode() : 0);
        return result;
    }


}
