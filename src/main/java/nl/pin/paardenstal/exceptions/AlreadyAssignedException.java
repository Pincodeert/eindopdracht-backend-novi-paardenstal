package nl.pin.paardenstal.exceptions;

public class AlreadyAssignedException extends RuntimeException{

    public AlreadyAssignedException(){
        super();
    }

    public AlreadyAssignedException(String message) {
        super(message);
    }
}
