package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.EnrollmentInputDto;
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
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {
        List<EnrollmentDto> dtos = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/enrollments/{id}")
    public ResponseEntity<EnrollmentDto> getEnrollmentById(@PathVariable long id) {
        EnrollmentDto dto = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Object> addNewEnrollment(@RequestBody EnrollmentInputDto enrollmentInputDto) {
        long newId = enrollmentService.addNewEnrollment(enrollmentInputDto);

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
    public ResponseEntity<Object> updateEnrollment(@PathVariable long id, @RequestBody EnrollmentInputDto enrollmentInputDto) {
        enrollmentService.updateEnrollment(id, enrollmentInputDto);
        return ResponseEntity.noContent().build();
    }
}
