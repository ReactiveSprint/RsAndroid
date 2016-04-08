package io.reactivesprint.rx;

/**
 * Created by Ahmad Baraka on 4/8/16.
 * <br />
 * Tuple of 2 elements.
 */
public class Pair<A, B> {
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
}
