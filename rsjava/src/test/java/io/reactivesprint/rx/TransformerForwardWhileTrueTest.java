package io.reactivesprint.rx;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.subjects.PublishSubject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/7/16.
 */
public class TransformerForwardWhileTrueTest extends TestCase {
    PublishSubject<Integer> subject;
    PublishSubject<Boolean> forwardSubject;
    final List<Integer> values = new ArrayList<>();
    boolean completed;
    Throwable receivedThrowable;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        subject = PublishSubject.create();
        forwardSubject = PublishSubject.create();
        completed = false;
        receivedThrowable = null;
        values.clear();

        subject.compose(new TransformerForwardWhileTrue<Integer>(forwardSubject))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        completed = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        receivedThrowable = e;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        values.add(integer);
                    }
                });
    }

    public void testValues() throws Exception {
        subject.onNext(1);
        assertThat(values).isEmpty();
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();

        forwardSubject.onNext(true);
        assertThat(values).isEmpty();
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();

        subject.onNext(2);
        assertThat(values).containsExactly(2);
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();

        forwardSubject.onNext(false);
        subject.onNext(3);
        assertThat(values).containsExactly(2);
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();

        subject.onCompleted();
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();
    }

    public void testErrorFromSource() throws Exception {
        forwardSubject.onNext(true);

        subject.onError(new RuntimeException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNotNull();
    }

    public void testErrorIfInactiveFromSource() throws Exception {
        subject.onError(new RuntimeException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();
    }

    public void testErrorFromForwardObservable() throws Exception {
        forwardSubject.onNext(true);

        forwardSubject.onError(new RuntimeException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNotNull();
    }

    public void testErrorIfInactiveFromForwardObservable() throws Exception {
        forwardSubject.onError(new RuntimeException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNotNull();
    }

    public void testCompleteFromSource() throws Exception {
        forwardSubject.onNext(true);

        subject.onCompleted();
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();
    }

    public void testCompleteIfInactiveFromSource() throws Exception {
        subject.onCompleted();
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();
    }

    public void testCompleteFromForwardObservable() throws Exception {
        forwardSubject.onNext(true);

        forwardSubject.onCompleted();
        assertThat(completed).isTrue();
        assertThat(receivedThrowable).isNull();
    }

    public void testCompleteIfInactiveFromForwardObservable() throws Exception {
        forwardSubject.onCompleted();
        assertThat(completed).isTrue();
        assertThat(receivedThrowable).isNull();
    }
}
