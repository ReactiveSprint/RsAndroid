package io.reactivesprint.viewmodels;

import java.util.Iterator;
import java.util.List;

import io.reactivesprint.rx.IProperty;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Represents an ViewModel which wraps an Array of ViewModels of type `Element`
 *
 * @param <E> Type of each element in Array.
 */
public interface IArrayViewModel<E extends IViewModel> extends IViewModel, Iterable<E> {
    /**
     * @return List of {@code Element}
     */
    List<E> getViewModels();

    /**
     * @return Count of wrapped elements.
     */
    IProperty<Integer> getCount();

    /**
     * @return true if Array is empty, false otherwise.
     */
    IProperty<Boolean> isEmpty();

    /**
     * @return localized message to be used when the array is empty.
     */
    IProperty<CharSequence> getLocalizedEmptyMessage();

    /**
     * Access the indexth element.
     *
     * @param index Must be > 0 and < count.
     */
    E getViewModel(int index);

    /**
     * @return the first index where {@code value} equals {@code element} or {@code null}
     * {@code element} is not found.
     */
    int indexOf(E element);
}
