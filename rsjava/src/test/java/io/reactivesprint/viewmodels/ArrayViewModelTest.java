package io.reactivesprint.viewmodels;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ahmad Baraka on 4/7/16.
 */
public class ArrayViewModelTest extends TestCase {
    ArrayViewModel<ViewModel> arrayViewModel;
    List<ViewModel> viewModels;
    static final String TEST_TITLE = "TestTitle";
    static final String TEST_EMPTY_MESSAGE = "No Items Available.";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        viewModels = generateViewModels(3);
        arrayViewModel = null;
    }

    public void testDefaultValues() throws Exception {
        arrayViewModel = new ArrayViewModel<>(Collections.<ViewModel>emptyList());

        assertThat(arrayViewModel.title().getValue()).isNull();
        assertThat(arrayViewModel.localizedEmptyMessage().getValue()).isNull();
        assertThat(arrayViewModel.count().getValue()).isZero();
        assertThat(arrayViewModel.empty().getValue()).isTrue();
    }

    public void testGetViewModel() throws Exception {
        arrayViewModel = new ArrayViewModel<>(viewModels);

        assertThat(arrayViewModel.getViewModel(0).title().getValue())
                .isEqualTo("1");
        assertThat(arrayViewModel.getViewModel(1).title().getValue())
                .isEqualTo("2");
        assertThat(arrayViewModel.getViewModel(2).title().getValue())
                .isEqualTo("3");
    }

    public void testIndexOfObject() throws Exception {
        arrayViewModel = new ArrayViewModel<>(viewModels);
        final ViewModel viewModel = viewModels.get(1);

        assertThat(arrayViewModel.indexOf(viewModel)).isEqualTo(1);
    }

    //region Helpers

    public static List<ViewModel> generateViewModels(int count) {
        return generateViewModels(count, 0);
    }

    public static List<ViewModel> generateViewModels(int count, int start) {
        List<ViewModel> viewModels = new ArrayList<>(count);

        for (int i = 1; i <= count; i++) {
            ViewModel viewModel = new ViewModel(Integer.toString(i + start));

            viewModels.add(viewModel);
        }

        return viewModels;
    }

    //endregion
}
