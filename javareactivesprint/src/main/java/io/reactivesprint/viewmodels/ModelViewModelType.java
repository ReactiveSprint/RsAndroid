package io.reactivesprint.viewmodels;

import io.reactivesprint.models.ModelType;

/**
 * Created by Ahmad Baraka on 3/30/16.
 */
public interface ModelViewModelType<Model extends ModelType> extends ViewModelType {

    /**
     * @return wrapped Model.
     */
    Model getModel();
}
