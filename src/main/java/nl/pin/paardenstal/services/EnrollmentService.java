package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.EnrollmentInputDto;
import nl.pin.paardenstal.exceptions.*;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import nl.pin.paardenstal.repositories.EnrollmentRepository;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionService subscriptionService;
    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerProfileService customerProfileService;
    private final HorseRepository horseRepository;
    private final HorseService horseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             SubscriptionRepository subscriptionRepository,
                             SubscriptionService subscriptionService,
                             CustomerProfileRepository customerProfileRepository,
                             CustomerProfileService customerProfileService, HorseRepository horseRepository, HorseService horseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
        this.customerProfileRepository = customerProfileRepository;
        this.customerProfileService = customerProfileService;
        this.horseRepository = horseRepository;
        this.horseService = horseService;
    }

    public List<EnrollmentDto> getAllEnrollments() {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        for(Enrollment e: enrollments) {
            EnrollmentDto dto = transferToDto(e);
            if(e.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(e.getCustomer()));
            }
            if(e.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(e.getSubscription()));
            }
            dtos.add(dto);
        }
        return dtos;
    }

    // zorgt ervoor dat alle verzoeken tot annulering van het abonnement kunnen worden opgevraagd.
    public List<EnrollmentDto> getAllCancellationRequests(boolean cancellationRequested) {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findAllByCancellationRequested(cancellationRequested);
        for(Enrollment e: enrollments) {
            EnrollmentDto dto = transferToDto(e);
            if(e.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(e.getCustomer()));
            }
            if(e.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(e.getSubscription()));
            }
            if(e.getHorse() != null) {
                dto.setHorse(horseService.transferToDto(e.getHorse()));
            }
            dtos.add(dto);
        }
        return dtos;
    }

    //zorgt ervoor dat alle lopende inschrijvingen op een abonnement worden opgehaald
    public List<EnrollmentDto> getAllOngoingEnrollments(boolean isOngoing) {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> ongoingEnrollments = enrollmentRepository.findAllByIsOngoing(isOngoing);

        for(Enrollment e: ongoingEnrollments) {
            EnrollmentDto dto = transferToDto(e);

            if(e.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(e.getCustomer()));
            }
            if(e.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(e.getSubscription()));
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public EnrollmentDto getEnrollmentById(Long id) {

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()){
            Enrollment storedEnrollment = optionalEnrollment.get();
            EnrollmentDto dto = transferToDto(storedEnrollment);
            if(storedEnrollment.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(storedEnrollment.getCustomer()));
            }
            if(storedEnrollment.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(storedEnrollment.getSubscription()));
            }
            return dto;
        } else {
            throw new RecordNotFoundException("This Id does not exist");
        }
    }

    //zorgt ervoor dat per klant al zijn/haar inschrijvingen op een abonnement worden opgehaald
    public List<EnrollmentDto> getAllEnrollmentsByCustomerProfileId(Long customerProfileId) {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findAllByCustomerProfileId(customerProfileId);

        for(Enrollment e: enrollments) {
            EnrollmentDto dto = transferToDto(e);
            if(e.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(e.getSubscription()));
            }
            if(e.getHorse() != null) {
                dto.setHorse(horseService.transferToDto(e.getHorse()));
            }
            dtos.add(dto);
        }
        return dtos;
    }

    //zorgt ervoor dat per abonnement de inschrijvingen hierop worden opgehaald.
    public List<EnrollmentDto> getAllEnrollmentsBySubscriptionId(Long subcriptionId) {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findAllBySubscriptionId(subcriptionId);

        for(Enrollment e: enrollments) {
            EnrollmentDto dto = transferToDto(e);

            if(e.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(e.getCustomer()));
            }
            dtos.add(dto);
        }
        return dtos;
    }

    //Zorgt ervoor dat per abonnement alleen de lopende inschrijving worden opgehaald.
    public List<EnrollmentDto> getOngoingEnrollmentsBySubscriptionId(Long subscriptionId) {
        List<EnrollmentDto> ongoingEnrollments = new ArrayList<>();
        List<EnrollmentDto> enrollments = getAllEnrollmentsBySubscriptionId(subscriptionId);

        for(EnrollmentDto ed: enrollments) {
            if(ed.isOngoing()) {
                ongoingEnrollments.add(ed);
            }
        }
        return ongoingEnrollments;
    }

    //berekent en geeft terug de totale prijs van de inschrijvingen op een abonnement van een specifieke klant
    public BigDecimal getTotalPriceOfAssignedSubscriptionsByCustomerId(Long customerProfileId) {
        double totalPrice = 0;
        List<Enrollment> enrollment = enrollmentRepository.findAllByCustomerProfileId(customerProfileId);

        for(Enrollment e: enrollment) {
            double price = e.getSubscription().getPrice();
            totalPrice += price;
        }
        BigDecimal roundedPrice = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
        return roundedPrice;
    }

    // zorgt ervoor dat een nieuwe inschrijving wordt aangemaakt en dat deze gelijk aan een abonnement(subscription),
    // klant(customerProfile) en paard(Horse) gekoppeld wordt en dat de ingangs- en verloopdatum worden vastgelegd.
    public Long createNewEnrollment(Long subscriptionId, Long customerId, Long horseId, String date) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(customerId);
        Optional<Horse> optionalHorse = horseRepository.findById(horseId);

        if (optionalSubscription.isPresent() && optionalCustomerProfile.isPresent() && optionalHorse.isPresent()){
            Subscription subscription = optionalSubscription.get();
            CustomerProfile customer = optionalCustomerProfile.get();
            Horse horse = optionalHorse.get();

            //zorgt ervoor dat de applicatie niet vastloopt, wanneer (per ongeluk) een horseId wordt opgegeven dat al
            // gekoppeld is aan een enrollment
            if(horse.getEnrollment() != null) {
                throw new AlreadyAssignedException("This horse already has a subscription");
            } //zorgt ervoor dat een paard altijd gekoppeld moet zijn aan een stal om een abonnement te kunnen afsluiten:
            else if (horse.getStall() == null) {
                throw new NotYetAssignedException("Horse must be assigned to a stall first");
            } else if(date != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                LocalDate futureDate = LocalDate.parse(date, formatter);
                Enrollment enrollment = new Enrollment(subscription, customer, horse, futureDate);
                Enrollment newEnrollment = enrollmentRepository.save(enrollment);

                Long newId = newEnrollment.getId();
                return newId;
            } else {
                Enrollment enrollment = new Enrollment(subscription, customer, horse);
                Enrollment newEnrollment = enrollmentRepository.save(enrollment);

                Long newId = newEnrollment.getId();
                return newId;
            }
        } else if (!optionalSubscription.isPresent()) {
            throw new RecordNotFoundException("There's no subscruption with this ID");
        } else if (!optionalCustomerProfile.isPresent()){
            throw new RecordNotFoundException("There's no customer with this ID");
        } else if (!optionalSubscription.isPresent()) {
            throw new RecordNotFoundException("There's no subscription with this ID");
        } else if (!optionalHorse.isPresent()) {
            throw new RecordNotFoundException("There's no horse with this ID");
            // De if-statement hier weggehaald, omdat anders om return-waarde blijft vragen.
        } else
        {
            throw new RecordNotFoundException("There's no customer with this ID");
        }

    }

    public void deleteEnrollment(Long id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()){
            Enrollment enrollment = optionalEnrollment.get();
            if(enrollment.isOngoing()) {
                throw new EnrollmentIsOngoingException("can't delete this enrollment; it is still ongoing");
            }
            if(enrollment.getSubscription() != null) {
                throw new NotYetRemovedException("first remove subscription from enrollment");
            }
            if(enrollment.getCustomer() != null) {
                throw new NotYetRemovedException("first remove customer from enrollment");
            }
            enrollmentRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No enrollment known by this ID");
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
        dto.setHorseNumber(enrollment.getHorseNumber());
        dto.setSubscriptionPrice(enrollment.getSubcriptionPrice());
        dto.setCustomerNumber(enrollment.getCustomerNumber());
        return dto;
    }

    // regelt dat er een verzoek tot annulering gedaan kan worden
    public void askForCancellation(Long enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);

        if(optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollment.setCancellationRequested(true);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RecordNotFoundException("No enrollment known by this ID");
        }
    }

    // regelt dat de inschrijving op een bepaald abonnement wordt stopgezet
    public void terminateSubscription(Long enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);

        if(optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollment.setOngoing(false);
            enrollment.setCancellationRequested(false);
            enrollment.setHorse(null);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RecordNotFoundException("No enrollment known by this ID");
        }
    }


    public void updateEnrollment(Long id, EnrollmentInputDto enrollmentInputDto) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);

        if(!optionalEnrollment.isPresent()) {
            throw new RecordNotFoundException("this id is unknown");
        } else {
            Enrollment enrollment = optionalEnrollment.get();

            if(enrollmentInputDto.isOngoing) {
                terminateSubscription(id);
            }
            if(enrollmentInputDto.subscriptionId != null && !enrollmentInputDto.subscriptionId.describeConstable().isEmpty()) {
                removeSubscriptionFromEnrollment(id);
            }
            if(enrollmentInputDto.customerId != null && !enrollmentInputDto.customerId.describeConstable().isEmpty()) {
                removeCustomerProfileFromEnrollment(id);
            }
        }
    }

    public void removeSubscriptionFromEnrollment(Long id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);

        if(optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollment.setSubscription(null);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RecordNotFoundException("this id is unknown");
        }
    }

    public void removeCustomerProfileFromEnrollment(Long id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);

        if(optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollment.setCustomer(null);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RecordNotFoundException("this id is unknown");
        }
    }

}


