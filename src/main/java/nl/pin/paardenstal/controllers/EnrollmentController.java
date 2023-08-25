package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.EnrollmentInputDto;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
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

    @DeleteMapping("/enrollments/{id}")
    public ResponseEntity<Object> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Object> assignCustomerToSubscription(@RequestBody EnrollmentInputDto input) {
        Long newId = enrollmentService.assignCustomerToSubscription(input.subscriptionId, input.customerId, input.horseId, input.date);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/enrollments/{id}")
    public ResponseEntity<Object> askForCancellation(@PathVariable Long id) {
        enrollmentService.askForCancellation(id);
        return ResponseEntity.noContent().build();
    }

    //dit klopt niet. input is SubscriptionId, maar wenselijk is EnrollmentId. De service methode werkt wel met EnrollmentId.
    // of wel aan de EnrollmentInputDto een EnrollmentId toevoegen, of wel deze methode via update-endpoint in de
    // zdezelfde service methode opnemen
    /*@PatchMapping("/enrollments")
    public ResponseEntity<Object> terminateSubscription(@RequestBody EnrollmentInputDto input) {
        enrollmentService.terminateSubscription(input.subscriptionId);
        return ResponseEntity.noContent().build();
    }*/

    // dit pad bestaat al. (askForCancellation). wat nu?
    @PutMapping("/enrollments/{id}")
    public ResponseEntity<Object> updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentInputDto enrollmentInputDto) {
        enrollmentService.updateEnrollment(id, enrollmentInputDto);
        return ResponseEntity.noContent().build();
    }

}



