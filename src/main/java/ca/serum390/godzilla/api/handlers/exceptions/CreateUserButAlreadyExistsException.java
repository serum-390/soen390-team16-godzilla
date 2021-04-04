package ca.serum390.godzilla.api.handlers.exceptions;

public class CreateUserButAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 7304118857901314870L;
    public static final String ERROR_MESSAGE = "User with username `%s` already exists";

    public static CreateUserButAlreadyExistsException forUser(String username) {
        return new CreateUserButAlreadyExistsException(String.format(ERROR_MESSAGE, username));
    }

    public CreateUserButAlreadyExistsException(String message) {
        super(message);
    }

    public CreateUserButAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CreateUserButAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateUserButAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
