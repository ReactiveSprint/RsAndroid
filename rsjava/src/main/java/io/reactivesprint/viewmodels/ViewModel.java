package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.IProperty;
import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.rx.Property;
import io.reactivesprint.rx.functions.Func1BooleanNot;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
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
    private final IProperty<Boolean> loading;

    private final IProperty<Boolean> enabled;

    private final Subject<Observable<IViewModelException>, Observable<IViewModelException>> errorsSubject = ReplaySubject.create(1);
    private final Observable<IViewModelException> errors = Observable.merge(errorsSubject);

    //endregion

    //region Constructors

    public ViewModel() {


        Observable<Boolean> loadingObservable = Observable.switchOnNext(loadingSubject.scan(Observable.just(false), new Func2<Observable<Boolean>, Observable<Boolean>, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Observable<Boolean> observable, Observable<Boolean> observable2) {
                return Observable.combineLatest(observable, observable2.startWith(false), new Func2<Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean, Boolean aBoolean2) {
                        return aBoolean || aBoolean2;
                    }
                });
            }
        }));

        loading = new Property<>(false, loadingObservable);

        enabled = new Property<>(false, loading.getObservable().map(Func1BooleanNot.getInstance()));
    }

    public ViewModel(String title) {
        this();
        this.title.setValue(title);
    }

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
    public IProperty<Boolean> isLoading() {
        return loading;
    }

    @Override
    public Observable<IViewModelException> getErrors() {
        return errors;
    }

    @Override
    public IProperty<Boolean> isEnabled() {
        return enabled;
    }

    //endregion

    //region Binding

    @Override
    public void bindLoading(Observable<Boolean> loadingObservable) {
        checkNotNull(loadingObservable, "loadingObservable");
        loadingSubject.onNext(loadingObservable
                .concatWith(Observable.just(false))
                .onErrorResumeNext(Observable.just(false))
        );
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
