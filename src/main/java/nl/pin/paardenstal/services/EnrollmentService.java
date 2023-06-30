package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.EnrollmentInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import nl.pin.paardenstal.repositories.EnrollmentRepository;
import nl.pin.paardenstal.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final CustomerProfileRepository customerProfileRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             SubscriptionRepository subscriptionRepository,
                             CustomerProfileRepository customerProfileRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.customerProfileRepository = customerProfileRepository;
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

    // zorgt ervoor dat alle verzoeken tot annulering van het abonnement kunnen worden opgevraagd.
    public List<EnrollmentDto> getAllCancellationRequests(boolean cancellationRequested) {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository
                .findAllEnrollmentsByCancellationRequested(cancellationRequested);
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

    // het deleten van een enrollment is over het algemeen niet wenselijk. Wel deze functionaliteit gemaakt, om het
    // mogelijk te maken een enrollment-object te deleten wanneer het per ongeluk is aangemaakt met een verkeerde
    // subscription Id?
    public void deleteEnrollment(long id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()){
            enrollmentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Er bestaat geen inschrijving/abonnement met deze Id");
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


    // zorgt ervoor dat een klant voor een bepaald abonnement wordt ingeschreven en  en waarbij er een inschrijving
    // (enrollment-object) wordt aangemaakt waarbij deze twee aan elkaar gekoppeld zijn en de datum van inschrijving
    // wordt toegevoegd
    public Long assignCustomerToSubscription(Long subscriptionId, Long customerId, String date) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(customerId);

        if (optionalSubscription.isPresent() && optionalCustomerProfile.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            CustomerProfile customer = optionalCustomerProfile.get();

            Enrollment enrollment = new Enrollment(subscription, customer, date);
            Enrollment newEnrollment = enrollmentRepository.save(enrollment);

            Long newId = newEnrollment.getId();
            return newId;

        } else if (!optionalSubscription.isPresent() && !optionalCustomerProfile.isPresent()) {
            throw new RecordNotFoundException("There's no subscruption nor customer with this ID");
        } else if (!optionalSubscription.isPresent()) {
            throw new RecordNotFoundException("There's no subscription with this ID");
            // De if-statement hier weggehaald, omdat anders om return-waarde blijft vragen.
        } else //if (!optionalCustomerProfile.isPresent())
        {
            throw new RecordNotFoundException("There's no customer with this ID");
        }
        //return null;
    }

    // regelt dat er een verzoek tot annulering gedaan kan worden
    public void askForCancellation(Long enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);

        if(optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollment.setCancellationRequested(true);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RecordNotFoundException("Er bestaat geen abonnement met deze Id");
        }
    }

    // regelt dat de inschrijving op een bepaald abonnement wordt stopgezet
    public void terminateSubscription(Long enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);

        if(optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollment.setOngoing(false);
            enrollment.setCancellationRequested(false);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RecordNotFoundException("Er bestaat geen abonnement met deze Id");
        }
    }

}


