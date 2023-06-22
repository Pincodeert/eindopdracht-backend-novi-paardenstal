package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.EnrollmentInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<EnrollmentDto> getAllEnrollments() {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        for(Enrollment e: enrollments) {
            EnrollmentDto dto = transferToDto(e);
            dtos.add(dto);
        }
        return dtos;
    }

    public EnrollmentDto getEnrollmentById(long id) {

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()){
            Enrollment storedEnrollment = optionalEnrollment.get();
            EnrollmentDto dto = transferToDto(storedEnrollment);
            return dto;
        } else {
            throw new RecordNotFoundException("This Id does not exist");
        }
    }

    public long addNewEnrollment(EnrollmentInputDto enrollmentInputDto) {
        Enrollment enrollment = transferToEnrollment(enrollmentInputDto);
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

    //regelt en wijzigt de annulering van de inschrijving van een abonnement
    public void updateEnrollment (long id, EnrollmentInputDto enrollmentInputDto) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()) {
            Enrollment storedEnrollment = optionalEnrollment.get();
            //weet niet of we de startDatum willen kunnen veranderen?
            storedEnrollment.setStartDate(enrollmentInputDto.getStartDate());
            storedEnrollment.setExpireDate(enrollmentInputDto.getExpireDate());
            //willen we de duration kunnen aanpassen? eerder een bereking in de Service-laag
            storedEnrollment.setDuration(enrollmentInputDto.getDuration());
            storedEnrollment.setOngoing(enrollmentInputDto.isOngoing());
            storedEnrollment.setCancellationRequested(enrollmentInputDto.isCancellationRequested());
            enrollmentRepository.save(storedEnrollment);
        } else {
            throw new RecordNotFoundException("This Id dose not exist");
        }
    }

    //zet een Enrollment Object om in een EnrollmentDto object
    public EnrollmentDto transferToDto(Enrollment enrollment) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(enrollment.getId());
        dto.setStartDate(enrollment.getStartDate());
        dto.setExpireDate(enrollment.getExpireDate());
        dto.setDuration(enrollment.getDuration());
        dto.setOngoing(enrollment.isOngoing());
        dto.setCancellationRequested(enrollment.isCancellationRequested());
        return dto;
    }

    public Enrollment transferToEnrollment(EnrollmentInputDto enrollmentInputDto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStartDate(enrollmentInputDto.getStartDate());
        enrollment.setExpireDate(enrollmentInputDto.getExpireDate());
        //hoort duration attribuut eigenlijk wel in de InputDto? is eerder een attribuut van entititeit dat berekend
        // wordt in de EnrollmentService, ipv wordt meegegeven in de PostRequest?!
        enrollment.setDuration(enrollmentInputDto.getDuration());
        enrollment.setOngoing(enrollmentInputDto.isOngoing());
        enrollment.setCancellationRequested(enrollmentInputDto.isCancellationRequested());
        return enrollment;
    }

}
