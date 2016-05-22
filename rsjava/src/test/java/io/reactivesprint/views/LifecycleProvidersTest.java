package io.reactivesprint.views;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by Ahmad Baraka on 5/22/16.
 */
public class LifecycleProvidersTest extends TestCase {
    List<Integer> actual;
    List<Integer> expected;
    BehaviorSubject<Integer> subject;
    ILifecycleProvider<Integer> provider;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        actual = new ArrayList<>();
        expected = new ArrayList<>();
        subject = BehaviorSubject.create(0);
        provider = LifecycleProviders.from(subject, 1, 3);
    }

    public void testStartBinding() throws Exception {
        provider.onStartBinding().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                actual.add(integer);
            }
        });

        assertEquals(expected, actual);

        subject.onNext(0);
        assertEquals(expected, actual);


        subject.onNext(1);
        expected.add(1);
        assertEquals(expected, actual);

        subject.onNext(1);
        assertEquals(expected, actual);

        subject.onNext(2);
        assertEquals(expected, actual);

        subject.onNext(3);
        assertEquals(expected, actual);

        subject.onNext(1);
        expected.add(1);
        assertEquals(expected, actual);

        subject.onNext(3);
        assertEquals(expected, actual);
    }

    public void testBindToLifeCycle() {
        PublishSubject<Integer> sequence = PublishSubject.create();

        sequence.compose(provider.<Integer>bindToLifecycle())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        actual.add(integer);
                    }
                });

        assertEquals(expected, actual);

        subject.onNext(0);
        assertEquals(expected, actual);

        subject.onNext(1);
        assertEquals(expected, actual);

        sequence.onNext(10);
        expected.add(10);
        assertEquals(expected, actual);

        subject.onNext(2);
        sequence.onNext(20);
        expected.add(20);
        assertEquals(expected, actual);

        subject.onNext(3);
        sequence.onNext(30);
        assertEquals(expected, actual);

        subject.onNext(1);
        sequence.onNext(40);
        assertEquals(expected, actual);
    }
}
