package io.reactivesprint.android.views;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivesprint.android.UiTestCase;
import io.reactivesprint.rx.MutableProperty;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/25/16.
 */
public class RsTextViewTest extends UiTestCase {

    @SuppressLint("SetTextI18n")
    public void testBindTextViewToStringProperty() throws Exception {
        testOnUiThread(new Func1<Action0, Action0>() {
            @Override
            public Action0 call(final Action0 done) {
                return new Action0() {
                    @Override
                    public void call() {
                        MutableProperty<String> property = new MutableProperty<>("Test");
                        TextView textView = new EditText(getContext());

                        Subscription subscription = RsTextView.bindTextViewToStringProperty(textView, property, true);

                        assertEquals("Test", textView.getText().toString());

                        textView.setText("Test2");
                        assertEquals("Test2", property.getValue());

                        subscription.unsubscribe();

                        textView.setText("Test3");
                        assertEquals("Test2", property.getValue());

                        done.call();
                    }
                };
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void testBindTextViewToCharSequenceProperty() throws Exception {
        testOnUiThread(new Func1<Action0, Action0>() {
            @Override
            public Action0 call(final Action0 doneAction) {
                return new Action0() {
                    @Override
                    public void call() {

                        MutableProperty<CharSequence> property = new MutableProperty<>((CharSequence) "Test1");
                        TextView textView = new EditText(getContext());

                        Subscription subscription = RsTextView.bindTextViewToCharSequenceProperty(textView, property, true);

                        assertEquals("Test1", textView.getText().toString());

                        textView.setText("Test2");
                        assertEquals("Test2", property.getValue().toString());

                        subscription.unsubscribe();

                        textView.setText("Test3");
                        assertEquals("Test2", property.getValue().toString());

                        doneAction.call();
                    }
                };
            }
        });
    }
}
