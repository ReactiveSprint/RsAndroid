package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class Func1NotNullTest extends TestCase {

    public void testCall() throws Exception {
        Func1NotNull<String> func = Func1NotNull.getInstance();
        assertThat(func.call("Test")).isTrue();
        assertThat(func.call(null)).isFalse();
    }
}
