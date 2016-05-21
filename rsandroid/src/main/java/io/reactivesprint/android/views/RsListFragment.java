package io.reactivesprint.android.views;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.RxLifecycle;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IArrayViewModel;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IView;
import io.reactivesprint.views.IViewBinder;
import io.reactivesprint.views.ViewBinder;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Ahmad Baraka on 5/21/16.
 */
public abstract class RsListFragment<VM extends IAndroidViewModel, AVM extends IArrayViewModel<?> & IAndroidViewModel> extends ListFragment
        implements FragmentLifecycleProvider, IView<VM>, IArrayView<VM, AVM> {
    //region Fields

    private VM viewModel;

    private IViewBinder<VM, ? extends IView<VM>> viewBinder;

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    //endregion

    public RsListFragment() {
        viewBinder = onCreateViewBinder();
    }

    protected IViewBinder<VM, ? extends IView<VM>> onCreateViewBinder() {
        return new ViewBinder<>(this, AndroidLifecycleProvider.from(this, FragmentEvent.START));
    }

    //region LifeCycle

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    //endregion

    //region FragmentLifeCycleProvider

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindFragment(lifecycleSubject);
    }

    //endregion
}
