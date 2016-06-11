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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (value0 != null ? !value0.equals(pair.value0) : pair.value0 != null) {
            return false;
        }
        return value1 != null ? value1.equals(pair.value1) : pair.value1 == null;
    }

    @Override
    public int hashCode() {
        int result = value0 != null ? value0.hashCode() : 0;
        result = 31 * result + (value1 != null ? value1.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "value0=" + value0 +
                ", value1=" + value1 +
                '}';
    }
}
