package io.reactivesprint.viewmodels;

import io.reactivesprint.models.ModelType;

/**
 * Created by Ahmad Baraka on 4/1/16.
 */
public class ModelViewModel<M extends ModelType> extends ViewModel implements ModelViewModelType<M> {

    private final M model;

    public ModelViewModel(M model) {
        this.model = model;
    }

    @Override
    public M getModel() {
        return model;
    }
}
