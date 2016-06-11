package io.reactivesprint.viewmodels;

import io.reactivesprint.models.IModel;

import static io.reactivesprint.Preconditions.checkNotNull;

/**
 * Created by Ahmad Baraka on 4/1/16.
 * <p/>
 * Implementation of {@link IModelViewModel} which has a constant {@link IModel}.
 */
public class ModelViewModel<M extends IModel> extends ViewModel implements IModelViewModel<M> {
    private M model;

    protected ModelViewModel() {

    }

    public ModelViewModel(M model) {
        setModel(model);
    }

    @Override
    public M getModel() {
        if (model == null) {
            throw new AssertionError("setModel was never called for class: " + getClass().getName());
        }
        return model;
    }

    protected void setModel(M model) {
        checkNotNull(model, "model");
        this.model = model;
    }

    @Override
    public String toString() {
        return "ModelViewModel{" +
                "model=" + model +
                "} " + super.toString();
    }
}
