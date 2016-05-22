package io.reactivesprint.views;

import junit.framework.TestCase;

import io.reactivesprint.viewmodels.TestArrayViewModel;
import io.reactivesprint.viewmodels.ViewModel;
import rx.subjects.BehaviorSubject;

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
public class ArrayViewBinderTest extends TestCase {
    TestArrayViewModel viewModel;
    IArrayView<ViewModel, TestArrayViewModel> view;
    IArrayViewBinder<ViewModel, TestArrayViewModel, IArrayView<ViewModel, TestArrayViewModel>> viewBinder;
    BehaviorSubject<Integer> lifecycleSubject;
    LifecycleProvider<Integer> lifecycleProvider;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(IArrayView.class);
        viewModel = new TestArrayViewModel(generateViewModels(3));
        viewModel.localizedEmptyMessage().setValue("Test");
        when(view.getArrayViewModel()).thenReturn(viewModel);
        lifecycleSubject = BehaviorSubject.create(0);
        //binding starts when 1 is sent, and stops when 3 is sent
        lifecycleProvider = LifecycleProvider.from(lifecycleSubject, 1, 3);
        viewBinder = new ArrayViewBinder<>(view, lifecycleProvider);
    }

    public void testBindViewModel() throws Exception {
        lifecycleSubject.onNext(1);

        verify(view).getViewModel();
        verify(view).getArrayViewModel();
        verify(view).setLocalizedEmptyMessage("Test");
        verify(view).setLocalizedEmptyMessageVisibility(false);
        verify(view).onDataSetChanged();

        lifecycleSubject.onNext(2);
        viewModel.setViewModels(generateViewModels(0));
        verify(view).setLocalizedEmptyMessageVisibility(true);
        verify(view, times(2)).onDataSetChanged();

        lifecycleSubject.onNext(3);

        viewModel.setViewModels(generateViewModels(4));

        verifyNoMoreInteractions(view);
    }
}
