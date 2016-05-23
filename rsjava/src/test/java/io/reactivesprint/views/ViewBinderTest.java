package io.reactivesprint.views;

import junit.framework.TestCase;

import io.reactivesprint.viewmodels.IViewModelException;
import io.reactivesprint.viewmodels.ViewModel;
import io.reactivesprint.viewmodels.ViewModelException;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Ahmad Baraka on 5/22/16.
 */
public class ViewBinderTest extends TestCase {
    ViewModel viewModel;
    IView<ViewModel> view;
    IViewBinder<ViewModel> viewBinder;
    BehaviorSubject<Integer> lifecycleSubject;
    ILifecycleProvider<Integer> lifecycleProvider;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //noinspection unchecked
        view = mock(IView.class);
        viewModel = new ViewModel();
        when(view.getViewModel()).thenReturn(viewModel);
        lifecycleSubject = BehaviorSubject.create(0);
        //binding starts when 1 is sent, and stops when 3 is sent
        lifecycleProvider = LifecycleProviders.from(lifecycleSubject, 1, 3);
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
        PublishSubject<Boolean> loadingSubject = PublishSubject.create();
        viewModel.bindLoading(loadingSubject);

        lifecycleSubject.onNext(1);
        verify(view).presentLoading(false);
        verify(view).getViewModel();
        verify(view).setTitle(null);

        loadingSubject.onNext(true);
        verify(view).presentLoading(true);

        lifecycleSubject.onNext(3);
        loadingSubject.onNext(false);
        verifyNoMoreInteractions(view);
    }

    public void testBindErrors() throws Exception {
        PublishSubject<IViewModelException> errorsSubject = PublishSubject.create();
        viewModel.bindErrors(errorsSubject);
        ViewModelException exception = new ViewModelException("TestException");

        lifecycleSubject.onNext(1);
        errorsSubject.onNext(exception);

        verify(view).setTitle(null);
        verify(view).presentLoading(false);
        verify(view).getViewModel();
        verify(view).presentError(exception);

        lifecycleSubject.onNext(3);
        errorsSubject.onNext(new ViewModelException("Error"));
        verifyNoMoreInteractions(view);
    }
}
