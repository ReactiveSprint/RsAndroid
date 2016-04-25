package io.reactivesprint.viewmodels;

import io.reactivesprint.models.IModel;

/**
 * Created by Ahmad Baraka on 4/25/16.
 * <p/>
 * {@link ModelViewModel} subclass which allows {@link #setModel(IModel)}
 */
public class MutableModelViewModel<M extends IModel> extends ModelViewModel<M> {
    public MutableModelViewModel() {
    }

    public MutableModelViewModel(M model) {
        super(model);
    }

    @Override
    public void setModel(M model) {
        super.setModel(model);
    }
}
