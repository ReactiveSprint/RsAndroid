package io.reactivesprint.android.views;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import io.reactivesprint.rx.IMutableProperty;
import rx.Observable;
import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/22/16.
 * <p/>
 * Static methods to bind {@link TextView} to {@link IMutableProperty}
 */
public final class RsTextView {
    private RsTextView() {
        throw new AssertionError("No instances.");
    }

    /**
     * Binds text changes from {@code textView} to {@code property}.
     * <br />
     * Binding ends when {@link RxView#detaches(View)} sends value or as stated at
     * {@link IMutableProperty#bind(Observable)}.
     *
     * @param textView        TextView to observe its changed.
     * @param property        Property to be set with text changes.
     * @param setInitialValue If true, {@code textView} will be set with value of {@code property}
     */
    public static void bindTextViewToStringProperty(@NonNull TextView textView,
                                                    @NonNull IMutableProperty<String> property,
                                                    boolean setInitialValue) {
        checkNotNull(textView, "textView");
        checkNotNull(property, "property");

        if (setInitialValue) {
            textView.setText(property.getValue());
        }

        property.bind(RxTextView.textChanges(textView).map(new Func1<CharSequence, String>() {
            @Override
            public String call(CharSequence charSequence) {
                if (charSequence != null) {
                    return charSequence.toString();
                }
                return null;
            }
        }).takeUntil(RxView.detaches(textView)));
    }

    /**
     * Binds text changes from {@code textView} to {@code property}.
     * <br />
     * Binding ends when {@link RxView#detaches(View)} sends value or as stated at
     * {@link IMutableProperty#bind(Observable)}.
     *
     * @param textView        TextView to observe its changed.
     * @param property        Property to be set with text changes.
     * @param setInitialValue If true, {@code textView} will be set with value of {@code property}
     */
    public static void bindTextViewToCharSequenceProperty(@NonNull TextView textView,
                                                          @NonNull IMutableProperty<CharSequence> property,
                                                          boolean setInitialValue) {
        checkNotNull(textView, "textView");
        checkNotNull(property, "property");

        if (setInitialValue) {
            textView.setText(property.getValue());
        }

        property.bind(RxTextView.textChanges(textView).takeUntil(RxView.detaches(textView)));
    }
}
