package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = AlreadyAssignedException.class)
    public ResponseEntity<Object> exception(AlreadyAssignedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NotYetAssignedException.class)
    public ResponseEntity<Object> exception(NotYetAssignedException notYetAssignedException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notYetAssignedException.getMessage());
    }

    @ExceptionHandler(value = NotYetRemovedException.class)
    public ResponseEntity<Object> exception(NotYetRemovedException notYetRemovedException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notYetRemovedException.getMessage());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> exception(BadRequestException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<String> exception(UsernameNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EnrollmentIsOngoingException.class)
    public ResponseEntity<String> exception(EnrollmentIsOngoingException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> exception(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
