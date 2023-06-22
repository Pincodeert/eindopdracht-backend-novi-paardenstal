package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController (EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/enrollments/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Object> addNewEnrollment(@RequestBody Enrollment enrollment) {
        long newId = enrollmentService.addNewEnrollment(enrollment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/enrollments/{id}")
    public ResponseEntity<Object> deleteEnrollment(@PathVariable long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enrollments/{id}")
    public ResponseEntity<Object> updateEnrollment(@PathVariable long id, @RequestBody Enrollment enrollment) {
        enrollmentService.updateEnrollment(id, enrollment);
        return ResponseEntity.noContent().build();
    }
}
