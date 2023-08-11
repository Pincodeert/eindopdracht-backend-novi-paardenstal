package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.exceptions.AlreadyAssignedException;
import nl.pin.paardenstal.exceptions.NotYetAssignedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception){
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

}
