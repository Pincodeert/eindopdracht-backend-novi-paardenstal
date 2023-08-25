package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.SubscriptionDto;
import nl.pin.paardenstal.dtos.SubscriptionInputDto;
import nl.pin.paardenstal.exceptions.NotYetRemovedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.repositories.EnrollmentRepository;
import nl.pin.paardenstal.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final EnrollmentService enrollmentService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               @Lazy EnrollmentService enrollmentService){
        this.subscriptionRepository = subscriptionRepository;
        this.enrollmentService = enrollmentService;
    }

    public List<SubscriptionDto> getAllSubscriptions(){
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionDto> dtos = new ArrayList<>();

        for(Subscription s: subscriptions){
            SubscriptionDto dto = transferToSubscriptionDto(s);
            dtos.add(dto);
        }
        return dtos;
    }

    public SubscriptionDto getSubscription(Long id){
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);

        if(optionalSubscription.isPresent()){
            SubscriptionDto dto = transferToSubscriptionDto(optionalSubscription.get());
            return dto;
        } else {
            throw new RecordNotFoundException("This id does not exist.");
        }
    }

    public Long addSubscription(SubscriptionInputDto subscriptionInputDto){
        Subscription newSubscription = transferToSubscription(subscriptionInputDto);
                subscriptionRepository.save(newSubscription);
        Long newId = newSubscription.getId();
        return newId;
    }

    //delete een subscription op voorwaarde dat het geen lopende abonnementen (Enrollments met isOngoing = true) (meer)
    // bevat. Als het wel nog lopende abonnementen bevat geeft het een melding hiervan.
    public void deleteSubscription(Long id){
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);

        if(optionalSubscription.isPresent()){
            Subscription subscription = optionalSubscription.get();
            //Geeft voor deze subscriptionId een lijst met alle lopende enrollments
            List<EnrollmentDto> allOngoingEnrollments = enrollmentService.getOngoingEnrollmentsBySubscriptionId(id);
            //geeft voor deze subscriptionId een lijst met alle enrollments
            List<EnrollmentDto> allEnrollments = enrollmentService.getAllEnrollmentsBySubscriptionId(id);

            if(subscription.getEnrollments().size() > 0) {
                if((allOngoingEnrollments.size()) > 0) {
                    throw new NotYetRemovedException("kan niet deleten, vanwege nog lopende abonnementen");
                } else if ((allEnrollments.size() - allOngoingEnrollments.size()) > 0) {
                    for(EnrollmentDto e: allEnrollments) {
                        enrollmentService.removeSubscriptionFromEnrollment(e.getId());
                    }
                }
            }
            subscriptionRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("no subsription known by this ID");
        }
    }

    public /*static*/ SubscriptionDto transferToSubscriptionDto(Subscription subscription){
        SubscriptionDto dto = new SubscriptionDto();

        dto.setId(subscription.getId());
        dto.setName(subscription.getName());
        dto.setPrice(subscription.getPrice());
        dto.setTypeOfCare(subscription.getTypeOfCare());
        dto.setTypeOfStall(subscription.getTypeOfStall());

        return dto;
    }

    public Subscription transferToSubscription(SubscriptionInputDto subscriptionInputDto){
        Subscription subscription = new Subscription();

        subscription.setName(subscriptionInputDto.getName());
        subscription.setPrice(subscriptionInputDto.getPrice());
        subscription.setTypeOfCare(subscriptionInputDto.getTypeOfCare());
        subscription.setTypeOfStall(subscriptionInputDto.getTypeOfStall());

        return subscription;
    }



}
