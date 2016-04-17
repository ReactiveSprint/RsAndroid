package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/16/16.
 */
public class Func1ComparableTest extends TestCase {

    public void testCall() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(4, 0);
        assertThat(func.call(4)).isTrue();
        assertThat(func.call(3)).isFalse();
    }

    public void testCall2() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(4, -1);
        assertThat(func.call(5)).isTrue();
        assertThat(func.call(4)).isFalse();
        assertThat(func.call(3)).isFalse();
    }

    public void testCall3() throws Exception {
        Func1Comparable<Integer> func = new Func1Comparable<>(4, 1);
        assertThat(func.call(5)).isFalse();
        assertThat(func.call(4)).isFalse();
        assertThat(func.call(3)).isTrue();
    }
}