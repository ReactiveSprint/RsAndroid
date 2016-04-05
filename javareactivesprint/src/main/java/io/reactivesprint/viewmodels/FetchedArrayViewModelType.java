package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.Command;
import io.reactivesprint.rx.IProperty;
import rx.Observable;

/**
 * Created by Ahmad Baraka on 3/30/16.
 * ArrayViewModelType which its array is lazily fetched, or even paginated.
 *
 * @param <Element>     Type of Elements in Array.
 * @param <Page>        Type representing page.
 * @param <FetchInput>  Type of FetchCommand Input.
 * @param <FetchOutput> Type of FetchCommand Output.
 */
public interface FetchedArrayViewModelType<Element extends ViewModelType, Page, FetchInput, FetchOutput>
        extends ArrayViewModelType<Element> {
    /**
     * @return Whether the receiver is refreshing.
     */
    IProperty<Boolean> isRefreshing();

    /**
     * @return An object representing next page.
     */
    Page getNextPage();

    /**
     * @return Whether the receiver is fetching next page.
     */
    IProperty<Boolean> isFetchingNextPage();

    /**
     * @return Next Page
     */
    IProperty<Boolean> hasNextPage();

    /**
     * @return Command which refreshes ViewModels.
     */
    Command<FetchInput, FetchOutput> getRefreshCommand();

    /**
     * @return Command which fetches ViewModels.
     * If {@code nextPage} is null, this command will refresh, else this command should fetch next page.
     */
    Command<FetchInput, FetchOutput> getFetchCommand();

    /**
     * @return Applies {@link #getFetchCommand()} only if next page is non null or returns {@link Observable#empty()}
     */
    Command<FetchInput, FetchOutput> getFetchIfNeededCommand();
}
