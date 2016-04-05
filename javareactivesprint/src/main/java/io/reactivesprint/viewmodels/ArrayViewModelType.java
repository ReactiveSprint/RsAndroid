package io.reactivesprint.viewmodels;

import java.util.List;

import io.reactivesprint.rx.IProperty;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Represents an ViewModel which wraps an Array of ViewModels of type `Element`
 *
 * @param <Element> Type of each element in Array.
 */
public interface ArrayViewModelType<Element extends ViewModelType> extends ViewModelType {
    /**
     * @return List of {@code Element}
     */
    List<Element> getViewModels();

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
    IProperty<String> getLocalizedEmptyMessage();

    /**
     * Access the indexth element.
     *
     * @param index Must be > 0 and < count.
     */
    Element getViewModel(int index);

    /**
     * @return the first index where {@code value} equals {@code element} or {@code null}
     * {@code element} is not found.
     */
    int indexOf(Element element);
}