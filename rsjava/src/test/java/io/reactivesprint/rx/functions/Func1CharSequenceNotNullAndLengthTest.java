package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class Func1CharSequenceNotNullAndLengthTest extends TestCase {

    Func1CharSequenceNotNullAndLength<String> func;

    public void testCall() throws Exception {
        func = new Func1CharSequenceNotNullAndLength<>();
        assertThat(func.call("Test")).isTrue();
        assertThat(func.call("1")).isTrue();
        assertThat(func.call(null)).isFalse();
        assertThat(func.call("")).isFalse();
    }

    public void testCall10() throws Exception {
        func = new Func1CharSequenceNotNullAndLength<>(4);
        assertThat(func.call("Test")).isTrue();
        assertThat(func.call(null)).isFalse();
        assertThat(func.call("")).isFalse();
        assertThat(func.call("123")).isFalse();
    }
}
