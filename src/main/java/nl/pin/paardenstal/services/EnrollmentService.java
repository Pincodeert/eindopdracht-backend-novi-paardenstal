package nl.pin.paardenstal.services;

import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments;
    }

    public Enrollment getEnrollmentById(long id) {

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()){
            return optionalEnrollment.get();
        } else {
            throw new RecordNotFoundException("This Id does not exist");
        }
    }

    public long addNewEnrollment(Enrollment enrollment) {
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        long newId = savedEnrollment.getId();
        return newId;
    }

    public void deleteEnrollment(long id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()){
            enrollmentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }
    }

    //regelt en wijzigt de annuelering van de inschrijving van een abonnement
    public void updateEnrollment (long id, Enrollment enrollment) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()) {
            Enrollment updatedEnrollment = optionalEnrollment.get();
            updatedEnrollment.setOngoing(enrollment.isOngoing());
            updatedEnrollment.setCancellationRequested(enrollment.isCancellationRequested());
            enrollmentRepository.save(updatedEnrollment);
        } else {
            throw new RecordNotFoundException("This Id dose not exist");
        }
    }

}
