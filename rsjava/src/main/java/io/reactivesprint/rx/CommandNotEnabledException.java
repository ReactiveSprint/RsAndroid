package io.reactivesprint.rx;

/**
 * Created by Ahmad Baraka on 4/6/16.
 * An Exception which occurs when user attempts to apply a Command while is not enabled.
 */
public final class CommandNotEnabledException extends RuntimeException {
    CommandNotEnabledException() {
        super("Command is not enabled, and cannot be executed.");
    }
}
