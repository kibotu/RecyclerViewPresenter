package net.kibotu.android.recyclerviewpresenter;

class UIDGenerator {

    public static final int START_UID = 0;
    public static final int INVALID_UID = START_UID - 1;

    private static final SynchronizedValue<Integer> nextUID;

    static {
        nextUID = new SynchronizedValue<>();
        nextUID.set(0);
    }

    public static int newUID() {
        if (!isValid(nextUID.get())) {
            throw new IllegalStateException("UID pool depleted");
        }
        nextUID.set(nextUID.get() + 1);
        return nextUID.get();
    }

    private static boolean isValid(final int uid) {
        return uid >= START_UID;
    }
}