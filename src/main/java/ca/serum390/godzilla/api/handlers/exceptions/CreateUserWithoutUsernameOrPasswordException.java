package ca.serum390.godzilla.api.handlers.exceptions;

import static ca.serum390.godzilla.api.handlers.GodzillaUserHandler.PASSWORD_FIELD;
import static ca.serum390.godzilla.api.handlers.GodzillaUserHandler.USERNAME_FIELD;

import org.springframework.util.MultiValueMap;

import reactor.core.publisher.SynchronousSink;

public class CreateUserWithoutUsernameOrPasswordException extends RuntimeException {
    private static final long serialVersionUID = -437083407553022825L;
    public static final String ERROR_MESSAGE = "User create form is missing required fields: `username` and/or `password`";

    /**
     * Causes the reactive stream to emit an error if the {@link MultiValueMap form
     * data} does not contain a username or password field.
     *
     * @param formData Form data submitted in an attempt to create a new user
     * @param sink     The {@link SynchronousSink} used to emit a
     *                 {@link CreateUserWithoutUsernameOrPasswordException} if the
     *                 form data does not contain fields for both username and
     *                 password.
     */
    public static void handle(
            MultiValueMap<String, String> formData,
            SynchronousSink<MultiValueMap<String, String>> sink) {
        if (formData.getFirst(USERNAME_FIELD) == null
                || formData.getFirst(PASSWORD_FIELD) == null) {
            sink.error(new CreateUserWithoutUsernameOrPasswordException(ERROR_MESSAGE));
        } else {
            sink.next(formData);
        }
    }

    public CreateUserWithoutUsernameOrPasswordException() {}

    public CreateUserWithoutUsernameOrPasswordException(String message) { super(message); }

    public CreateUserWithoutUsernameOrPasswordException(Throwable cause) {
        super(cause);
    }

    public CreateUserWithoutUsernameOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateUserWithoutUsernameOrPasswordException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
