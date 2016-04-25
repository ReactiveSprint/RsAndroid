package io.reactivesprint.android.views;

import android.widget.Button;

import io.reactivesprint.android.UiTestCase;
import io.reactivesprint.rx.Command;
import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.MutableProperty;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class RsViewTest extends UiTestCase {
    int calls;
    Object sentValue;
    PublishSubject<Void> applySubject;
    MutableProperty<Boolean> enabledProperty;
    Button button;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        calls = 0;
        applySubject = null;
        sentValue = null;
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

    public void testBindCommandMap() throws Exception {
        testOnUiThread(new Func1<Action0, Action0>() {
            @Override
            public Action0 call(final Action0 doneAction) {
                return new Action0() {
                    @Override
                    public void call() {
                        ICommand<Integer, Void> command = new Command<>(enabledProperty, new Func1<Integer, Observable<Void>>() {
                            @Override
                            public Observable<Void> call(Integer integer) {
                                sentValue = integer;
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
                        Subscription subscription = RsView.bindCommand(button, command, new Func1<Void, Integer>() {
                            @Override
                            public Integer call(Void aVoid) {
                                if (sentValue == null) {
                                    return 1;
                                }
                                return (Integer) sentValue + 1;
                            }
                        });

                        assertFalse(button.isEnabled());
                        assertEquals(0, calls);

                        enabledProperty.setValue(true);
                        assertTrue(button.isEnabled());
                        assertEquals(0, calls);

                        button.performClick();
                        assertFalse(button.isEnabled());
                        assertEquals(1, calls);
                        assertEquals(1, sentValue);

                        applySubject.onCompleted();
                        assertTrue(button.isEnabled());
                        assertEquals(1, calls);
                        assertEquals(1, sentValue);

                        button.performClick();
                        assertFalse(button.isEnabled());
                        assertEquals(2, calls);
                        assertEquals(2, sentValue);

                        applySubject.onCompleted();
                        assertTrue(button.isEnabled());
                        assertEquals(2, calls);
                        assertEquals(2, sentValue);

                        subscription.unsubscribe();
                        enabledProperty.setValue(false);
                        assertTrue(button.isEnabled());
                        assertEquals(2, calls);
                        assertEquals(2, sentValue);

                        button.performClick();
                        assertTrue(button.isEnabled());
                        assertEquals(2, calls);
                        assertEquals(2, sentValue);

                        doneAction.call();
                    }
                };
            }
        });
    }
}
