package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.SubscriptionDto;
import nl.pin.paardenstal.dtos.SubscriptionInputDto;
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

    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions(){
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptionDtos);
    }

    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable long id){
        SubscriptionDto subscriptionDto = subscriptionService.getSubscription(id);
        return ResponseEntity.ok(subscriptionDto);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<Long> addSubscription(@RequestBody SubscriptionInputDto subscriptionInputDto){
        long newId = subscriptionService.addSubscription(subscriptionInputDto);

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
