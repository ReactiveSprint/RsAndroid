package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class FuncNNotNullTest extends TestCase {

    public void testCall() throws Exception {
        FuncNNotNull func = FuncNNotNull.getInstance();
        assertThat(func.call("Test", "Test")).isTrue();
        assertThat(func.call("Test", null)).isFalse();
        assertThat(func.call(null, null)).isFalse();
    }
}
