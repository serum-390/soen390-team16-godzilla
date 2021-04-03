package ca.serum390.godzilla.api.handlers.exceptions;

import ca.serum390.godzilla.domain.packaging.PackagedProduct;
import reactor.core.publisher.SynchronousSink;

/**
 * Indicates the a {@link } has a negative Id
 */
public class NegativePackagedProductIdException extends RuntimeException {
    private static final long serialVersionUID = -4949643334214000453L;
    public static final String CANNOT_PROCESS_DUE_TO = "Cannot process due to: ";

    /**
     * A handler/filter to apply on {@link reactor.core.publisher.Mono} or
     * {@link reactor.core.publisher.Flux} streams. Will cause the reactive stream
     * to emit an {@code error} signal if the id of the {@link PackagedProduct} is less
     * than 0.
     *
     * @param package The {@link PackagedProduct} whose id you want to check
     * @param sink    This {@link SynchronousSink} will be used to emit an error
     *                signal if the {@link PackagedProduct#getId()} method returns a
     *                negative value
     */
    public static void errorIfNegativePackageId(
            PackagedProduct product,
            SynchronousSink<PackagedProduct> sink) {
        if (product.getId() < 0) {
            sink.error(forProduct(product));
        } else {
            sink.next(product);
        }
    }

    public static NegativePackagedProductIdException forProduct(PackagedProduct product) {
        return new NegativePackagedProductIdException(
                "Negative Id not allowed for Packaged Product " + product);
    }

    // CONSTRUCTORS: Pass straight to RuntimeException
    public NegativePackagedProductIdException() {}
    public NegativePackagedProductIdException(Throwable cause) { super(cause); }
    public NegativePackagedProductIdException(String message) { super(message); }
    public NegativePackagedProductIdException(String message, Throwable cause) { super(message, cause); }
    public NegativePackagedProductIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}