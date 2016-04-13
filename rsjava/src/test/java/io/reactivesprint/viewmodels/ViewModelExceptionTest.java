package io.reactivesprint.viewmodels;

import junit.framework.TestCase;

import rx.Observable;
import rx.Subscriber;

import static uk.co.ribot.assertjrx.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/7/16.
 */
public class ViewModelExceptionTest extends TestCase {

    static final String EXCEPTION_NAME = "TestException";

    public void testMapErrorFunc() throws Exception {

        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onError(new NullPointerException("Test"));
            }
        }).onErrorResumeNext(ViewModelException.<Integer>mapErrorFunc(EXCEPTION_NAME));

        assertThat(observable.toBlocking()).failsWithError(ViewModelException.class);
    }
}
