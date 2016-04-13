package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class Func1BooleanNotTest extends TestCase {

    public void testCall() throws Exception {
        Func1BooleanNot func = Func1BooleanNot.getInstance();
        assertThat(func.call(false)).isTrue();
        assertThat(func.call(true)).isFalse();
    }
}
