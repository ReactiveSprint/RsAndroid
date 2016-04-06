package io.reactivesprint.rx;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Ahmad Baraka on 4/6/16.
 */
public class CommandTest extends TestCase {

    Subscriber<? super String> subscriber;
    Command<Integer, String> command;
    MutableProperty<Boolean> enabled;
    int executionCount = 0;
    List<String> values;
    List<Throwable> errors;
    @SuppressWarnings("ThrowableInstanceNeverThrown")
    Throwable testError = new RuntimeException("TestError");

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        values = new ArrayList<>();
        enabled = new MutableProperty<>(false);
        errors = new ArrayList<>();

        command = new Command<>(enabled, new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(final Integer integer) {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        executionCount++;

                        CommandTest.this.subscriber = subscriber;

                        if (integer % 2 == 0) {
                            subscriber.onNext(integer.toString());
                            subscriber.onNext(integer.toString() + integer.toString());
                        } else {
                        }
                    }
                });
            }
        });


        command.getValues().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                values.add(s);
            }
        });

        command.getErrors().subscribe(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                errors.add(throwable);
            }
        });
    }

    public void testInitialState() throws Exception {
        assertThat(command.isExecuting().getValue()).isEqualTo(false);
        assertThat(command.isEnabled().getValue()).isEqualTo(false);
    }

    public void testShouldNotExecuteIfDisabled() throws Exception {
        command.apply(0).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                errors.add(throwable);
            }
        });

        assertThat(errors).isNotEmpty();
        assertThat(errors).hasOnlyElementsOfType(CommandNotEnabledException.class);
    }

    public void testEnabledProperty() throws Exception {
        enabled.setValue(true);
        assertThat(command.isEnabled().getValue()).isEqualTo(true);
        assertThat(command.isExecuting().getValue()).isEqualTo(false);

        enabled.setValue(false);
        assertThat(command.isEnabled().getValue()).isEqualTo(false);
        assertThat(command.isExecuting().getValue()).isEqualTo(false);
    }

    public void testExecution() throws Exception {
        enabled.setValue(true);

        final List<String> receivedValues = new ArrayList<>(2);
        command.apply(0).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                receivedValues.add(s);
            }
        });

        assertThat(executionCount).isEqualTo(1);
        assertThat(command.isExecuting().getValue()).isEqualTo(true);
        assertThat(command.isEnabled().getValue()).isEqualTo(false);

        assertThat(receivedValues).containsExactly("0", "00");
        assertThat(values).containsExactly("0", "00");
        assertThat(errors).isEmpty();

        subscriber.onCompleted();

        assertThat(command.isExecuting().getValue()).isEqualTo(false);
        assertThat(command.isEnabled().getValue()).isEqualTo(true);

        assertThat(values).containsExactly("0", "00");
        assertThat(errors).isEmpty();
    }

    public void testUnsubscribeShouldStopExecution() throws Exception {
        enabled.setValue(true);

        Subscription subscription = command.apply(0).subscribe();

        assertThat(executionCount).isEqualTo(1);
        assertThat(command.isExecuting().getValue()).isEqualTo(true);
        assertThat(command.isEnabled().getValue()).isEqualTo(false);

        subscription.unsubscribe();

        assertThat(command.isExecuting().getValue()).isEqualTo(false);
        assertThat(command.isEnabled().getValue()).isEqualTo(true);
    }

    public void testExecutionError() throws Exception {
        enabled.setValue(true);

        final Throwable[] receivedThrowable = new Throwable[1];

        command.apply(1).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                receivedThrowable[0] = throwable;
            }
        });

        assertThat(executionCount).isEqualTo(1);
        assertThat(command.isExecuting().getValue()).isEqualTo(true);
        assertThat(command.isEnabled().getValue()).isEqualTo(false);

        subscriber.onError(testError);

        assertThat(command.isExecuting().getValue()).isEqualTo(false);
        assertThat(command.isEnabled().getValue()).isEqualTo(true);

        assertThat(receivedThrowable).containsExactly(testError);

        assertThat(values).isEmpty();
        assertThat(errors).isNotEmpty();
        assertThat(errors).containsExactly(testError);
    }
}
