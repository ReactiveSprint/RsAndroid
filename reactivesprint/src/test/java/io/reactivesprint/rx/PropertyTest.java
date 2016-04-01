package io.reactivesprint.rx;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 3/31/16.
 */
public class PropertyTest extends TestCase {

    public void testGetValue() throws Exception {
        String initialValue = "InitialValue";
        MutableProperty<String> mutableProperty = new MutableProperty<>(initialValue);
        Property<String> property = new Property<>(mutableProperty);

        assertThat(property.getValue()).isEqualTo(initialValue);

        String subsequentValue = "SubsequentValue";
        mutableProperty.setValue(subsequentValue);

        assertThat(property.getValue()).isEqualTo(subsequentValue);
    }
}
