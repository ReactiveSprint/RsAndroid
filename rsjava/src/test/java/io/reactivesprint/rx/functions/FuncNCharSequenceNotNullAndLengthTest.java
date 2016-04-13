package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class FuncNCharSequenceNotNullAndLengthTest extends TestCase {
    FuncNCharSequenceNotNullAndLength func;

    public void testCall() throws Exception {
        func = new FuncNCharSequenceNotNullAndLength();
        assertThat(func.call("Test", "Test")).isTrue();
        assertThat(func.call("1", "1")).isTrue();
        assertThat(func.call("Test", "")).isFalse();
        assertThat(func.call(null, "Test")).isFalse();
        assertThat(func.call(null, null)).isFalse();
        assertThat(func.call("", "")).isFalse();
    }

    public void testCall10() throws Exception {
        func = new FuncNCharSequenceNotNullAndLength(4);
        assertThat(func.call("Test", "Test")).isTrue();
        assertThat(func.call("123", "Test")).isFalse();
        assertThat(func.call("Test", "")).isFalse();
        assertThat(func.call(null, "Test")).isFalse();
        assertThat(func.call(null, null)).isFalse();
        assertThat(func.call("", "")).isFalse();
    }
}
