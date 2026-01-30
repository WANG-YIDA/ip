package app.exception;

/**
 * Indicates that a request was rejected (for example task list is full)
 */
public class RequestRejectedException extends Exception {
    /**
     * Constructs the exception with a descriptive message.
     *
     * @param msg the detail message explaining why the request was rejected
     */
    public RequestRejectedException(String msg) {
        super(msg);
    }
}
