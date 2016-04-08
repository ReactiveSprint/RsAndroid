package io.reactivesprint.viewmodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

import io.reactivesprint.rx.Command;
import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.IProperty;
import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.rx.Pair;
import io.reactivesprint.rx.Property;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static io.reactivesprint.internal.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/2/16.
 * An implementation of {@link IFetchedArrayViewModel} that fetches ViewModels by calling {@code fetchFunc}
 */
public class FetchedArrayViewModel<E extends ViewModel> extends ViewModel
        implements IFetchedArrayViewModel<E, Integer, Void, Collection<E>>, RandomAccess {
    //region Fields

    private MutableProperty<List<E>> viewModels = new MutableProperty<>(Collections.<E>emptyList());

    private final IProperty<Integer> count;
    private final IProperty<Boolean> empty;

    private final IMutableProperty<CharSequence> localizedEmptyMessage = new MutableProperty<>(null);

    private final MutableProperty<Boolean> refreshing = new MutableProperty<>(false);
    private final MutableProperty<Boolean> fetchingNextPage = new MutableProperty<>(false);
    private final MutableProperty<Boolean> hasNextPage = new MutableProperty<>(true);

    private Integer nextPage;

    private final Func1<Integer, Observable<Pair<Integer, Collection<E>>>> fetchFunc;
    private final ICommand<Void, Collection<E>> refreshCommand;
    private final ICommand<Void, Collection<E>> fetchCommand;
    private final ICommand<Void, Collection<E>> fetchIfNeededCommand;

    //endregion

    //region Constructors

    /**
     * Creates an instance with {@code viewModels}
     */
    public FetchedArrayViewModel(Func1<Integer, Observable<Pair<Integer, Collection<E>>>> fetchFunc) {
        checkNotNull(fetchFunc, "fetchFunc");
        this.fetchFunc = fetchFunc;
        count = new Property<>(0, viewModels.getObservable().map(new Func1<List<E>, Integer>() {
            @Override
            public Integer call(List<E> elements) {
                return elements.size();
            }
        }));

        empty = new Property<>(count.getValue() <= 0, count.getObservable().map(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer <= 0;
            }
        }));

        fetchCommand = createFetchCommand();
        refreshCommand = createRefreshCommand();
        fetchIfNeededCommand = createFetchIfNeededCommand();
    }

    //endregion

    //region Iterable

    @Override
    public Iterator<E> iterator() {
        return viewModels.getValue().iterator();
    }

    //endregion

    //region IArrayViewModel

    @Override
    public IProperty<Integer> getCount() {
        return count;
    }

    @Override
    public IProperty<Boolean> isEmpty() {
        return empty;
    }

    @Override
    public IMutableProperty<CharSequence> getLocalizedEmptyMessage() {
        return localizedEmptyMessage;
    }

    @Override
    public List<E> getViewModels() {
        return viewModels.getValue();
    }

    @Override
    public int indexOf(E element) {
        checkNotNull(element, "element");
        return viewModels.getValue().indexOf(element);
    }

    @Override
    public E getViewModel(int index) {
        return viewModels.getValue().get(index);
    }

    //endregion

    //region IFetchedArrayViewModel

    @Override
    public IProperty<Boolean> isRefreshing() {
        return new Property<>(refreshing);
    }

    @Override
    public Integer getNextPage() {
        return nextPage;
    }

    @Override
    public IProperty<Boolean> isFetchingNextPage() {
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
        Command<Void, Collection<E>> command = new Command<>(isEnabled(), new Func1<Void, Observable<Collection<E>>>() {
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
        Command<Void, Collection<E>> command = new Command<>(isEnabled(), new Func1<Void, Observable<Collection<E>>>() {
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
        return new Command<>(isEnabled(), new Func1<Void, Observable<Collection<E>>>() {
            @Override
            public Observable<Collection<E>> call(Void input) {
                if (getNextPage() != null && hasNextPage().getValue()) {
                    return getFetchCommand().apply(input);
                }

                return Observable.empty();
            }
        });
    }

    private Observable<Collection<E>> fetch(final Integer nextPage) {
        return fetchFunc.call(nextPage)
                .doOnNext(new Action1<Pair<Integer, Collection<E>>>() {
                    @Override
                    public void call(Pair<Integer, Collection<E>> objects) {
                        ArrayList<E> newViewModels = new ArrayList<>();
                        if (!refreshing.getValue()) {
                            newViewModels.addAll(viewModels.getValue());
                        }

                        newViewModels.addAll(objects.getValue1());

                        viewModels.setValue(newViewModels);

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
                .map(new Func1<Pair<Integer, Collection<E>>, Collection<E>>() {
                    @Override
                    public Collection<E> call(Pair<Integer, Collection<E>> objects) {
                        return objects.getValue1();
                    }
                });
    }

    //endregion
}
