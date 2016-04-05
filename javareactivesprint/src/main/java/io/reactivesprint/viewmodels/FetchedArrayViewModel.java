package io.reactivesprint.viewmodels;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivesprint.rx.Command;
import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.Property;
import io.reactivesprint.rx.IProperty;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Ahmad Baraka on 4/2/16.
 * An implementation of FetchedArrayViewModelType that fetches ViewModels by calling {@code fetchFunc}
 */
public class FetchedArrayViewModel<Element extends ViewModel> extends ViewModel implements FetchedArrayViewModelType<Element, Integer, Void, List<Element>> {
    //region Fields

    private MutableProperty<List<Element>> viewModels = new MutableProperty<>(Collections.<Element>emptyList());

    private final IProperty<Integer> count;
    private final IProperty<Boolean> empty;

    private final IMutableProperty<String> localizedEmptyMessage = new MutableProperty<>(null);

    private final MutableProperty<Boolean> refreshing = new MutableProperty<>(false);
    private final MutableProperty<Boolean> fetchingNextPage = new MutableProperty<>(false);
    private final MutableProperty<Boolean> hasNextPage = new MutableProperty<>(true);

    private Integer nextPage;

    private final Func1<Integer, Observable<Pair<Integer, List<Element>>>> fetchFunc;
    private final Command<Void, List<Element>> refreshCommand;
    private final Command<Void, List<Element>> fetchCommand;
    private final Command<Void, List<Element>> fetchIfNeededCommand;

    //endregion

    //region Constructors

    /**
     * Creates an instance with {@code viewModels}
     */
    public FetchedArrayViewModel(Func1<Integer, Observable<Pair<Integer, List<Element>>>> fetchFunc) {
        this.fetchFunc = fetchFunc;
        count = new Property<>(0, viewModels.getObservable().map(new Func1<List<Element>, Integer>() {
            @Override
            public Integer call(List<Element> elements) {
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
        fetchIfNeededCommand = createFetchIfNeededCommand(this);
    }

    //endregion

    //region ArrayViewModelType

    @Override
    public IProperty<Integer> getCount() {
        return count;
    }

    @Override
    public IProperty<Boolean> isEmpty() {
        return empty;
    }

    @Override
    public IMutableProperty<String> getLocalizedEmptyMessage() {
        return localizedEmptyMessage;
    }

    @Override
    public List<Element> getViewModels() {
        return viewModels.getValue();
    }

    @Override
    public int indexOf(Element element) {
        return viewModels.getValue().indexOf(element);
    }

    @Override
    public Element getViewModel(int index) {
        return viewModels.getValue().get(index);
    }

    //endregion

    //region FetchedArrayViewModelType

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
    public Command<Void, List<Element>> getRefreshCommand() {
        return refreshCommand;
    }

    @Override
    public Command<Void, List<Element>> getFetchCommand() {
        return fetchCommand;
    }

    @Override
    public Command<Void, List<Element>> getFetchIfNeededCommand() {
        return fetchIfNeededCommand;
    }

    //endregion

    //region Create Commands

    protected Command<Void, List<Element>> createRefreshCommand() {
        Command<Void, List<Element>> command = new Command<>(getEnabled(), new Func1<Void, Observable<List<Element>>>() {
            @Override
            public Observable<List<Element>> call(Void aVoid) {
                refreshing.setValue(true);
                return fetch(null);
            }
        });

        bindCommand(command);

        return command;
    }

    protected Command<Void, List<Element>> createFetchCommand() {
        Command<Void, List<Element>> command = new Command<>(getEnabled(), new Func1<Void, Observable<List<Element>>>() {
            @Override
            public Observable<List<Element>> call(Void aVoid) {
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

    protected static <Element extends ViewModelType, Page, FetchInput, FetchOutput> Command<FetchInput, FetchOutput>
    createFetchIfNeededCommand(final FetchedArrayViewModelType<Element, Page, FetchInput, FetchOutput> fetchedArrayViewModelType) {
        Command<FetchInput, FetchOutput> command = new Command<>(fetchedArrayViewModelType.getEnabled(), new Func1<FetchInput, Observable<FetchOutput>>() {
            @Override
            public Observable<FetchOutput> call(FetchInput input) {
                if (fetchedArrayViewModelType.getNextPage() != null && fetchedArrayViewModelType.hasNextPage().getValue()) {
                    return fetchedArrayViewModelType.getFetchCommand().apply(input);
                }

                return Observable.empty();
            }
        });

        fetchedArrayViewModelType.bindCommand(command);

        return command;
    }

    private Observable<List<Element>> fetch(final Integer nextPage) {
        return fetchFunc.call(nextPage)
                .doOnNext(new Action1<Pair<Integer, List<Element>>>() {
                    @Override
                    public void call(Pair<Integer, List<Element>> objects) {
                        ArrayList<Element> newViewModels = new ArrayList<>();
                        if (!refreshing.getValue()) {
                            newViewModels.addAll(viewModels.getValue());
                        }

                        newViewModels.addAll(objects.getValue1());

                        viewModels.setValue(newViewModels);

                        hasNextPage.setValue(!objects.getValue1().isEmpty());

                        FetchedArrayViewModel.this.nextPage = objects.getValue0();
                    }
                })
                .map(new Func1<Pair<Integer, List<Element>>, List<Element>>() {
                    @Override
                    public List<Element> call(Pair<Integer, List<Element>> objects) {
                        return objects.getValue1();
                    }
                });
    }


    //endregion
}
