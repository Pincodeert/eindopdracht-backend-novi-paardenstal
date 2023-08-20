package nl.pin.paardenstal.exceptions;

public class NotYetRemovedException extends RuntimeException {

    public NotYetRemovedException() {
        super();
    }

    public NotYetRemovedException(String message) {
        super(message);
    }
}
