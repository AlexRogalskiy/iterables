package com.sensiblemetrics.api.iterables;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

@UtilityClass
public class AssertionUtils {

    @NonNull
    public static <T extends Throwable> T checkThrows(final Class<? extends T> expectedThrowable, final Executable executable) {
        try {
            return Assertions.assertThrows(expectedThrowable, executable);
        } catch (AssertionError e) {
            throw new IllegalArgumentException(e);
        }
    }
}
