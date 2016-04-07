package io.reactivesprint.rx;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.internal.operators.OperatorThrottleFirst;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/7/16.
 */
public class TransformerThrottleWhileFalseTest extends TestCase {
    PublishSubject<Integer> subject;
    PublishSubject<Boolean> throttleSubject;
    final List<Integer> values = new ArrayList<>();
    boolean completed;
    Throwable receivedThrowable;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        subject = PublishSubject.create();
        throttleSubject = PublishSubject.create();
        completed = false;
        receivedThrowable = null;
        values.clear();

        OperatorThrottleFirst<Integer> throttleOperator = new OperatorThrottleFirst<>(1, TimeUnit.SECONDS, Schedulers.computation());

        subject.compose(new TransformerThrottleWhileFalse<>(throttleSubject, throttleOperator))
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

        throttleSubject.onNext(true);
    }

    public void testValues() throws Exception {
        subject.onNext(1);
        assertThat(values).containsExactly(1);
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();

        throttleSubject.onNext(false);

        subject.onNext(2);
        subject.onNext(3);
        subject.onNext(4);
        assertThat(values).containsExactly(1, 2);
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();

        throttleSubject.onNext(true);

        subject.onNext(5);
        subject.onNext(6);
        assertThat(values).containsExactly(1, 2, 5, 6);
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isNull();
    }

    public void testCompleteFromSource() throws Exception {
        subject.onCompleted();
        assertThat(completed).isTrue();
        assertThat(receivedThrowable).isNull();
    }

    public void testThrottleCompleteFromSource() throws Exception {
        throttleSubject.onNext(false);
        subject.onCompleted();
        assertThat(completed).isTrue();
        assertThat(receivedThrowable).isNull();
    }

    public void testCompleteFromThrottleSubject() throws Exception {
        throttleSubject.onCompleted();
        assertThat(completed).isTrue();
        assertThat(receivedThrowable).isNull();
    }

    public void testThrottleCompleteFromThrottleSubject() throws Exception {
        throttleSubject.onNext(false);
        throttleSubject.onCompleted();
        assertThat(completed).isTrue();
        assertThat(receivedThrowable).isNull();
    }

    public void testErrorFromSource() throws Exception {
        subject.onError(new IllegalStateException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isExactlyInstanceOf(IllegalStateException.class);
    }

    public void testThrottleErrorFromSource() throws Exception {
        throttleSubject.onNext(false);
        subject.onError(new IllegalStateException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isExactlyInstanceOf(IllegalStateException.class);
    }

    public void testErrorFromThrottleSubject() throws Exception {
        throttleSubject.onError(new IllegalStateException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isExactlyInstanceOf(IllegalStateException.class);
    }

    public void testThrottleErrorFromThrottleSubject() throws Exception {
        throttleSubject.onNext(false);
        throttleSubject.onError(new IllegalStateException());
        assertThat(completed).isFalse();
        assertThat(receivedThrowable).isExactlyInstanceOf(IllegalStateException.class);
    }
}
