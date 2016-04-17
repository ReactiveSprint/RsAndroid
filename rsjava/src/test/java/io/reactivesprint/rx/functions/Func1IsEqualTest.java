package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/17/16.
 */
public class Func1IsEqualTest extends TestCase {

    public void testCall() throws Exception {
        String test = "Test";
        Func1IsEqual<String> func = new Func1IsEqual<>(test);
        assertThat(func.call(test)).isTrue();
        assertThat(func.call("Test")).isTrue();
        assertThat(func.call("test2")).isFalse();
    }
}