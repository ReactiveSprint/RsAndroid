package io.reactivesprint.viewmodels;

import java.util.ArrayList;
import java.util.List;

import io.reactivesprint.rx.ConstantProperty;
import io.reactivesprint.rx.MutableProperty;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.IProperty;

/**
 * Created by Ahmad Baraka on 4/1/16.
 * ArrayViewModelType implementation that has a constant {@code List<ViewModel>}
 */
public class ArrayViewModel<Element extends ViewModelType> extends ViewModel implements ArrayViewModelType<Element> {
    //region Fields

    private final IProperty<Integer> count;

    private final IProperty<Boolean> empty;

    private final List<Element> viewModels;

    private final IMutableProperty<String> localizedEmptyMessage = new MutableProperty<>(null);

    //endregion

    //region Constructors

    /**
     * Creates an instance with {@code viewModels}
     */
    public ArrayViewModel(List<Element> viewModels) {
        this.viewModels = new ArrayList<>(viewModels);
        count = new ConstantProperty<>(viewModels.size());
        empty = new ConstantProperty<>(viewModels.isEmpty());
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
        return viewModels;
    }

    @Override
    public int indexOf(Element element) {
        return viewModels.indexOf(element);
    }

    @Override
    public Element getViewModel(int index) {
        return viewModels.get(index);
    }

    //endregion
}
