package io.reactivesprint.viewmodels;

import junit.framework.TestCase;

import io.reactivesprint.rx.Command;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static org.assertj.core.api.Assertions.*;

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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        viewModel = new ViewModel(TEST_TITLE);
        receivedThrowable = null;
        completed = false;

        viewModel.getErrors().subscribe(new Action1<IViewModelException>() {
            @Override
            public void call(IViewModelException e) {
                receivedThrowable = e;
            }
        });

        viewModel.getErrors().subscribe(new Subscriber<IViewModelException>() {
            @Override
            public void onCompleted() {
                completed = true;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(IViewModelException e) {

            }
        });
    }

    public void testTitle() {
        assertThat(viewModel.getTitle().getValue()).isEqualTo(TEST_TITLE);
    }

    public void testBindErrorsFromObservable() throws Exception {
        PublishSubject<IViewModelException> subject = PublishSubject.create();

        viewModel.bindErrors(subject);

        assertThat(receivedThrowable).isNull();

        subject.onNext(TEST_EXCEPTION);

        assertThat(receivedThrowable).isSameAs(TEST_EXCEPTION);
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

        assertThat(receivedThrowable).isSameAs(TEST_EXCEPTION);
        assertThat(completed).isFalse();
    }
}
