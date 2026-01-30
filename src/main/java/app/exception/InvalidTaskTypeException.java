package app.exception;

/**
 * Thrown when a task type is invalid or unrecognized.
 */
public class InvalidTaskTypeException extends Exception {
    /**
     * Constructs the exception with a descriptive message.
     *
     * @param msg the detail message describing the invalid task type
     */
    public InvalidTaskTypeException(String msg) {
        super(msg);
    }
}
