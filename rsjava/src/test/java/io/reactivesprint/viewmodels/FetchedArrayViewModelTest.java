package io.reactivesprint.viewmodels;

import junit.framework.TestCase;

import java.util.Collection;

import io.reactivesprint.rx.Pair;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

import static io.reactivesprint.viewmodels.ArrayViewModelTest.generateViewModels;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/8/16.
 */
public class FetchedArrayViewModelTest extends TestCase {

    FetchedArrayViewModel<ViewModel, Integer> viewModel;
    PublishSubject<Pair<Integer, Collection<ViewModel>>> subject;
    boolean completed;
    Collection<ViewModel> receivedViewModels;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        subject = null;
        completed = false;

        receivedViewModels = null;

        viewModel = new FetchedArrayViewModel<ViewModel, Integer>() {
            @Override
            protected Observable<Pair<Integer, Collection<ViewModel>>> onFetch(Integer page) {
                subject = PublishSubject.create();
                return subject;
            }
        };
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

        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.refreshing().getValue()).isTrue();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<Integer, Collection<ViewModel>>(null, viewModels));
        subject.onCompleted();

        assertThat(viewModel.count().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).hasSameElementsAs(viewModels);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        completed = false;

        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.refreshing().getValue()).isTrue();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<Integer, Collection<ViewModel>>(null, viewModels));
        subject.onCompleted();

        assertThat(viewModel.count().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).containsExactlyElementsOf(viewModels);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();
    }

    public void testFetchWithPagination() throws Exception {
        Collection<ViewModel> viewModels = generateViewModels(4);
        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.refreshing().getValue()).isTrue();
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<>(1, viewModels));
        subject.onCompleted();

        assertThat(viewModel.count().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).hasSameElementsAs(viewModels);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        /**
         * Fetching next page
         */
        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.fetchingNextPage().getValue()).isTrue();

        viewModels = generateViewModels(4, 4);
        subject.onNext(new Pair<>(2, viewModels));
        subject.onCompleted();

        assertThat(viewModel.count().getValue()).isEqualTo(8);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        /**
         * Fetch If Needed Command
         */
        viewModel.getFetchIfNeededCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.fetchingNextPage().getValue()).isTrue();

        viewModels = generateViewModels(4, 8);
        subject.onNext(new Pair<>(3, viewModels));
        subject.onCompleted();

        assertThat(viewModel.count().getValue()).isEqualTo(12);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        /**
         * Refresh
         */
        viewModel.getRefreshCommand().apply().subscribe(createSubscriber());
        viewModels = generateViewModels(4);
        viewModel.getFetchCommand().apply().subscribe(createSubscriber());

        assertThat(viewModel.refreshing().getValue()).isTrue();
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();

        subject.onNext(new Pair<>(1, viewModels));
        subject.onCompleted();

        assertThat(viewModel.count().getValue()).isEqualTo(4);
        assertThat(viewModel.getViewModels()).hasSameElementsAs(viewModels);
        assertThat(receivedViewModels).hasSameElementsAs(viewModels);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.refreshing().getValue()).isFalse();
        assertThat(viewModel.fetchingNextPage().getValue()).isFalse();
    }
}
