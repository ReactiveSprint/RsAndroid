package io.reactivesprint.viewmodels;

import io.reactivesprint.models.IModel;

import static io.reactivesprint.Preconditions.checkNotNull;

public class ModelViewModel<M extends IModel> extends ViewModel implements IModelViewModel<M> {

    private final M model;

    public ModelViewModel(M model) {
        checkNotNull(model, "model");
        this.model = model;
    }

    @Override
    public M getModel() {
        return model;
    }
}
