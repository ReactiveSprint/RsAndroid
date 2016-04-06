package io.reactivesprint.views;

import io.reactivesprint.viewmodels.ErrorType;
import rx.functions.Action1;

/**
 * Created by Ahmad Baraka on 4/6/16.
 */
public final class ViewControllers {
    private ViewControllers() {
        throw new IllegalStateException("no instances.");
    }

    public static Action1<String> setTitle(final IViewController<?> viewController) {
        return new Action1<String>() {
            @Override
            public void call(String s) {
                viewController.setTitle(s);
            }
        };
    }

    public static Action1<Boolean> presentLoading(final IViewController<?> viewController) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                viewController.presentLoading(aBoolean);
            }
        };
    }

    public static Action1<ErrorType> presentError(final IViewController<?> viewController) {
        return new Action1<ErrorType>() {
            @Override
            public void call(ErrorType errorType) {
                viewController.presentError(errorType);
            }
        };
    }

    public static Action1<Integer> onDataSetChanged(final IArrayViewController<?, ?> viewController) {
        return new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                viewController.onDataSetChanged();
            }
        };
    }
    
    public static Action1<Boolean> presentRefreshing(final IFetchedArrayViewController<?, ?> viewController) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                viewController.presentRefreshing(aBoolean);
            }
        };
    }

    public static Action1<Boolean> presentFetchingNextPage(final IFetchedArrayViewController<?, ?> viewController) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                viewController.presentFetchingNextPage(aBoolean);
            }
        };
    }
}
