package io.reactivesprint.viewmodels;

import android.support.annotation.NonNull;

import io.reactivesprint.models.ModelType;

/**
 * Created by Ahmad Baraka on 4/1/16.
 */
public class ModelViewModel<Model extends ModelType> extends ViewModel implements ModelViewModelType<Model> {

    private final Model model;

    public ModelViewModel(@NonNull Model model) {
        this.model = model;
    }

    @Override
    public Model getModel() {
        return model;
    }
}