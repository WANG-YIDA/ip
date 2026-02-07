package app.exceptions;

/**
 * Signals that a required component of a command is missing.
 */
public class MissingComponentException extends Exception {
    /**
     * Constructs the exception with a descriptive message.
     *
     * @param msg the detail message describing the missing component
     */
    public MissingComponentException(String msg) {
        super(msg);
    }
}
