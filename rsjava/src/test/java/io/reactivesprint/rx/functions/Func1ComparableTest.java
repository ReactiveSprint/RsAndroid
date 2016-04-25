package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/16/16.
 */
public class Func1ComparableTest extends TestCase {

    public void testGreaterThanOrEqual() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(Func1Comparator.GREATER_THAN_OR_EQUAL, 4);
        assertThat(func.call(5)).isTrue();
        assertThat(func.call(4)).isTrue();
        assertThat(func.call(3)).isFalse();
    }

    public void testGreaterThan() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(Func1Comparator.GREATER_THAN, 4);
        assertThat(func.call(5)).isTrue();
        assertThat(func.call(4)).isFalse();
        assertThat(func.call(3)).isFalse();
    }

    public void testEqual() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(Func1Comparator.EQUAL, 4);
        assertThat(func.call(5)).isFalse();
        assertThat(func.call(4)).isTrue();
        assertThat(func.call(3)).isFalse();
    }

    public void testLessThan() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(Func1Comparator.LESS_THAN, 4);
        assertThat(func.call(5)).isFalse();
        assertThat(func.call(4)).isFalse();
        assertThat(func.call(3)).isTrue();
    }

    public void testLessThanOrEqual() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(Func1Comparator.LESS_THAN_OR_EQUAL, 4);
        assertThat(func.call(5)).isFalse();
        assertThat(func.call(4)).isTrue();
        assertThat(func.call(3)).isTrue();
    }
}