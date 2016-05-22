package io.reactivesprint.viewmodels;

import java.util.Collection;

/**
 * Created by Ahmad Baraka on 4/24/16.
 */
public class TestArrayViewModel extends ArrayViewModel<ViewModel> {

    public TestArrayViewModel(Collection<ViewModel> viewModels) {
        super(viewModels);
    }

    @Override
    public void setViewModels(Collection<ViewModel> viewModels) {
        super.setViewModels(viewModels);
    }
}
