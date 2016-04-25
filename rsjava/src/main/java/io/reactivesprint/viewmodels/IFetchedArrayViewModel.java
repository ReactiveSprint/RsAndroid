package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.ICommand;
import io.reactivesprint.rx.IProperty;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * {@link IArrayViewModel} which its array is lazily fetched, or even paginated.
 *
 * @param <E> Type of Elements in Array.
 * @param <P> Type representing page.
 * @param <I> Type of FetchCommand Input.
 * @param <R> Type of FetchCommand Output.
 */
public interface IFetchedArrayViewModel<E extends IViewModel, P, I, R> extends IArrayViewModel<E> {
    /**
     * @return Whether the receiver is refreshing.
     */
    IProperty<Boolean> refreshing();

    /**
     * @return An object representing next page.
     */
    P getNextPage();

    /**
     * @return Whether the receiver is fetching next page.
     */
    IProperty<Boolean> fetchingNextPage();

    /**
     * @return Next Page
     */
    IProperty<Boolean> hasNextPage();

    /**
     * @return Command which refreshes ViewModels.
     */
    ICommand<I, R> getRefreshCommand();

    /**
     * @return Command which fetches ViewModels.
     * If {@code nextPage} is null, this command will refresh, else this command should fetch next page.
     */
    ICommand<I, R> getFetchCommand();

    /**
     * @return Applies {@link #getFetchCommand()} only if next page is non null or returns {@link Observable#empty()}
     */
    ICommand<I, R> getFetchIfNeededCommand();
}
