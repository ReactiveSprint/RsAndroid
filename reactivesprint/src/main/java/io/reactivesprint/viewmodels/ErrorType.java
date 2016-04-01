package io.reactivesprint.viewmodels;

import java.util.List;

/**
 * Created by Ahmad Baraka on 4/1/16.
 * Represents an Error which can occur in a ViewModel.
 */
public interface ErrorType {
    String getLocalizedDescription();

    String getLocalizedRecoverySuggestions();

    List<String> getLocalizedRecoveryOptions();
}
