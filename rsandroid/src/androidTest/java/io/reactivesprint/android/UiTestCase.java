package io.reactivesprint.android;

import android.test.AndroidTestCase;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
//TODO: Use JUnit4 instead
public class UiTestCase extends AndroidTestCase {
    public void testOnUiThread(Func1<Action0, Action0> testAction) throws Exception {
        testOnUiThread(testAction, 100, TimeUnit.MILLISECONDS, 10, TimeUnit.MILLISECONDS);
    }

    public void testOnUiThread(final Func1<Action0, Action0> testFunc, final long timeOut,
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
