package io.reactivesprint.android.views;

import android.test.AndroidTestCase;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivesprint.android.viewmodels.AndroidFetchedArrayViewModel;
import io.reactivesprint.android.viewmodels.AndroidViewModel;
import io.reactivesprint.android.viewmodels.TestAndroidArrayViewModel;
import io.reactivesprint.android.viewmodels.TestAndroidFetchedArrayViewModel;
import io.reactivesprint.rx.Pair;
import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.viewmodels.ViewModelException;
import io.reactivesprint.views.IArrayView;
import io.reactivesprint.views.IFetchedArrayView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Ahmad Baraka on 4/8/Fragment6.
 */
@SuppressWarnings("unchecked")
public class AndroidViewsTest extends AndroidTestCase {
    AndroidViewModel viewModel;
    Observable<ActivityEvent> activityEventObservable;
    Observable<FragmentEvent> fragmentEventObservable;
    PublishSubject<Pair<Integer, Collection<AndroidViewModel>>> subject;

    <T extends IActivity> T mockActivity(Class<T> aClass) {
        T activity = mock(aClass);
        when(activity.lifecycle()).thenReturn(activityEventObservable);
        return activity;
    }

    <T extends IFragment> T mockFragment(Class<T> aClass) {
        T fragment = mock(aClass);
        when(fragment.lifecycle()).thenReturn(fragmentEventObservable);
        return fragment;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        viewModel = new AndroidViewModel(getContext());
        subject = null;

        activityEventObservable = Observable.create(new Observable.OnSubscribe<ActivityEvent>() {
            @Override
            public void call(Subscriber<? super ActivityEvent> subscriber) {
                subscriber.onNext(ActivityEvent.CREATE);
            }
        });

        fragmentEventObservable = Observable.create(new Observable.OnSubscribe<FragmentEvent>() {
            @Override
            public void call(Subscriber<? super FragmentEvent> subscriber) {
                subscriber.onNext(FragmentEvent.CREATE);
            }
        });
    }

    //region ActivityTests

    public void testBindTitleActivity() throws Exception {
        IActivity<AndroidViewModel> activity = mockActivity(IActivity.class);
        Subscription subscription = AndroidViews.bindTitle(activity, viewModel);
        viewModel.title().setValue("Test");
        verify(activity).lifecycle();
        verify(activity).setTitle(null);
        verify(activity).setTitle("Test");

        subscription.unsubscribe();
        viewModel.title().setValue("Test2");
        verifyNoMoreInteractions(activity);
    }

    public void testBindLoadingActivity() throws Exception {
        PublishSubject<Boolean> loadingSubject = PublishSubject.create();
        IActivity<AndroidViewModel> activity = mockActivity(IActivity.class);
        viewModel.bindLoading(loadingSubject);
        Subscription subscription = AndroidViews.bindLoading(activity, viewModel);

        loadingSubject.onNext(true);
        verify(activity).lifecycle();
        verify(activity).presentLoading(false);
        verify(activity).presentLoading(true);

        subscription.unsubscribe();
        loadingSubject.onNext(false);
        verifyNoMoreInteractions(activity);
    }

    public void testBindErrorsActivity() throws Exception {
        PublishSubject<IViewModelException> errorsSubject = PublishSubject.create();
        IActivity<AndroidViewModel> activity = mockActivity(IActivity.class);
        Subscription subscription = AndroidViews.bindErrors(activity, viewModel);
        viewModel.bindErrors(errorsSubject);
        ViewModelException exception = new ViewModelException("TestException");

        errorsSubject.onNext(exception);

        verify(activity).lifecycle();
        verify(activity).presentError(exception);

        subscription.unsubscribe();
        errorsSubject.onNext(new ViewModelException("Error"));
        verifyNoMoreInteractions(activity);
    }

    public void testBindCountActivity() throws Exception {
        TestAndroidArrayViewModel arrayViewModel = new TestAndroidArrayViewModel(getContext(), generateViewModels(3));
        TestArrayActivity activity = mockActivity(TestArrayActivity.class);

        Subscription subscription = AndroidViews.bindCount(activity, arrayViewModel);

        verify(activity).lifecycle();
        verify(activity).onDataSetChanged();

        subscription.unsubscribe();
        arrayViewModel.setViewModels(generateViewModels(4));
        verifyNoMoreInteractions(activity);
    }

    public void testBindLocalizedEmptyMessageActivity() throws Exception {
        TestAndroidArrayViewModel arrayViewModel = new TestAndroidArrayViewModel(getContext(), generateViewModels(3));
        TestArrayActivity activity = mockActivity(TestArrayActivity.class);

        Subscription subscription = AndroidViews.bindLocalizedEmptyMessage(activity, arrayViewModel);

        arrayViewModel.localizedEmptyMessage().setValue("TestEmptyMessage");
        verify(activity).lifecycle();
        verify(activity).setLocalizedEmptyMessage(null);
        verify(activity).setLocalizedEmptyMessage("TestEmptyMessage");

        subscription.unsubscribe();
        arrayViewModel.localizedEmptyMessage().setValue("NotSent");
        verifyNoMoreInteractions(activity);
    }

    public void testBindLocalizedEmptyMessageVisibilityActivity() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new TestAndroidFetchedArrayViewModel(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayActivity activity = mockActivity(TestFetchedArrayActivity.class);

        Subscription subscription = AndroidViews.bindLocalizedEmptyMessageVisibility(activity, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onCompleted();

        verify(activity).lifecycle();
        verify(activity).setLocalizedEmptyMessageVisibility(true);

        subscription.unsubscribe();
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(0, generateViewModels(3)));
        subject.onCompleted();
        verifyNoMoreInteractions(activity);
    }

    public void testBindRefreshingActivity() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new TestAndroidFetchedArrayViewModel(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayActivity activity = mockActivity(TestFetchedArrayActivity.class);

        Subscription subscription = AndroidViews.bindRefreshing(activity, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();

        verify(activity).lifecycle();
        verify(activity).presentRefreshing(false);
        verify(activity).presentRefreshing(true);

        subscription.unsubscribe();
        subject.onCompleted();
        arrayViewModel.getRefreshCommand().apply().subscribe();
        verifyNoMoreInteractions(activity);
    }

    public void testBindFetchingNextPageActivity() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new TestAndroidFetchedArrayViewModel(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayActivity activity = mockActivity(TestFetchedArrayActivity.class);

        Subscription subscription = AndroidViews.bindFetchingNextPage(activity, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(1, generateViewModels(4)));
        subject.onCompleted();

        arrayViewModel.getFetchCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(2, generateViewModels(4)));

        verify(activity).lifecycle();
        verify(activity, times(2)).presentFetchingNextPage(false);
        verify(activity).presentFetchingNextPage(true);

        subscription.unsubscribe();
        subject.onCompleted();
        arrayViewModel.getFetchCommand().apply().subscribe();
        verifyNoMoreInteractions(activity);
    }

    //endregion

    //region FragmentTests

    public void testBindTitleFragment() throws Exception {
        IFragment<AndroidViewModel> fragment = mockFragment(IFragment.class);
        Subscription subscription = AndroidViews.bindTitle(fragment, viewModel);
        viewModel.title().setValue("Test");
        verify(fragment).lifecycle();
        verify(fragment).setTitle(null);
        verify(fragment).setTitle("Test");

        subscription.unsubscribe();
        viewModel.title().setValue("Test2");
        verifyNoMoreInteractions(fragment);
    }

    public void testBindLoadingFragment() throws Exception {
        PublishSubject<Boolean> loadingSubject = PublishSubject.create();
        IFragment<AndroidViewModel> fragment = mockFragment(IFragment.class);
        viewModel.bindLoading(loadingSubject);
        Subscription subscription = AndroidViews.bindLoading(fragment, viewModel);

        loadingSubject.onNext(true);
        verify(fragment).lifecycle();
        verify(fragment).presentLoading(false);
        verify(fragment).presentLoading(true);

        subscription.unsubscribe();
        loadingSubject.onNext(false);
        verifyNoMoreInteractions(fragment);
    }

    public void testBindErrorsFragment() throws Exception {
        PublishSubject<IViewModelException> errorsSubject = PublishSubject.create();
        IFragment<AndroidViewModel> fragment = mockFragment(IFragment.class);
        viewModel.bindErrors(errorsSubject);
        Subscription subscription = AndroidViews.bindErrors(fragment, viewModel);
        ViewModelException exception = new ViewModelException("TestException");

        errorsSubject.onNext(exception);
        verify(fragment).lifecycle();
        verify(fragment).presentError(exception);

        subscription.unsubscribe();
        errorsSubject.onNext(new ViewModelException("Error"));
        verifyNoMoreInteractions(fragment);
    }

    public void testBindCountFragment() throws Exception {
        TestAndroidArrayViewModel arrayViewModel = new TestAndroidArrayViewModel(getContext(), generateViewModels(3));
        TestArrayFragment fragment = mockFragment(TestArrayFragment.class);

        Subscription subscription = AndroidViews.bindCount(fragment, arrayViewModel);

        verify(fragment).lifecycle();
        verify(fragment).onDataSetChanged();

        subscription.unsubscribe();
        arrayViewModel.setViewModels(generateViewModels(4));
        verifyNoMoreInteractions(fragment);
    }

    public void testBindLocalizedEmptyMessageFragment() throws Exception {
        TestAndroidArrayViewModel arrayViewModel = new TestAndroidArrayViewModel(getContext(), generateViewModels(3));
        TestArrayFragment fragment = mockFragment(TestArrayFragment.class);

        Subscription subscription = AndroidViews.bindLocalizedEmptyMessage(fragment, arrayViewModel);

        arrayViewModel.localizedEmptyMessage().setValue("TestEmptyMessage");
        verify(fragment).lifecycle();
        verify(fragment).setLocalizedEmptyMessage(null);
        verify(fragment).setLocalizedEmptyMessage("TestEmptyMessage");

        subscription.unsubscribe();
        arrayViewModel.localizedEmptyMessage().setValue("NotSent");
        verifyNoMoreInteractions(fragment);
    }

    public void testBindLocalizedEmptyMessageVisibilityFragment() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new TestAndroidFetchedArrayViewModel(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayFragment fragment = mockFragment(TestFetchedArrayFragment.class);

        Subscription subscription = AndroidViews.bindLocalizedEmptyMessageVisibility(fragment, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onCompleted();

        verify(fragment).lifecycle();
        verify(fragment).setLocalizedEmptyMessageVisibility(true);

        subscription.unsubscribe();
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(0, generateViewModels(3)));
        subject.onCompleted();
        verifyNoMoreInteractions(fragment);
    }

    public void testBindRefreshingFragment() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new TestAndroidFetchedArrayViewModel(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayFragment fragment = mockFragment(TestFetchedArrayFragment.class);

        Subscription subscription = AndroidViews.bindRefreshing(fragment, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();

        verify(fragment).lifecycle();
        verify(fragment).presentRefreshing(false);
        verify(fragment).presentRefreshing(true);

        subscription.unsubscribe();
        subject.onCompleted();
        arrayViewModel.getFetchCommand().apply().subscribe();
        verifyNoMoreInteractions(fragment);
    }

    public void testBindFetchingNextPageFragment() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new TestAndroidFetchedArrayViewModel(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayFragment fragment = mockFragment(TestFetchedArrayFragment.class);

        Subscription subscription = AndroidViews.bindFetchingNextPage(fragment, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(1, generateViewModels(4)));
        subject.onCompleted();

        arrayViewModel.getFetchCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(2, generateViewModels(4)));

        verify(fragment).lifecycle();
        verify(fragment, times(2)).presentFetchingNextPage(false);
        verify(fragment).presentFetchingNextPage(true);

        subscription.unsubscribe();
        subject.onCompleted();
        arrayViewModel.getFetchCommand().apply().subscribe();
        verifyNoMoreInteractions(fragment);
    }

    //endregion

    //region Helpers

    public List<AndroidViewModel> generateViewModels(int count) {
        return generateViewModels(count, 0);
    }

    public List<AndroidViewModel> generateViewModels(int count, int start) {
        List<AndroidViewModel> viewModels = new ArrayList<>(count);

        for (int i = 1; i <= count; i++) {
            AndroidViewModel viewModel = new AndroidViewModel(getContext());
            viewModel.title().setValue(Integer.toString(i + start));
            viewModels.add(viewModel);
        }

        return viewModels;
    }

    //endregion

    //region Test Interfaces
    /// Interfaces for generic insanity

    private interface TestArrayActivity extends IActivity<AndroidViewModel>, IArrayView<AndroidViewModel, TestAndroidArrayViewModel> {
    }

    private interface TestArrayFragment extends IFragment<AndroidViewModel>, IArrayView<AndroidViewModel, TestAndroidArrayViewModel> {
    }

    private interface TestFetchedArrayActivity extends IActivity<AndroidViewModel>,
            IFetchedArrayView<AndroidViewModel, AndroidFetchedArrayViewModel<AndroidViewModel, Integer>> {
    }

    private interface TestFetchedArrayFragment extends IFragment<AndroidViewModel>,
            IFetchedArrayView<AndroidViewModel, AndroidFetchedArrayViewModel<AndroidViewModel, Integer>> {
    }

    //endregion
}
