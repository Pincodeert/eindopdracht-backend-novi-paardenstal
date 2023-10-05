package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.EnrollmentInputDto;
import nl.pin.paardenstal.services.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;


    public EnrollmentController (EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments(@RequestParam(value = "cancellationRequested",
            required = false) Optional<Boolean> cancellationRequested, @RequestParam(value = "isOngoing",
            required = false) Optional<Boolean> isOngoing) {
        List<EnrollmentDto> dtos = enrollmentService.getAllEnrollments();

        if(cancellationRequested.isEmpty() && isOngoing.isEmpty()) {
            dtos = enrollmentService.getAllEnrollments();
        } else if(isOngoing.isEmpty()) {
            dtos = enrollmentService.getAllCancellationRequests(cancellationRequested.get());
        } else {
            dtos = enrollmentService.getAllOngoingEnrollments(isOngoing.get());
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/enrollments/{id}")
    public ResponseEntity<EnrollmentDto> getEnrollmentById(@PathVariable Long id) {
        EnrollmentDto dto = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Object> createNewEnrollment(@RequestBody EnrollmentInputDto input) {
        Long newId = enrollmentService.createNewEnrollment(input.subscriptionId, input.customerId, input.horseId, input.date);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/enrollments/{id}")
    public ResponseEntity<Object> askForCancellation(@PathVariable Long id) {
        enrollmentService.askForCancellation(id);
        return ResponseEntity.noContent().build();
    }

    //afhankelijk van de meegegeven inhoud in de enrollmentInputDto wordt in de servicelaag de subscription op geëindigd
    // gezet of de subscription van de enrollment verwijderd.
    @PutMapping("/enrollments/{id}")
    public ResponseEntity<Object> updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentInputDto enrollmentInputDto) {
        enrollmentService.updateEnrollment(id, enrollmentInputDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/enrollments/{id}")
    public ResponseEntity<Object> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}



