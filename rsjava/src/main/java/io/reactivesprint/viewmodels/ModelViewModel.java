package io.reactivesprint.viewmodels;

import io.reactivesprint.models.IModel;

/**
 * Created by Ahmad Baraka on 4/1/16.
 */
public class ModelViewModel<M extends IModel> extends ViewModel implements IModelViewModel<M> {

    private final M model;

    public ModelViewModel(M model) {
        this.model = model;
    }

    @Override
    public M getModel() {
        return model;
    }
}
