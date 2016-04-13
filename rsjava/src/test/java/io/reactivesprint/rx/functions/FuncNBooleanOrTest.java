package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class FuncNBooleanOrTest extends TestCase {

    public void testCall() throws Exception {
        FuncNBooleanOr func = FuncNBooleanOr.getInstance();
        assertThat(func.call(true, true, true)).isTrue();
        assertThat(func.call(true, false, true)).isTrue();
        assertThat(func.call(false, false, true)).isTrue();
        assertThat(func.call(false, false, false)).isFalse();
    }
}
