package io.reactivesprint.android.views;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.views.IViewController;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents an Android Fragment that implements {@link IViewController}
 */
public interface IFragment<VM extends IAndroidViewModel> extends IViewController<VM> {
    Observable<FragmentEvent> lifecycle();
}
