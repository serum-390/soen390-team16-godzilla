package ca.serum390.godzilla.util;

import java.util.Optional;

/**
 * Utility class containing some convenience methods for functional programming
 */
public class FunctionalUtils {

    private FunctionalUtils() {}

    /**
     * Remove the need to handle a {@link NumberFormatException} on failed integer
     * parsing by receiving an {@link Optional} instead.
     *
     * @param value The string potentially containing an integer
     * @return An {@link Optional} containing the value if the parse succeeds,
     *         otherwise an empty {@link Optional}
     */
    public static Optional<Integer> tryParse(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
