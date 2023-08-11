package nl.pin.paardenstal.exceptions;

public class NotYetAssignedException extends RuntimeException{

    public NotYetAssignedException() {
        super();
    }

    public NotYetAssignedException(String message) {
        super(message);
    }
}
