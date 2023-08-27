package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.SubscriptionDto;
import nl.pin.paardenstal.dtos.SubscriptionInputDto;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.services.EnrollmentService;
import nl.pin.paardenstal.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final EnrollmentService enrollmentService;

    //@Autowired
    public SubscriptionController(SubscriptionService subscriptionService, EnrollmentService enrollmentService){
        this.subscriptionService = subscriptionService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions(){
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptionDtos);
    }

    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable Long id){
        SubscriptionDto subscriptionDto = subscriptionService.getSubscription(id);
        return ResponseEntity.ok(subscriptionDto);
    }

    //laat voor een bepaald Abonnement(Subscription) alle (zowel lopende als beëindigde) inschrijvingen (Enrollement) zien.
    @GetMapping("/subscriptions/{id}/enrollments")
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollmentsBySubscriptionId(@PathVariable Long id) {
        List<EnrollmentDto> enrollments = enrollmentService.getAllEnrollmentsBySubscriptionId(id);
        return ResponseEntity.ok(enrollments);
    }

    //laat voor een bepaald Abonnement(Subscription) alle lopende inschrijvingen(Enrollment) zien.
    @GetMapping("subscriptions/enrollments/{subscriptionId}")
    public ResponseEntity<List<EnrollmentDto>> getOngoingEnrollmentsBySubscriptionId(@PathVariable("subscriptionId")
                                                                                     Long subscriptionId) {
        List<EnrollmentDto> enrollments = enrollmentService.getOngoingEnrollmentsBySubscriptionId(subscriptionId);
        return ResponseEntity.ok(enrollments);
    }
    @PostMapping("/subscriptions")
    public ResponseEntity<Object> addSubscription(@Valid @RequestBody SubscriptionInputDto subscriptionInputDto,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            Long newId = subscriptionService.addSubscription(subscriptionInputDto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newId).toUri();

            return ResponseEntity.created(location).build();
        }
    }

    @PatchMapping("/subscriptions/{id}")
    public ResponseEntity<Object> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionInputDto inputDto) {
        subscriptionService.updateSubscription(id, inputDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable Long id){
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }



}
