package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivesprint.viewmodels.FetchedArrayViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IFetchedArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/14/16.
 * <p/>
 * Implementation if {@link IFetchedArrayViewModel} for Android.
 * <p/>
 * <dl>
 * <dt><b>Parcel Written Fields:</b></dt>
 * <dd>{@link IViewModel#getTitle()}</dd>
 * <dd>{@link IArrayViewModel#getLocalizedEmptyMessage()}</dd>
 * <dd>{@link IArrayViewModel#getViewModels()}</dd>
 * <dd>{@link IFetchedArrayViewModel#getNextPage()}</dd>
 *
 * @param <P> Type of Page, must work with {@link Parcel#writeValue(Object)}
 */
public abstract class AndroidFetchedArrayViewModel<E extends IAndroidViewModel, P>
        extends FetchedArrayViewModel<E, P>
        implements IAndroidViewModel {
    //region Fields

    @Nullable
    private Context context;

    //endregion

    //region Constructors

    public AndroidFetchedArrayViewModel(@NonNull Context context) {
        super();
        checkNotNull(context, "context");
        this.context = context.getApplicationContext();
        onContextSet(context);
    }

    @SuppressWarnings("unchecked")
    protected AndroidFetchedArrayViewModel(Parcel in) {
        getTitle().setValue(in.readString());
        getLocalizedEmptyMessage().setValue(in.readString());
        setViewModels(in.readArrayList(getArrayClassLoader()));
        setNextPage((P) in.readValue(getPageClassLoader()));
    }

    //endregion

    //region Properties

    protected abstract ClassLoader getArrayClassLoader();

    protected ClassLoader getPageClassLoader() {
        return null;
    }

    @Nullable
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String title = getTitle().getValue() == null ? null : getTitle().getValue().toString();
        dest.writeString(title);

        String message = getLocalizedEmptyMessage().getValue() == null ? null : getLocalizedEmptyMessage().getValue().toString();
        dest.writeString(message);

        dest.writeList(getViewModels());

        dest.writeValue(getNextPage());
    }

    //endregion
}
