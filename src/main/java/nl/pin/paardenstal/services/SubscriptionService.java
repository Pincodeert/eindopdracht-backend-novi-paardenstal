package nl.pin.paardenstal.services;

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

    List<Subscription> subscriptions = new ArrayList<>();

    public List<Subscription> getAllSubscriptions(){
        subscriptions = subscriptionRepository.findAll();
        return subscriptions;
    }

    public Subscription getSubscription(long id){
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);

        if(optionalSubscription.isPresent()){
            return optionalSubscription.get();
        } else {
            throw new RecordNotFoundException("This id does not exist.");
        }
    }

    public long addSubscription(Subscription subscription){
        Subscription newSubscription = subscriptionRepository.save(subscription);
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

}
