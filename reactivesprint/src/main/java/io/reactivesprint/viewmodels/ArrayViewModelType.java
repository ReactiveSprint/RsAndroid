package io.reactivesprint.viewmodels;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivesprint.rx.PropertyType;

/**
 * Created by Ahmad Baraka on 3/29/16.
 */
public interface ArrayViewModelType<Element extends ViewModelType> extends ViewModelType {
    List<Element> getViewModels();

    PropertyType<Integer> getCount();

    PropertyType<String> getLocalizedEmptyMessage();

    Element getViewModel(int index);

    int indexOf(Element element);
}
