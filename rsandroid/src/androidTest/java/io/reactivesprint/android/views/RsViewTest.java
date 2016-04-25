package io.reactivesprint.android.views;

import android.test.AndroidTestCase;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import io.reactivesprint.rx.Command;
import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.MutableProperty;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class RsViewTest extends AndroidTestCase {
    int calls;
    PublishSubject<Void> applySubject;
    MutableProperty<Boolean> enabledProperty;
    Button button;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        calls = 0;
        applySubject = null;
        enabledProperty = new MutableProperty<>(false);
        button = new Button(getContext());
    }

    public void testBindCommand() throws Exception {
        testOnUiThread(new Func1<Action0, Action0>() {
            @Override
            public Action0 call(final Action0 doneAction) {
                return new Action0() {
                    @Override
                    public void call() {
                        ICommand<Void, Void> command = new Command<>(enabledProperty, new Func1<Void, Observable<Void>>() {
                            @Override
                            public Observable<Void> call(Void aVoid) {
                                return Observable.defer(new Func0<Observable<Void>>() {
                                    @Override
                                    public Observable<Void> call() {
                                        calls++;
                                        applySubject = PublishSubject.create();
                                        return applySubject;
                                    }
                                });
                            }
                        });
                        Subscription subscription = RsView.bindCommand(button, command);

                        assertFalse(button.isEnabled());
                        assertEquals(0, calls);

                        enabledProperty.setValue(true);
                        assertTrue(button.isEnabled());
                        assertEquals(0, calls);

                        button.performClick();
                        assertFalse(button.isEnabled());
                        assertEquals(1, calls);

                        applySubject.onCompleted();
                        assertTrue(button.isEnabled());
                        assertEquals(1, calls);

                        button.performClick();
                        assertFalse(button.isEnabled());
                        assertEquals(2, calls);

                        applySubject.onCompleted();
                        assertTrue(button.isEnabled());
                        assertEquals(2, calls);

                        subscription.unsubscribe();
                        enabledProperty.setValue(false);
                        assertTrue(button.isEnabled());
                        assertEquals(2, calls);

                        button.performClick();
                        assertTrue(button.isEnabled());
                        assertEquals(2, calls);

                        doneAction.call();
                    }
                };
            }
        });
    }

    public void testBindCommandWithInput() throws Exception {

    }

    void testOnUiThread(Func1<Action0, Action0> testAction) throws Exception {
        testOnUiThread(testAction, 100, TimeUnit.MILLISECONDS, 10, TimeUnit.MILLISECONDS);
    }

    void testOnUiThread(final Func1<Action0, Action0> testFunc, final long timeOut,
                        final TimeUnit timeOutUnit, final long interval,
                        final TimeUnit intervalTimeUnit) throws Exception {
        assertNotNull(testFunc);
        assertNotNull(timeOutUnit);
        assertTrue(timeOut > 0);
        assertTrue(interval > 0);
        assertNotNull(intervalTimeUnit);

        final long intervalMilliSeconds = intervalTimeUnit.toMillis(interval);
        final long timeOutMilliSeconds = timeOutUnit.toMillis(timeOut);
        final boolean[] done = {false};
        long time = 0;

        Action0 doneAction = new Action0() {
            @Override
            public void call() {
                done[0] = true;
            }
        };

        AndroidSchedulers.mainThread().createWorker().schedule(testFunc.call(doneAction));

        while (!done[0] && time < timeOutMilliSeconds) {
            Thread.sleep(intervalMilliSeconds);
            time += intervalMilliSeconds;
        }

        assertTrue("waitUntilDone didn't complete execution. doneAction must be called." +
                " Waited for " + time + " milliseconds." +
                " Expected timeOut " + timeOut + " " + timeOutUnit.name(), done[0]);
    }
}