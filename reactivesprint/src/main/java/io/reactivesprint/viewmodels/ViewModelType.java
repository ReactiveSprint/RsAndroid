package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.MutablePropertyType;
import io.reactivesprint.rx.PropertyType;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Represents a ViewModel.
 */
public interface ViewModelType {
    /**
     * @return Whether the receiver is active.
     */
    MutablePropertyType<Boolean> getActive();

    /**
     * @return General title of the receiver.
     */
    MutablePropertyType<String> getTitle();

    /**
     * @return Whether the receiver is loading.
     */
    PropertyType<Boolean> getLoading();
}
