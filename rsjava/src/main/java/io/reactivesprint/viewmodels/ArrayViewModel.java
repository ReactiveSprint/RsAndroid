package io.reactivesprint.viewmodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

import io.reactivesprint.rx.ConstantProperty;
import io.reactivesprint.rx.IMutableProperty;
import io.reactivesprint.rx.IProperty;
import io.reactivesprint.rx.MutableProperty;

import static io.reactivesprint.internal.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/1/16.
 * {@link IArrayViewModel} implementation that has a constant {@code List<ViewModel>}
 */
public class ArrayViewModel<E extends IViewModel> extends ViewModel implements IArrayViewModel<E>, RandomAccess {
    //region Fields

    private final IProperty<Integer> count;

    private final IProperty<Boolean> empty;

    private final List<E> viewModels;

    private final IMutableProperty<CharSequence> localizedEmptyMessage = new MutableProperty<>(null);

    //endregion

    //region Constructors

    /**
     * Creates an instance with {@code viewModels}
     */
    public ArrayViewModel(Collection<E> viewModels) {
        checkNotNull(viewModels, "viewModels");
        this.viewModels = new ArrayList<>(viewModels);
        count = new ConstantProperty<>(viewModels.size());
        empty = new ConstantProperty<>(viewModels.isEmpty());
    }

    public ArrayViewModel(Collection<E> viewModels, String title) {
        this(viewModels);
        getTitle().setValue(title);
    }

    public ArrayViewModel(Collection<E> viewModels, String title, String localizedEmptyMessage) {
        this(viewModels);
        getTitle().setValue(title);
        getLocalizedEmptyMessage().setValue(localizedEmptyMessage);
    }

    //endregion

    //region Iterable

    @Override
    public Iterator<E> iterator() {
        return viewModels.iterator();
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
        return viewModels;
    }

    @Override
    public int indexOf(E element) {
        checkNotNull(element, "element");
        return viewModels.indexOf(element);
    }

    @Override
    public E getViewModel(int index) {
        return viewModels.get(index);
    }

    //endregion
}
