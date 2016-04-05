package io.reactivesprint.rx;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.ribot.assertjrx.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 3/31/16.
 */
public class MutablePropertyTest extends TestCase {
    private MutableProperty<String> property;
    private final String initialValue = "InitialValue";
    private final String subsequentValue = "SubsequentValue";
    private final String finalValue = "FinalValue";


    public void testGetValue() throws Exception {
        property = new MutableProperty<>(initialValue);
        assertThat(property.getValue())
                .isEqualTo(initialValue);
    }

    public void testGetObservable() throws Exception {
        property = new MutableProperty<>(initialValue);
        final List<String> sentValues = new ArrayList<>();

        property.getObservable().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                sentValues.add(s);
            }
        });

        assertEquals(1, sentValues.size());
        assertEquals(initialValue, sentValues.get(0));

        property.setValue(initialValue);
        assertEquals(2, sentValues.size());
        assertEquals(initialValue, sentValues.get(1));

        property.setValue(subsequentValue);
        assertEquals(3, sentValues.size());
        assertEquals(subsequentValue, sentValues.get(2));

        property.setValue(finalValue);
        assertEquals(4, sentValues.size());
        assertEquals(finalValue, sentValues.get(3));

        Observable<String> observable = property.getObservable();
        property = null;
        System.gc();
        assertThat(observable.toBlocking())
                .completes();
    }

    public void testBind() throws Exception {
        property = new MutableProperty<>(initialValue);

        Subject<String, String> subject = PublishSubject.create();

        assertThat(property.getValue()).isEqualTo(initialValue);

        property.bind(subject);

        assertThat(property.getValue()).isEqualTo(initialValue);

        subject.onNext(subsequentValue);

        assertThat(property.getValue()).isEqualTo(subsequentValue);

        subject.onNext(finalValue);

        assertThat(property.getValue()).isEqualTo(finalValue);
    }
}
