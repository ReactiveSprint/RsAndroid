package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.IProperty;
import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.rx.Property;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import static io.reactivesprint.internal.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Abstract implementation of a {@code ViewModel} used in MVVM.
 */
public class ViewModel implements IViewModel {
    //region Fields

    private final IMutableProperty<Boolean> active = new MutableProperty<>(false);
    private final IMutableProperty<CharSequence> title = new MutableProperty<>(null);

    private final Subject<Observable<Boolean>, Observable<Boolean>> loadingSubject = PublishSubject.create();
    private final IProperty<Boolean> loading = new MutableProperty<>(false);
    private final IProperty<Boolean> enabled = new Property<>(false, loading.getObservable().map(new Func1<Boolean, Boolean>() {
        @Override
        public Boolean call(Boolean aBoolean) {
            return !aBoolean;
        }
    }));

    private final Subject<Observable<IViewModelException>, Observable<IViewModelException>> errorsSubject = PublishSubject.create();
    private final Observable<IViewModelException> errors = Observable.merge(errorsSubject);

    //endregion

    //region Properties

    @Override
    public IMutableProperty<Boolean> getActive() {
        return active;
    }

    @Override
    public IMutableProperty<CharSequence> getTitle() {
        return title;
    }

    @Override
    public IProperty<Boolean> getLoading() {
        return loading;
    }

    @Override
    public Observable<IViewModelException> getErrors() {
        return errors;
    }

    @Override
    public IProperty<Boolean> getEnabled() {
        return enabled;
    }

    //endregion

    //region Binding

    @Override
    public void bindLoading(Observable<Boolean> loadingObservable) {
        checkNotNull(loadingObservable, "loadingObservable");
        loadingSubject.onNext(loadingObservable.onErrorResumeNext(Observable.<Boolean>empty()));
    }

    @Override
    public void bindErrors(Observable<IViewModelException> errorObservable) {
        checkNotNull(errorObservable, "errorObservable");
        errorsSubject.onNext(errorObservable.onErrorResumeNext(Observable.<IViewModelException>empty()));
    }

    @Override
    public <I, R> void bindCommand(ICommand<I, R> command) {
        checkNotNull(command, "command");
        bindLoading(command.isExecuting().getObservable());

        bindErrors(command.getErrors().flatMap(new Func1<Throwable, Observable<IViewModelException>>() {
            @Override
            public Observable<IViewModelException> call(Throwable throwable) {
                if (throwable instanceof IViewModelException) {
                    return Observable.just((IViewModelException) throwable);
                }
                return Observable.empty();
            }
        }));
    }

    //endregion
}
