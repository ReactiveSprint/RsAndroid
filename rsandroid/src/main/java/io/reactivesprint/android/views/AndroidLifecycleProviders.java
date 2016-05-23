package io.reactivesprint.android.views;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import io.reactivesprint.android.viewmodels.IAndroidViewModel;
import io.reactivesprint.viewmodels.IViewModel;
import io.reactivesprint.views.ILifecycleProvider;
import rx.Observable;
import rx.functions.Func1;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 5/21/16.
 * <p/>
 * Utility class for creating {@link ILifecycleProvider}
 */
public final class AndroidLifecycleProviders {
    private AndroidLifecycleProviders() {
    }

    public static ILifecycleProvider<ActivityEvent> from(@NonNull final ActivityLifecycleProvider lifecycleProvider, @NonNull final ActivityEvent startEvent) {
        checkNotNull(lifecycleProvider, "lifecycleProvider");
        checkNotNull(startEvent, "startEvent");
        return new ILifecycleProvider<ActivityEvent>() {
            @Override
            public Observable<ActivityEvent> onStartBinding() {
                return lifecycleProvider.lifecycle()
                        .filter(new Func1<ActivityEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityEvent event) {
                                return startEvent.equals(event);
                            }
                        });
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return lifecycleProvider.bindToLifecycle();
            }
        };
    }

    public static ILifecycleProvider<FragmentEvent> from(@NonNull final FragmentLifecycleProvider lifecycleProvider, @NonNull final FragmentEvent startEvent) {
        checkNotNull(lifecycleProvider, "lifecycleProvider");
        checkNotNull(startEvent, "startEvent");
        return new ILifecycleProvider<FragmentEvent>() {
            @Override
            public Observable<FragmentEvent> onStartBinding() {
                return lifecycleProvider.lifecycle()
                        .filter(new Func1<FragmentEvent, Boolean>() {
                            @Override
                            public Boolean call(FragmentEvent event) {
                                return startEvent.equals(event);
                            }
                        });
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return lifecycleProvider.bindToLifecycle();
            }
        };
    }

    public static <VM extends IViewModel & IAndroidViewModel> ILifecycleProvider<VM> from(@NonNull final IAndroidViewHolder<VM> viewHolder) {
        checkNotNull(viewHolder, "viewHolder");
        return new ILifecycleProvider<VM>() {
            @Override
            public Observable<VM> onStartBinding() {
                return viewHolder.onViewRecycled();
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return new Observable.Transformer<T, T>() {
                    @Override
                    public Observable<T> call(Observable<T> source) {
                        return source.takeUntil(viewHolder.onViewRecycled());
                    }
                };
            }
        };
    }
}
