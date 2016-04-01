package io.reactivesprint.viewmodels;

import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.rx.MutablePropertyType;
import io.reactivesprint.rx.PropertyType;

/**
 * Created by Ahmad Baraka on 3/29/16.
 * Abstract implementation of a {@code ViewModel} used in MVVM.
 */
public class ViewModel implements ViewModelType {
    //region Fields

    private final MutablePropertyType<Boolean> active = new MutableProperty<>(false);
    private final MutablePropertyType<String> title = new MutableProperty<>(null);
    private final PropertyType<Boolean> loading = new MutableProperty<>(false);

    //endregion

    //region Properties

    @Override
    public MutablePropertyType<Boolean> getActive() {
        return active;
    }

    @Override
    public MutablePropertyType<String> getTitle() {
        return title;
    }

    @Override
    public PropertyType<Boolean> getLoading() {
        return loading;
    }

    //endregion
}
