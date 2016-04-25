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

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/1/16.
 * {@link IArrayViewModel} implementation that has a constant {@code List<ViewModel>}
 */
public class ArrayViewModel<E extends IViewModel> extends ViewModel implements IArrayViewModel<E>, RandomAccess {
    //region Fields

    private IProperty<Integer> count;

    private IProperty<Boolean> empty;

    private List<E> viewModels;

    private IMutableProperty<CharSequence> localizedEmptyMessage = new MutableProperty<>(null);

    //endregion

    //region Constructors

    protected ArrayViewModel() {

    }

    /**
     * Creates an instance with {@code viewModels}
     */
    public ArrayViewModel(Collection<E> viewModels) {
        checkNotNull(viewModels, "viewModels");
        setViewModels(viewModels);
    }

    public ArrayViewModel(Collection<E> viewModels, String title) {
        this(viewModels);
        title().setValue(title);
    }

    public ArrayViewModel(Collection<E> viewModels, String title, String localizedEmptyMessage) {
        this(viewModels);
        title().setValue(title);
        localizedEmptyMessage().setValue(localizedEmptyMessage);
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
    public IProperty<Integer> count() {
        return count;
    }

    @Override
    public IProperty<Boolean> empty() {
        return empty;
    }

    @Override
    public IMutableProperty<CharSequence> localizedEmptyMessage() {
        return localizedEmptyMessage;
    }

    protected void setViewModels(Collection<E> viewModels) {
        if (this.viewModels != null) {
            throw new AssertionError("Cannot re-setViewModels.");
        }
        this.viewModels = new ArrayList<>(viewModels);
        count = new ConstantProperty<>(viewModels.size());
        empty = new ConstantProperty<>(viewModels.isEmpty());
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
