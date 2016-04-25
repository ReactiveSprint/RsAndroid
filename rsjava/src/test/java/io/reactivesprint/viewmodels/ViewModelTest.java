package io.reactivesprint.viewmodels;

import junit.framework.TestCase;

import io.reactivesprint.rx.Command;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/7/16.
 */
public class ViewModelTest extends TestCase {
    ViewModel viewModel;
    final String TEST_TITLE = "TestTitle";
    @SuppressWarnings("ThrowableInstanceNeverThrown")
    final ViewModelException TEST_EXCEPTION = new ViewModelException("Test Exception");
    IViewModelException receivedThrowable = null;
    boolean completed = false;
    int calls = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        viewModel = new ViewModel(TEST_TITLE);
        receivedThrowable = null;
        completed = false;
        calls = 0;
    }

    public void testTitle() {
        assertThat(viewModel.title().getValue()).isEqualTo(TEST_TITLE);
    }

    Subscriber<IViewModelException> createErrorsSubscriber() {
        return new Subscriber<IViewModelException>() {
            @Override
            public void onCompleted() {
                completed = true;
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(IViewModelException e) {
                calls++;
                receivedThrowable = e;
            }
        };
    }

    public void testBindErrorsFromObservable() throws Exception {
        PublishSubject<IViewModelException> subject = PublishSubject.create();

        viewModel.errors().subscribe(createErrorsSubscriber());

        viewModel.bindErrors(subject);

        assertThat(receivedThrowable).isNull();

        subject.onNext(TEST_EXCEPTION);

        assertThat(receivedThrowable).isSameAs(TEST_EXCEPTION);
        assertThat(calls).isEqualTo(1);
        assertThat(completed).isFalse();

        subject.onCompleted();
        assertThat(completed).isFalse();
    }

    public void testBindErrorsFromObservable2() throws Exception {
        PublishSubject<IViewModelException> subject = PublishSubject.create();

        viewModel.bindErrors(subject);

        viewModel.errors().subscribe(createErrorsSubscriber());

        assertThat(receivedThrowable).isNull();

        subject.onNext(TEST_EXCEPTION);

        assertThat(receivedThrowable).isSameAs(TEST_EXCEPTION);
        assertThat(calls).isEqualTo(1);
        assertThat(completed).isFalse();

        subject.onCompleted();
        assertThat(completed).isFalse();
    }

    public void testBindErrorsFromCommand() throws Exception {
        Command<Integer, Integer> command = new Command<>(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.error(TEST_EXCEPTION);
            }
        });

        viewModel.errors().subscribe(createErrorsSubscriber());

        viewModel.bindCommand(command);

        assertThat(receivedThrowable).isNull();

        command.apply().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

        assertThat(calls).isEqualTo(1);
        assertThat(receivedThrowable).isSameAs(TEST_EXCEPTION);
        assertThat(completed).isFalse();
    }

    public void testBindLoadingFromObservable() throws Exception {
        final PublishSubject<Boolean> subject = PublishSubject.create();
        final PublishSubject<Boolean> otherSubject = PublishSubject.create();

        viewModel.bindLoading(subject);
        viewModel.bindLoading(otherSubject);

        //Binding should not affect initial state.
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();

        subject.onNext(true);
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();

        //Sending true again should no do anything
        subject.onNext(true);
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();

        //Sending false should stop loading
        subject.onNext(false);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();

        otherSubject.onNext(true);
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();

        //Sending false from first subject, should still be loading
        subject.onNext(false);
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();

        otherSubject.onNext(false);
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();
    }

    public void testBindLoadingFromObservableWithError() throws Exception {
        final PublishSubject<Boolean> subject = PublishSubject.create();

        viewModel.bindLoading(subject);

        subject.onNext(true);
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();

        //unexpected loading error should stop loading
        subject.onError(new RuntimeException());
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();
    }

    public void testBindLoadingFromObservableWithCompleted() throws Exception {
        final PublishSubject<Boolean> subject = PublishSubject.create();

        viewModel.bindLoading(subject);

        subject.onNext(true);
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();

        //unexpected loading onCompleted should stop loading
        subject.onCompleted();
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();
    }

    public void testBindLoadingFromCommand() throws Exception {
        final PublishSubject<Void> subject = PublishSubject.create();
        final PublishSubject<Void> otherSubject = PublishSubject.create();
        final Command<Void, Void> command = new Command<>(viewModel.enabled(), new Func1<Void, Observable<Void>>() {
            @Override
            public Observable<Void> call(Void aVoid) {
                return subject;
            }
        });

        final Command<Void, Void> otherCommand = new Command<Void, Void>(viewModel.enabled(), new Func1<Void, Observable<Void>>() {
            @Override
            public Observable<Void> call(Void aVoid) {
                return otherSubject;
            }
        });

        viewModel.bindCommand(command);
        viewModel.bindCommand(otherCommand);

        //after binding, nothing should change
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();
        assertThat(command.isEnabled().getValue()).isTrue();
        assertThat(otherCommand.isEnabled().getValue()).isTrue();

        command.apply().subscribe();
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();
        assertThat(command.isEnabled().getValue()).isFalse();
        assertThat(otherCommand.isEnabled().getValue()).isFalse();

        subject.onCompleted();
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();
        assertThat(command.isEnabled().getValue()).isTrue();
        assertThat(otherCommand.isEnabled().getValue()).isTrue();

        otherCommand.apply().subscribe();
        assertThat(viewModel.loading().getValue()).isTrue();
        assertThat(viewModel.enabled().getValue()).isFalse();
        assertThat(command.isEnabled().getValue()).isFalse();
        assertThat(otherCommand.isEnabled().getValue()).isFalse();

        otherSubject.onCompleted();
        assertThat(viewModel.loading().getValue()).isFalse();
        assertThat(viewModel.enabled().getValue()).isTrue();
        assertThat(command.isEnabled().getValue()).isTrue();
        assertThat(otherCommand.isEnabled().getValue()).isTrue();
    }
}
