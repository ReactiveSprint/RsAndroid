package io.reactivesprint.rx;

/**
 * Created by Ahmad Baraka on 4/8/16.
 * <br />
 * Tuple of 2 elements.
 */
public final class Pair<A, B> {
    private A value0;
    private B value1;

    public Pair(A value0, B value1) {
        this.value0 = value0;
        this.value1 = value1;
    }

    public A getValue0() {
        return value0;
    }

    public B getValue1() {
        return value1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair<?, ?>) o;
        return (p.value0 == null ? value0 == null : p.value0.equals(value0))
                && (p.value1 == null ? value1 == null : p.value1.equals(value1));
    }

    @Override
    public int hashCode() {
        return (value0 == null ? 0 : value0.hashCode()) ^ (value1 == null ? 0 : value1.hashCode());
    }

    @Override
    public String toString() {
        return "Pair{" +
                "value0=" + value0 +
                ", value1=" + value1 +
                '}';
    }
}
