package io.reactivesprint.viewmodels;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivesprint.rx.Command;
import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.IProperty;
import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.rx.Pair;
import io.reactivesprint.rx.Property;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/2/16.
 * An implementation of {@link IFetchedArrayViewModel} that fetches ViewModels by calling {@link #onFetch(Object)}
 */
public abstract class FetchedArrayViewModel<E extends IViewModel, P> extends ConstantArrayViewModel<E>
        implements IFetchedArrayViewModel<E, P, Void, Collection<E>> {
    //region Fields

    private final MutableProperty<Boolean> refreshing = new MutableProperty<>(false);
    private final MutableProperty<Boolean> fetchingNextPage = new MutableProperty<>(false);
    private final MutableProperty<Boolean> hasNextPage = new MutableProperty<>(true);

    private P nextPage;

    private final ICommand<Void, Collection<E>> refreshCommand;
    private final ICommand<Void, Collection<E>> fetchCommand;
    private final ICommand<Void, Collection<E>> fetchIfNeededCommand;

    //endregion

    //region Constructors

    public FetchedArrayViewModel() {
        fetchCommand = createFetchCommand();
        refreshCommand = createRefreshCommand();
        fetchIfNeededCommand = createFetchIfNeededCommand();
    }

    //endregion

    //region Abstract

    /**
     * Implement this method to fetch ViewModels at {@code page}
     */
    protected abstract Observable<Pair<P, Collection<E>>> onFetch(P page);

    //endregion

    //region IFetchedArrayViewModel

    @Override
    public IProperty<Boolean> refreshing() {
        return new Property<>(refreshing);
    }

    @Override
    public P getNextPage() {
        return nextPage;
    }

    protected void setNextPage(P nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public IProperty<Boolean> fetchingNextPage() {
        return new Property<>(fetchingNextPage);
    }

    @Override
    public IProperty<Boolean> hasNextPage() {
        return new Property<>(hasNextPage);
    }

    @Override
    public ICommand<Void, Collection<E>> getRefreshCommand() {
        return refreshCommand;
    }

    @Override
    public ICommand<Void, Collection<E>> getFetchCommand() {
        return fetchCommand;
    }

    @Override
    public ICommand<Void, Collection<E>> getFetchIfNeededCommand() {
        return fetchIfNeededCommand;
    }

    //endregion

    //region Create Commands

    protected ICommand<Void, Collection<E>> createRefreshCommand() {
        Command<Void, Collection<E>> command = new Command<>(enabled(), new Func1<Void, Observable<Collection<E>>>() {
            @Override
            public Observable<Collection<E>> call(Void aVoid) {
                refreshing.setValue(true);
                return fetch(null);
            }
        });

        bindCommand(command);

        return command;
    }

    protected ICommand<Void, Collection<E>> createFetchCommand() {
        Command<Void, Collection<E>> command = new Command<>(enabled(), new Func1<Void, Observable<Collection<E>>>() {
            @Override
            public Observable<Collection<E>> call(Void aVoid) {
                if (nextPage != null) {
                    fetchingNextPage.setValue(true);
                } else {
                    refreshing.setValue(true);
                }
                return fetch(nextPage);
            }
        });

        bindCommand(command);

        return command;
    }

    protected ICommand<Void, Collection<E>> createFetchIfNeededCommand() {
        return new Command<>(enabled(), new Func1<Void, Observable<Collection<E>>>() {
            @Override
            public Observable<Collection<E>> call(Void input) {
                if (getNextPage() != null && hasNextPage().getValue()) {
                    return getFetchCommand().apply(input);
                }

                return Observable.empty();
            }
        });
    }

    private Observable<Collection<E>> fetch(final P nextPage) {
        return onFetch(nextPage)
                .doOnNext(new Action1<Pair<P, Collection<E>>>() {
                    @Override
                    public void call(Pair<P, Collection<E>> objects) {
                        ArrayList<E> newViewModels = new ArrayList<>();
                        if (!refreshing.getValue()) {
                            newViewModels.addAll(getViewModels());
                        }

                        newViewModels.addAll(objects.getValue1());

                        setViewModels(newViewModels);

                        hasNextPage.setValue(!objects.getValue1().isEmpty());

                        FetchedArrayViewModel.this.nextPage = objects.getValue0();
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        refreshing.setValue(false);
                        fetchingNextPage.setValue(false);
                    }
                })
                .map(new Func1<Pair<P, Collection<E>>, Collection<E>>() {
                    @Override
                    public Collection<E> call(Pair<P, Collection<E>> objects) {
                        return objects.getValue1();
                    }
                });
    }

    //endregion
}
