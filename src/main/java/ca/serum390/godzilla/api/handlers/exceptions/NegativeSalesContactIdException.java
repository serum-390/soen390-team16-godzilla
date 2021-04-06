package ca.serum390.godzilla.api.handlers.exceptions;

import ca.serum390.godzilla.domain.orders.SalesContact;
import reactor.core.publisher.SynchronousSink;

/**
 * Indicates the a {@link SalesContact} has a negative Id
 */
public class NegativeSalesContactIdException extends RuntimeException {
    private static final long serialVersionUID = -4949643334214000453L;
    public static final String CANNOT_PROCESS_DUE_TO = "Cannot process due to: ";

    /**
     * A handler/filter to apply on {@link reactor.core.publisher.Mono} or
     * {@link reactor.core.publisher.Flux} streams. Will cause the reactive stream
     * to emit an {@code error} signal if the id of the {@link SalesContact} is less
     * than 0.
     *
     * @param contact The {@link SalesContact} whose id you want to check
     * @param sink    This {@link SynchronousSink} will be used to emit an error
     *                signal if the {@link SalesContact#getId()} method returns a
     *                negative value
     */
    public static void handle(SalesContact contact, SynchronousSink<SalesContact> sink) {
        if (contact.getId() < 0) {
            sink.error(forContact(contact));
        } else {
            sink.next(contact);
        }
    }

    public static NegativeSalesContactIdException forContact(SalesContact contact) {
        return new NegativeSalesContactIdException(
                "Negative Id not allowed for SalesContact " + contact);
    }

    // CONSTRUCTORS: Pass straight to RuntimeException
    public NegativeSalesContactIdException() {}
    public NegativeSalesContactIdException(Throwable cause) { super(cause); }
    public NegativeSalesContactIdException(String message) { super(message); }
    public NegativeSalesContactIdException(String message, Throwable cause) { super(message, cause); }
    public NegativeSalesContactIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
