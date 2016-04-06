package io.reactivesprint.android;

import com.trello.rxlifecycle.ActivityEvent;

import io.reactivesprint.viewmodels.ViewModelType;
import io.reactivesprint.views.IViewController;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents an {@link android.app.Activity} that implements {@link IViewController}
 */
public interface IActivity<VM extends ViewModelType> extends IViewController<VM> {
    Observable<ActivityEvent> getLifeCycle();
}
