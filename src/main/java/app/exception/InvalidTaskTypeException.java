package app.exception;

public class InvalidTaskTypeException extends Exception {
    public InvalidTaskTypeException(String msg) {
        super(msg);
    }
}
