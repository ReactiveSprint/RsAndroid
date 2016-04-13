package io.reactivesprint.rx.functions;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/13/16.
 */
public class Func1CharSequenceNotNullAndRegexTest extends TestCase {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    public void testCall() throws Exception {
        Func1CharSequenceNotNullAndRegex<String> func = new Func1CharSequenceNotNullAndRegex<>(EMAIL_REGEX);
        assertThat(func.call("test@test.com")).isTrue();
        assertThat(func.call("test")).isFalse();
        assertThat(func.call("12345")).isFalse();
        assertThat(func.call("test.com")).isFalse();
    }
}
