package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.SubscriptionDto;
import nl.pin.paardenstal.dtos.SubscriptionInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
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

    public SubscriptionDto getSubscription(long id){
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);

        if(optionalSubscription.isPresent()){
            SubscriptionDto dto = transferToSubscriptionDto(optionalSubscription.get());
            return dto;
        } else {
            throw new RecordNotFoundException("This id does not exist.");
        }
    }

    public long addSubscription(SubscriptionInputDto subscriptionInputDto){
        Subscription newSubscription = transferToSubscription(subscriptionInputDto);
                subscriptionRepository.save(newSubscription);
        long newId = newSubscription.getId();
        return newId;
    }

    public void deleteSubscription(long id){
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);

        if(optionalSubscription.isPresent()){
            subscriptionRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("no subsription known by this ID");
        }

    }

    public SubscriptionDto transferToSubscriptionDto(Subscription subscription){
        SubscriptionDto dto = new SubscriptionDto();

        dto.setId(subscription.getId());
        dto.setPrice(subscription.getPrice());
        dto.setTypeOfCare(subscription.getTypeOfCare());
        dto.setTypeOfStall(subscription.getTypeOfStall());

        return dto;
    }

    public Subscription transferToSubscription(SubscriptionInputDto subscriptionInputDto){
        Subscription subscription = new Subscription();

        subscription.setPrice(subscriptionInputDto.getPrice());
        subscription.setTypeOfCare(subscriptionInputDto.getTypeOfCare());
        subscription.setTypeOfStall(subscriptionInputDto.getTypeOfStall());

        return subscription;
    }

}
