package nl.pin.paardenstal.exceptions;

public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException(String username) {
        super("Cannot find user " + username);
    }
}
