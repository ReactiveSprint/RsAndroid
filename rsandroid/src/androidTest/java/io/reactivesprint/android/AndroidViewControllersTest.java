package io.reactivesprint.android;

import android.test.AndroidTestCase;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivesprint.android.viewmodels.AndroidArrayViewModel;
import io.reactivesprint.android.viewmodels.AndroidFetchedArrayViewModel;
import io.reactivesprint.android.viewmodels.AndroidViewModel;
import io.reactivesprint.android.views.AndroidViewControllers;
import io.reactivesprint.android.views.IActivity;
import io.reactivesprint.android.views.IFragment;
import io.reactivesprint.rx.Pair;
import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.viewmodels.ViewModelException;
import io.reactivesprint.views.IArrayViewController;
import io.reactivesprint.views.IFetchedArrayViewController;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ahmad Baraka on 4/8/Fragment6.
 */
@SuppressWarnings("unchecked")
public class AndroidViewControllersTest extends AndroidTestCase {
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
        AndroidViewControllers.bindTitle(activity, viewModel);
        viewModel.getTitle().setValue("Test");
        verify(activity).setTitle(null);
        verify(activity).setTitle("Test");
    }

    public void testBindLoadingActivity() throws Exception {
        PublishSubject<Boolean> loadingSubject = PublishSubject.create();
        IActivity<AndroidViewModel> activity = mockActivity(IActivity.class);
        viewModel.bindLoading(loadingSubject);
        AndroidViewControllers.bindLoading(activity, viewModel);

        loadingSubject.onNext(true);
        verify(activity).presentLoading(false);
        verify(activity).presentLoading(true);
    }

    public void testBindErrorsActivity() throws Exception {
        PublishSubject<IViewModelException> errorsSubject = PublishSubject.create();
        IActivity<AndroidViewModel> activity = mockActivity(IActivity.class);
        AndroidViewControllers.bindErrors(activity, viewModel);
        viewModel.bindErrors(errorsSubject);
        ViewModelException exception = new ViewModelException("TestException");

        errorsSubject.onNext(exception);

        verify(activity).presentError(exception);
    }

    public void testBindCountActivity() throws Exception {
        AndroidArrayViewModel<AndroidViewModel> arrayViewModel = new AndroidArrayViewModel<>(getContext(), generateViewModels(3));
        TestArrayActivity activity = mockActivity(TestArrayActivity.class);

        AndroidViewControllers.bindCount(activity, arrayViewModel);

        verify(activity).onDataSetChanged();
    }

    public void testBindLocalizedEmptyMessageActivity() throws Exception {
        AndroidArrayViewModel<AndroidViewModel> arrayViewModel = new AndroidArrayViewModel<>(getContext(), generateViewModels(3));
        TestArrayActivity activity = mockActivity(TestArrayActivity.class);

        AndroidViewControllers.bindLocalizedEmptyMessage(activity, arrayViewModel);

        arrayViewModel.getLocalizedEmptyMessage().setValue("TestEmptyMessage");
        verify(activity).setLocalizedEmptyMessage(null);
        verify(activity).setLocalizedEmptyMessage("TestEmptyMessage");
    }

    public void testBindRefreshingActivity() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new AndroidFetchedArrayViewModel<AndroidViewModel, Integer>(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                return Observable.create(new Observable.OnSubscribe<Pair<Integer, Collection<AndroidViewModel>>>() {
                    @Override
                    public void call(Subscriber<? super Pair<Integer, Collection<AndroidViewModel>>> subscriber) {
                        subscriber.onNext(new Pair<Integer, Collection<AndroidViewModel>>(null, generateViewModels(4)));
                    }
                });
            }
        };

        TestFetchedArrayActivity activity = mockActivity(TestFetchedArrayActivity.class);

        AndroidViewControllers.bindRefreshing(activity, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();

        verify(activity).presentRefreshing(false);
        verify(activity).presentRefreshing(true);
    }

    public void testBindFetchingNextPageActivity() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new AndroidFetchedArrayViewModel<AndroidViewModel, Integer>(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayActivity activity = mockActivity(TestFetchedArrayActivity.class);

        AndroidViewControllers.bindFetchingNextPage(activity, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(1, generateViewModels(4)));
        subject.onCompleted();

        arrayViewModel.getFetchCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(2, generateViewModels(4)));

        verify(activity, times(2)).presentFetchingNextPage(false);
        verify(activity).presentFetchingNextPage(true);
    }

    //endregion

    //region FragmentTests

    public void testBindTitleFragment() throws Exception {
        IFragment<AndroidViewModel> fragment = mockFragment(IFragment.class);
        AndroidViewControllers.bindTitle(fragment, viewModel);
        viewModel.getTitle().setValue("Test");
        verify(fragment).setTitle(null);
        verify(fragment).setTitle("Test");
    }

    public void testBindLoadingFragment() throws Exception {
        PublishSubject<Boolean> loadingSubject = PublishSubject.create();
        IFragment<AndroidViewModel> fragment = mockFragment(IFragment.class);
        viewModel.bindLoading(loadingSubject);
        AndroidViewControllers.bindLoading(fragment, viewModel);

        loadingSubject.onNext(true);
        verify(fragment).presentLoading(false);
        verify(fragment).presentLoading(true);
    }

    public void testBindErrorsFragment() throws Exception {
        PublishSubject<IViewModelException> errorsSubject = PublishSubject.create();
        IFragment<AndroidViewModel> fragment = mockFragment(IFragment.class);
        viewModel.bindErrors(errorsSubject);
        AndroidViewControllers.bindErrors(fragment, viewModel);
        ViewModelException exception = new ViewModelException("TestException");

        errorsSubject.onNext(exception);
        verify(fragment).presentError(exception);
    }

    public void testBindCountFragment() throws Exception {
        AndroidArrayViewModel<AndroidViewModel> arrayViewModel = new AndroidArrayViewModel<>(getContext(), generateViewModels(3));
        TestArrayFragment fragment = mockFragment(TestArrayFragment.class);

        AndroidViewControllers.bindCount(fragment, arrayViewModel);

        verify(fragment).onDataSetChanged();
    }

    public void testBindLocalizedEmptyMessageFragment() throws Exception {
        AndroidArrayViewModel<AndroidViewModel> arrayViewModel = new AndroidArrayViewModel<>(getContext(), generateViewModels(3));
        TestArrayFragment fragment = mockFragment(TestArrayFragment.class);

        AndroidViewControllers.bindLocalizedEmptyMessage(fragment, arrayViewModel);

        arrayViewModel.getLocalizedEmptyMessage().setValue("TestEmptyMessage");
        verify(fragment).setLocalizedEmptyMessage(null);
        verify(fragment).setLocalizedEmptyMessage("TestEmptyMessage");
    }

    public void testBindRefreshingFragment() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new AndroidFetchedArrayViewModel<AndroidViewModel, Integer>(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                return Observable.create(new Observable.OnSubscribe<Pair<Integer, Collection<AndroidViewModel>>>() {
                    @Override
                    public void call(Subscriber<? super Pair<Integer, Collection<AndroidViewModel>>> subscriber) {
                        subscriber.onNext(new Pair<Integer, Collection<AndroidViewModel>>(null, generateViewModels(4)));
                    }
                });
            }
        };

        TestFetchedArrayFragment fragment = mockFragment(TestFetchedArrayFragment.class);

        AndroidViewControllers.bindRefreshing(fragment, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();

        verify(fragment).presentRefreshing(false);
        verify(fragment).presentRefreshing(true);
    }

    public void testBindFetchingNextPageFragment() throws Exception {
        AndroidFetchedArrayViewModel<AndroidViewModel, Integer> arrayViewModel = new AndroidFetchedArrayViewModel<AndroidViewModel, Integer>(getContext()) {
            @Override
            protected Observable<Pair<Integer, Collection<AndroidViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };

        TestFetchedArrayFragment fragment = mockFragment(TestFetchedArrayFragment.class);

        AndroidViewControllers.bindFetchingNextPage(fragment, arrayViewModel);
        arrayViewModel.getRefreshCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(1, generateViewModels(4)));
        subject.onCompleted();

        arrayViewModel.getFetchCommand().apply().subscribe();
        subject.onNext(new Pair<Integer, Collection<AndroidViewModel>>(2, generateViewModels(4)));

        verify(fragment, times(2)).presentFetchingNextPage(false);
        verify(fragment).presentFetchingNextPage(true);
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
            viewModel.getTitle().setValue(Integer.toString(i + start));
            viewModels.add(viewModel);
        }

        return viewModels;
    }

    //endregion

    //region Test Interfaces
    /// Interfaces for generic insanity

    private interface TestArrayActivity extends IActivity<AndroidViewModel>, IArrayViewController<AndroidViewModel, AndroidArrayViewModel<AndroidViewModel>> {
    }

    private interface TestArrayFragment extends IFragment<AndroidViewModel>, IArrayViewController<AndroidViewModel, AndroidArrayViewModel<AndroidViewModel>> {
    }

    private interface TestFetchedArrayActivity extends IActivity<AndroidViewModel>,
            IFetchedArrayViewController<AndroidViewModel, AndroidFetchedArrayViewModel<AndroidViewModel, Integer>> {
    }

    private interface TestFetchedArrayFragment extends IFragment<AndroidViewModel>,
            IFetchedArrayViewController<AndroidViewModel, AndroidFetchedArrayViewModel<AndroidViewModel, Integer>> {
    }

    //endregion
}