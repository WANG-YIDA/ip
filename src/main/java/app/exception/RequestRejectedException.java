package app.exception;

public class RequestRejectedException extends Exception {
    public RequestRejectedException(String msg) {
        super(msg);
    }
}
