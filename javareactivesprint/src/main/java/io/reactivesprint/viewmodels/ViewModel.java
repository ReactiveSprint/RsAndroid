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

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Abstract implementation of a {@code ViewModel} used in MVVM.
 */
public class ViewModel implements ViewModelType {
    //region Fields

    private final IMutableProperty<Boolean> active = new MutableProperty<>(false);
    private final IMutableProperty<String> title = new MutableProperty<>(null);

    private final Subject<Observable<Boolean>, Observable<Boolean>> loadingSubject = PublishSubject.create();
    private final IProperty<Boolean> loading = new MutableProperty<>(false);
    private final IProperty<Boolean> enabled = new Property<>(false, loading.getObservable().map(new Func1<Boolean, Boolean>() {
        @Override
        public Boolean call(Boolean aBoolean) {
            return !aBoolean;
        }
    }));

    private final Subject<Observable<ErrorType>, Observable<ErrorType>> errorsSubject = PublishSubject.create();
    private final Observable<ErrorType> errors = Observable.merge(errorsSubject);

    //endregion

    //region Properties

    @Override
    public IMutableProperty<Boolean> getActive() {
        return active;
    }

    @Override
    public IMutableProperty<String> getTitle() {
        return title;
    }

    @Override
    public IProperty<Boolean> getLoading() {
        return loading;
    }

    @Override
    public Observable<ErrorType> getErrors() {
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
        loadingSubject.onNext(loadingObservable.onErrorResumeNext(Observable.<Boolean>empty()));
    }

    @Override
    public void bindErrors(Observable<ErrorType> errorObservable) {
        errorsSubject.onNext(errorObservable.onErrorResumeNext(Observable.<ErrorType>empty()));
    }

    @Override
    public <I, R> void bindCommand(ICommand<I, R> command) {
        bindLoading(command.isExecuting().getObservable());

        bindErrors(command.getErrors().flatMap(new Func1<Throwable, Observable<ErrorType>>() {
            @Override
            public Observable<ErrorType> call(Throwable throwable) {
                if (throwable instanceof ErrorType) {
                    return Observable.just((ErrorType) throwable);
                }
                return Observable.empty();
            }
        }));
    }

    //endregion
}
