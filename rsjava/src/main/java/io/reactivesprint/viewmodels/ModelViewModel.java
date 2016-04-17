package io.reactivesprint.viewmodels;

import io.reactivesprint.models.IModel;

import static io.reactivesprint.Preconditions.checkNotNull;

public class ModelViewModel<M extends IModel> extends ViewModel implements IModelViewModel<M> {

    private M model;

    protected ModelViewModel() {

    }

    public ModelViewModel(M model) {
        checkNotNull(model, "model");
        this.model = model;
    }

    @Override
    public M getModel() {
        return model;
    }

    protected void setModel(M model) {
        if (this.model != null) {
            throw new AssertionError("Cannot re-setModel.");
        }
        checkNotNull(model, "model");
        this.model = model;
    }
}
