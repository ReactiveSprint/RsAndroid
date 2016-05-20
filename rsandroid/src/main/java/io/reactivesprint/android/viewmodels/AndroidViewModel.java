package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.viewmodels.ViewModel;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/14/16.
 * <p/>
 * Implementation of {@link IAndroidViewModel} which expects
 * {@link Context} to be always set from {@link #AndroidViewModel(Context)}.
 * If {@link #AndroidViewModel(Parcel)} is used to construct an instance, use {@link #setContext(Context)}.
 * <p/>
 * It's an error to use an {@link AndroidViewModel} before {@link Context} is set.
 * <dl>
 * <dt><b>Parcel Written Fields:</b></dt>
 * <dd>{@link IViewModel#title()}</dd>
 * </dl>
 *
 * @see #onContextSet(Context)
 */
public class AndroidViewModel extends ViewModel implements IAndroidViewModel {
    //region Fields

    private Context context;

    //endregion

    //region Constructors

    /**
     * Constructs an instance with {@link Context}
     *
     * @param context {@link Context#getApplicationContext()} is always used to set the context.
     */
    public AndroidViewModel(@NonNull Context context) {
        checkNotNull(context, "context");
        this.context = context.getApplicationContext();
        onContextSet(context);
    }

    protected AndroidViewModel(Parcel in) {
        title().setValue(in.readString());
    }

    //endregion

    //region Properties

    @Override
    public final Context getContext() {
        return context;
    }

    /**
     * Sets {@code context} to the receiver.
     * <p/>
     * If {@link #getContext()} returns NonNull, this method does nothing.
     *
     * @param context {@link Context#getApplicationContext()} is always used to set the context.
     * @see #onContextSet(Context)
     */
    @Override
    public final void setContext(@NonNull Context context) {
        checkNotNull(context, "context");
        if (this.context != null) {
            return;
        }
        this.context = context.getApplicationContext();
        onContextSet(context);
    }

    /**
     * Invoked when a NonNull {@link Context} is set for first time.
     * <p/>
     * Use this method to initialize properties that require {@link Context} to be set.
     */
    @CallSuper
    protected void onContextSet(@NonNull Context context) {

    }

    //endregion

    //region Parcelable

    public static Creator<AndroidViewModel> CREATOR = new Creator<AndroidViewModel>() {
        @Override
        public AndroidViewModel createFromParcel(Parcel source) {
            return new AndroidViewModel(source);
        }

        @Override
        public AndroidViewModel[] newArray(int size) {
            return new AndroidViewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String title = title().getValue() == null ? null : title().getValue().toString();
        dest.writeString(title);
    }

    //endregion
}
