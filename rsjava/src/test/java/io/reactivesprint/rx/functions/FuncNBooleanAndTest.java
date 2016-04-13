package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class FuncNBooleanAndTest extends TestCase {

    public void testCall() throws Exception {
        FuncNBooleanAnd func = FuncNBooleanAnd.getInstance();
        assertThat(func.call(true, true, true)).isTrue();
        assertThat(func.call(true, false, true)).isFalse();
    }
}
