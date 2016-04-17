package io.reactivesprint.android.viewmodels;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivesprint.android.models.IAndroidModel;
import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.viewmodels.ModelViewModel;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/14/16.
 * <p/>
 * <dl>
 * <dt><b>Parcel Written Fields:</b></dt>
 * <dd>{@link IViewModel#getTitle()}</dd>
 * <dd>{@link ModelViewModel#getModel()}</dd>
 * </dl>
 */
public class AndroidModelViewModel<M extends IAndroidModel> extends ModelViewModel<M> implements IAndroidViewModel {
    //region Fields

    @Nullable
    private Context context;

    //endregion

    //region Constructors

    public AndroidModelViewModel(@NonNull Context context, @NonNull M model) {
        super(model);
        checkNotNull(context, "context");
        this.context = context.getApplicationContext();
        onContextSet(context);
    }

    protected AndroidModelViewModel(Parcel in) {
        super();
        getTitle().setValue(in.readString());
        M model = in.readParcelable(getModelClassLoader());
        setModel(model);
    }

    //endregion

    //region Properties

    protected ClassLoader getModelClassLoader() {
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

    public static Creator<AndroidModelViewModel> CREATOR = new Creator<AndroidModelViewModel>() {
        @Override
        public AndroidModelViewModel createFromParcel(Parcel source) {
            return new AndroidModelViewModel(source);
        }

        @Override
        public AndroidModelViewModel[] newArray(int size) {
            return new AndroidModelViewModel[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTitle().getValue().toString());
        dest.writeParcelable(getModel(), flags);
    }

    //endregion
}
