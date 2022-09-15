package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    List<Subscription> subscriptions = new ArrayList<>();

    @GetMapping("/subscriptions")
    public ResponseEntity<List<Subscription>> getAllSubscriptions(){
        subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable long id){
        Subscription subscription = subscriptionService.getSubscription(id);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<Long> addSubscription(@RequestBody Subscription subscription){
        long newId = subscriptionService.addSubscription(subscription);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location). build();
    }

    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable long id){
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

}
