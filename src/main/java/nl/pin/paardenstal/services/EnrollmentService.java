package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.EnrollmentInputDto;
import nl.pin.paardenstal.exceptions.AlreadyAssignedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import nl.pin.paardenstal.repositories.EnrollmentRepository;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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

    @Autowired
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
            // in theorie kan enrollment.getCustomer() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een customer te koppelen. dus if-statement hier in principe niet nodig?
            if(e.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(e.getCustomer()));
            }
            // in theorie kan enrollment.getSubscription() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een subscription te koppelen. dus if-statement hier in principe niet nodig?
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
            // in theorie kan enrollment.getCustomer() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een customer te koppelen. dus if-statement hier in principe niet nodig?
            if(e.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(e.getCustomer()));
            }
            // in theorie kan enrollment.getSubscription() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een subscription te koppelen. dus if-statement hier in principe niet nodig?
            if(e.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(e.getSubscription()));
            }
            dtos.add(dto);
        }
        return dtos;
    }

    //zorgt ervoor dat alle lopende abonnementen worden opgehaald
    public List<EnrollmentDto> getAllOngoingEnrollments(boolean isOngoing) {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> ongoingEnrollments = enrollmentRepository.findAllByIsOngoing(isOngoing);

        for(Enrollment e: ongoingEnrollments) {
            EnrollmentDto dto = transferToDto(e);
            //omdat deze methode alleen gebruikt gaat worden om de totale prijs te berekenen, hoeft de customerProfile
            // niet te worden meegenomen in de dto
            //de subscription daarentegen wel.
            if(e.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(e.getSubscription()));
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public EnrollmentDto getEnrollmentById(long id) {

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if(optionalEnrollment.isPresent()){
            Enrollment storedEnrollment = optionalEnrollment.get();
            EnrollmentDto dto = transferToDto(storedEnrollment);
            // in theorie kan enrollment.getCustomer() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een customer te koppelen. dus if-statement hier in principe niet nodig?
            if(storedEnrollment.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(storedEnrollment.getCustomer()));
            }
            // in theorie kan enrollment.getSubscription() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een subscription te koppelen. dus if-statement hier in principe niet nodig?
            if(storedEnrollment.getSubscription() != null) {
                dto.setSubscription(subscriptionService.transferToSubscriptionDto(storedEnrollment.getSubscription()));
            }
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


    // zorgt ervoor dat een klant voor een bepaald paard een bepaald abonnement wordt ingeschreven en waarbij er een
    // inschrijving (enrollment-object) wordt aangemaakt waarbij deze drie aan elkaar gekoppeld zijn en de datum van
    // inschrijving wordt toegevoegd.
    public Long assignCustomerToSubscription(Long subscriptionId, Long customerId, Long horseId, String date) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(customerId);
        Optional<Horse> optionalHorse = horseRepository.findById(horseId);

        if (optionalSubscription.isPresent() && optionalCustomerProfile.isPresent() && optionalHorse.isPresent()){
            Subscription subscription = optionalSubscription.get();
            CustomerProfile customer = optionalCustomerProfile.get();
            Horse horse = optionalHorse.get();

            //zorgt ervoor dat de applicatie niet vastloopt, wanneer (per ongeluk) een horseId wordt opgegeven dat al
            // gekoppeld is aan een enrollment
            if(horse.getEnrollment() != null){
                throw new AlreadyAssignedException("This horse already has a subscription");
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

    //zorgt ervoor dat per klant al zijn/haar inschrijvingen op een abonnement worden opgehaald (en dus getoond kunnen
    // gaan worden).
    public List<EnrollmentDto> getAllEnrollmentsByCustomerProfileId(Long customerProfileId) {
        List<EnrollmentDto> dtos = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findAllByCustomerProfileId(customerProfileId);

        for(Enrollment e: enrollments) {
            EnrollmentDto dto = transferToDto(e);
            // in theorie kan enrollment.getSubscription() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een subscription te koppelen. dus if-statement hier in principe niet nodig?
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
            // in theorie kan enrollment.getCustomer() nooit null zijn omdat de constructor die we gebruiken afdwingt
            // altijd een customer te koppelen. dus if-statement hier in principe niet nodig?
            if(e.getCustomer() != null) {
                dto.setCustomer(customerProfileService.transferToDto(e.getCustomer()));
            }
            // de enrollment.getSubscription() hoeft hier niet worden toegevoegd, want we weten al van welke
            // subscription we de informatie opvragen.
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
        //default-waarde van een double is al 0. hoeven we hier dus niet apart te declareren.
        double totalPrice = 0;
        List<Enrollment> enrollment = enrollmentRepository.findAllByCustomerProfileId(customerProfileId);

        for(Enrollment e: enrollment) {
            double price = e.getSubscription().getPrice();
            totalPrice += price;
        }
        BigDecimal roundedPrice = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
        return roundedPrice;
    }

}


