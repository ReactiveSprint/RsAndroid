package io.reactivesprint.android.views;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import io.reactivesprint.rx.IMutableProperty;
import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/22/16.
 */
public final class RsTextView {
    private RsTextView() {
        throw new AssertionError("No instances.");
    }

    public static void bindTextViewToStringProperty(@NonNull TextView textView,
                                                    @NonNull IMutableProperty<String> property,
                                                    boolean initialValue) {
        checkNotNull(textView, "textView");
        checkNotNull(property, "property");

        if (initialValue) {
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

    public static void bindTextViewToCharSequenceProperty(@NonNull TextView textView,
                                                          @NonNull IMutableProperty<CharSequence> property,
                                                          boolean initialValue) {
        checkNotNull(textView, "textView");
        checkNotNull(property, "property");

        if (initialValue) {
            textView.setText(property.getValue());
        }

        property.bind(RxTextView.textChanges(textView).takeUntil(RxView.detaches(textView)));
    }
}
