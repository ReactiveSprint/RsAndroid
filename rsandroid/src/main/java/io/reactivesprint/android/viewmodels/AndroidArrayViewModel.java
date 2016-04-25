package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;

import io.reactivesprint.viewmodels.ConstantArrayViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.viewmodels.IViewModel;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/14/16.
 * <p/>
 * <dl>
 * <dt><b>Parcel Written Fields:</b></dt>
 * <dd>{@link IViewModel#title()}</dd>
 * <dd>{@link IArrayViewModel#localizedEmptyMessage()}</dd>
 * <dd>{@link IArrayViewModel#getViewModels()}</dd>
 * </dl>
 */
public abstract class AndroidArrayViewModel<E extends IAndroidViewModel> extends ConstantArrayViewModel<E> implements IAndroidViewModel {
    //region Fields

    @Nullable
    private Context context;

    //endregion

    //region Constructors

    public AndroidArrayViewModel(@NonNull Context context, @NonNull Collection<E> viewModels) {
        super(viewModels);
        checkNotNull(context, "context");
        this.context = context.getApplicationContext();
        onContextSet(context);
    }

    @SuppressWarnings("unchecked")
    protected AndroidArrayViewModel(Parcel in) {
        title().setValue(in.readString());
        localizedEmptyMessage().setValue(in.readString());
        setViewModels(in.readArrayList(getArrayClassLoader()));
    }

    //endregion

    //region Properties

    protected abstract ClassLoader getArrayClassLoader();

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
        String title = title().getValue() == null ? null : title().getValue().toString();
        dest.writeString(title);

        String message = localizedEmptyMessage().getValue() == null ? null : localizedEmptyMessage().getValue().toString();
        dest.writeString(message);

        dest.writeList(getViewModels());
    }

    //endregion
}
