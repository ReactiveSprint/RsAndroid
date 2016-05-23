package io.reactivesprint.views;

import junit.framework.TestCase;

import java.util.Collection;

import io.reactivesprint.rx.Pair;
import io.reactivesprint.viewmodels.FetchedArrayViewModel;
import io.reactivesprint.viewmodels.ViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static io.reactivesprint.viewmodels.ArrayViewModelTest.generateViewModels;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Ahmad Baraka on 5/22/16.
 */
public class FetchedArrayViewBinderTest extends TestCase {
    FetchedArrayViewModel<ViewModel, Integer> viewModel;
    IFetchedArrayView<ViewModel, FetchedArrayViewModel<ViewModel, Integer>> view;
    IFetchedArrayViewBinder<ViewModel, FetchedArrayViewModel<ViewModel, Integer>> viewBinder;
    BehaviorSubject<Integer> lifecycleSubject;
    ILifecycleProvider<Integer> lifecycleProvider;
    PublishSubject<Pair<Integer, Collection<ViewModel>>> viewModelsSubject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //noinspection unchecked
        view = mock(IFetchedArrayView.class);
        viewModel = new FetchedArrayViewModel<ViewModel, Integer>() {
            @Override
            protected Observable<Pair<Integer, Collection<ViewModel>>> onFetch(Integer page) {
                viewModelsSubject = PublishSubject.create();
                return viewModelsSubject;
            }
        };
        when(view.getViewModel()).thenReturn(viewModel);
        lifecycleSubject = BehaviorSubject.create(0);
        //binding starts when 1 is sent, and stops when 3 is sent
        lifecycleProvider = LifecycleProviders.from(lifecycleSubject, 1, 3);
        //noinspection unchecked
        viewBinder = new FetchedArrayViewBinder(view, lifecycleProvider);
    }

    public void testBindViewModel() throws Exception {
        lifecycleSubject.onNext(1);

        verify(view, times(3)).getViewModel();
        verify(view).setTitle(null);
        verify(view).setLocalizedEmptyMessage(null);
        verify(view).setLocalizedEmptyMessageVisibility(true);
        verify(view).onDataSetChanged();
        verify(view).presentRefreshing(false);
        verify(view).presentFetchingNextPage(false);

        viewModel.getRefreshCommand().apply().subscribe();
        viewModelsSubject.onNext(new Pair<>(1, generateViewModels(3)));
        viewModelsSubject.onCompleted();

        verify(view).presentRefreshing(true);
        verify(view, times(2)).presentRefreshing(false);
        verify(view, times(2)).onDataSetChanged();
        verify(view).setLocalizedEmptyMessageVisibility(false);
        verify(view, times(2)).presentFetchingNextPage(false);

        viewModel.getFetchCommand().apply().subscribe();
        viewModelsSubject.onNext(new Pair<>(1, generateViewModels(3)));
        viewModelsSubject.onCompleted();

        //FIXME: Refresh and fetch commands re-set values
        //for refreshing and fetchingNextPage
        //this is unexpected and should be fixed.
        verify(view).presentFetchingNextPage(true);
        verify(view, times(3)).presentFetchingNextPage(false);
        verify(view, times(3)).presentRefreshing(false);
        verify(view, times(2)).setLocalizedEmptyMessageVisibility(false);
        verify(view, times(3)).onDataSetChanged();

        lifecycleSubject.onNext(3);

        viewModel.getRefreshCommand().apply().subscribe();

        verify(view, times(2)).presentLoading(true);
        verify(view, times(3)).presentLoading(false);

        verifyNoMoreInteractions(view);
    }
}