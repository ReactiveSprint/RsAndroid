package io.reactivesprint.android;

import com.trello.rxlifecycle.FragmentEvent;

import io.reactivesprint.viewmodels.ViewModelType;
import io.reactivesprint.views.IViewController;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * Represents an Android Fragment that implements {@link IViewController}
 */
public interface IFragment<VM extends ViewModelType> extends IViewController<VM> {
    Observable<FragmentEvent> getLifeCycle();
}
