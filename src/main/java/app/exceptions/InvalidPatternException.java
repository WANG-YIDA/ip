package app.exceptions;

/**
 * Thrown when a user input pattern does not match the expected format.
 */
public class InvalidPatternException extends Exception {
    /**
     * Constructs the exception with a descriptive message.
     *
     * @param msg the detail message explaining the invalid pattern
     */
    public InvalidPatternException(String msg) {
        super(msg);
    }
}
