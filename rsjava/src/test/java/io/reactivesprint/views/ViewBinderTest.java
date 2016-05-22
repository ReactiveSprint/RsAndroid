package io.reactivesprint.views;

import junit.framework.TestCase;

import io.reactivesprint.viewmodels.ViewModel;
import rx.subjects.BehaviorSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Ahmad Baraka on 5/22/16.
 */
@SuppressWarnings("unchecked")
public class ViewBinderTest extends TestCase {
    ViewModel viewModel;
    IView<ViewModel> view;
    IViewBinder<ViewModel, IView<ViewModel>> viewBinder;
    BehaviorSubject<Integer> lifecycleSubject;
    LifecycleProvider<Integer> lifecycleProvider;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        view = mock(IView.class);
        viewModel = new ViewModel();
        when(view.getViewModel()).thenReturn(viewModel);
        lifecycleSubject = BehaviorSubject.create(0);
        //binding starts when 1 is sent, and stops when 3 is sent
        lifecycleProvider = LifecycleProvider.from(lifecycleSubject, 1, 3);
        viewBinder = new ViewBinder<>(view, lifecycleProvider);
    }

    public void testBindTitle() throws Exception {
        viewModel.title().setValue("Test");

        lifecycleSubject.onNext(1);
        verify(view).setTitle("Test");

        lifecycleSubject.onNext(2);
        viewModel.title().setValue("Test2");
        verify(view).setTitle("Test2");

        lifecycleSubject.onNext(3);

        viewModel.title().setValue("Test3");

        lifecycleSubject.onNext(1);
        verify(view, times(2)).presentLoading(false);
        verify(view, times(2)).getViewModel();

        verify(view).setTitle("Test3");
        viewModel.title().setValue("Test4");
        verify(view).setTitle("Test4");

        verifyNoMoreInteractions(view);
    }

    public void testBindLoading() throws Exception {

    }

    public void testBindErrors() throws Exception {

    }
}
