package nl.pin.paardenstal.exceptions;

public class EnrollmentIsOngoingException extends RuntimeException{

    public EnrollmentIsOngoingException() {
        super();
    }

    public EnrollmentIsOngoingException(String message) {
        super(message);
    }
}
