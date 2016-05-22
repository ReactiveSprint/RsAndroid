package io.reactivesprint.views;

import junit.framework.TestCase;

import io.reactivesprint.rx.Pair;
import io.reactivesprint.viewmodels.FetchedArrayViewModel;
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
@SuppressWarnings("unchecked")
public class FetchedArrayViewBinderTest extends TestCase {
    FetchedArrayViewModel viewModel;
    IFetchedArrayView view;
    IFetchedArrayViewBinder viewBinder;
    BehaviorSubject<Integer> lifecycleSubject;
    LifecycleProvider<Integer> lifecycleProvider;
    PublishSubject<Pair> viewModelsSubject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(IFetchedArrayView.class);
        viewModel = new FetchedArrayViewModel() {
            @Override
            protected Observable<Pair> onFetch(Object page) {
                viewModelsSubject = PublishSubject.create();
                return viewModelsSubject;
            }
        };
        when(view.getArrayViewModel()).thenReturn(viewModel);
        lifecycleSubject = BehaviorSubject.create(0);
        //binding starts when 1 is sent, and stops when 3 is sent
        lifecycleProvider = LifecycleProvider.from(lifecycleSubject, 1, 3);
        viewBinder = new FetchedArrayViewBinder(view, lifecycleProvider);
    }

    public void testBindViewModel() throws Exception {
        lifecycleSubject.onNext(1);

        verify(view).getViewModel();
        verify(view, times(2)).getArrayViewModel();
        verify(view).setLocalizedEmptyMessage(null);
        verify(view).setLocalizedEmptyMessageVisibility(true);
        verify(view).onDataSetChanged();
        verify(view).presentRefreshing(false);
        verify(view).presentFetchingNextPage(false);

        viewModel.getRefreshCommand().apply().subscribe();
        viewModelsSubject.onNext(new Pair(1, generateViewModels(3)));
        viewModelsSubject.onCompleted();

        verify(view).presentRefreshing(true);
        verify(view, times(2)).presentRefreshing(false);
        verify(view, times(2)).onDataSetChanged();
        verify(view).setLocalizedEmptyMessageVisibility(false);
        verify(view, times(2)).presentFetchingNextPage(false);

        viewModel.getFetchCommand().apply().subscribe();
        viewModelsSubject.onNext(new Pair(1, generateViewModels(3)));
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

        verifyNoMoreInteractions(view);
    }
}