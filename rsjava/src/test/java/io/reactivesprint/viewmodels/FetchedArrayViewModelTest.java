package io.reactivesprint.viewmodels;

import junit.framework.TestCase;

import org.javatuples.Pair;

import java.util.Collection;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static io.reactivesprint.viewmodels.ArrayViewModelTest.generateViewModels;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/8/16.
 */
public class FetchedArrayViewModelTest extends TestCase {

    FetchedArrayViewModel<ViewModel> viewModel;
    PublishSubject<Pair<Integer, Collection<ViewModel>>> subject;
    boolean completed;
    Collection<ViewModel> receivedViewModels;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        subject = null;
        completed = false;

        receivedViewModels = null;

        viewModel = new FetchedArrayViewModel<>(new Func1<Integer, Observable<Pair<Integer, Collection<ViewModel>>>>() {
            @Override
            public Observable<Pair<Integer, Collection<ViewModel>>> call(Integer integer) {
                subject = PublishSubject.create();
                return subject;
            }
        });
    }

    Subscriber<Collection<ViewModel>> createSubscriber() {
        return new Subscriber<Collection<ViewModel>>() {
            @Override
            public void onCompleted() {
                completed = true;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Collection<ViewModel> viewModels) {
                receivedViewModels = viewModels;
            }
        };
    }

    public void testFetchWithNoPagination() throws Exception {
        Collection<ViewModel> viewModels = generateViewModels(4);

        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.isLoading().getValue()).isTrue();
        assertThat(viewModel.isRefreshing().getValue()).isTrue();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<Integer, Collection<ViewModel>>(null, viewModels));
        subject.onCompleted();

        assertThat(viewModel.getCount().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).hasSameElementsAs(viewModels);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.isLoading().getValue()).isFalse();
        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        completed = false;

        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.isLoading().getValue()).isTrue();
        assertThat(viewModel.isRefreshing().getValue()).isTrue();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<Integer, Collection<ViewModel>>(null, viewModels));
        subject.onCompleted();

        assertThat(viewModel.getCount().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).containsExactlyElementsOf(viewModels);
        assertThat(viewModel.isLoading().getValue()).isFalse();
        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();
    }

    public void testFetchWithPagination() throws Exception {
        Collection<ViewModel> viewModels = generateViewModels(4);
        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.isRefreshing().getValue()).isTrue();
        assertThat(viewModel.isLoading().getValue()).isTrue();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<>(1, viewModels));
        subject.onCompleted();

        assertThat(viewModel.getCount().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).hasSameElementsAs(viewModels);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.isLoading().getValue()).isFalse();
        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        /**
         * Fetching next page
         */
        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isLoading().getValue()).isTrue();
        assertThat(viewModel.isFetchingNextPage().getValue()).isTrue();

        viewModels = generateViewModels(4, 4);
        subject.onNext(new Pair<>(2, viewModels));
        subject.onCompleted();

        assertThat(viewModel.getCount().getValue()).isEqualTo(8);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.isLoading().getValue()).isFalse();
        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        /**
         * Fetch If Needed Command
         */
        viewModel.getFetchIfNeededCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isLoading().getValue()).isTrue();
        assertThat(viewModel.isFetchingNextPage().getValue()).isTrue();

        viewModels = generateViewModels(4, 8);
        subject.onNext(new Pair<>(3, viewModels));
        subject.onCompleted();

        assertThat(viewModel.getCount().getValue()).isEqualTo(12);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.isLoading().getValue()).isFalse();
        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        /**
         * Refresh
         */
        viewModel.getRefreshCommand().apply().subscribe(createSubscriber());
        viewModels = generateViewModels(4);
        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.isRefreshing().getValue()).isTrue();
        assertThat(viewModel.isLoading().getValue()).isTrue();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<>(1, viewModels));
        subject.onCompleted();

        assertThat(viewModel.getCount().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).hasSameElementsAs(viewModels);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.isLoading().getValue()).isFalse();
        assertThat(viewModel.isRefreshing().getValue()).isFalse();
        assertThat(viewModel.isFetchingNextPage().getValue()).isFalse();
    }
}
