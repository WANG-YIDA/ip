package app.exception;

/**
 * Thrown when the user issues an unrecognized command.
 */
public class InvalidCommandException extends Exception {
    /**
     * Constructs the exception with a descriptive message.
     *
     * @param msg the detail message describing the invalid command
     */
    public InvalidCommandException(String msg) {
        super(msg);
    }
}
