package io.reactivesprint.android.views;

import com.trello.rxlifecycle.ActivityEvent;

import io.reactivesprint.android.models.IAndroidModel;
import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.views.IViewController;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents an {@link android.app.Activity} that implements {@link IViewController}
 */
public interface IActivity<VM extends IAndroidViewModel> extends IViewController<VM> {
    Observable<ActivityEvent> lifecycle();
}
