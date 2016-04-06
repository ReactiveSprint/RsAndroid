package io.reactivesprint.viewmodels;

import io.reactivesprint.models.IModel;

/**
 * Created by Ahmad Baraka on 3/30/16.
 */
public interface IModelViewModel<M extends IModel> extends IViewModel {

    /**
     * @return wrapped Model.
     */
    M getModel();
}
