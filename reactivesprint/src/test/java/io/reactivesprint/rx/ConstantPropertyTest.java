package io.reactivesprint.rx;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.ribot.assertjrx.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 3/31/16.
 */
public class ConstantPropertyTest extends TestCase {
    private ConstantProperty<String> property;
    private final String initialValue = "InitialValue";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        property = new ConstantProperty<>(initialValue);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        property = null;
    }

    public void testGetValue() throws Exception {
        assertThat(property.getValue()).isEqualTo(initialValue);
    }

    public void testGetObservable() throws Exception {
        assertThat(property.getObservable().toBlocking())
                .completes()
                .emitsSingleValue(initialValue);
    }
}
