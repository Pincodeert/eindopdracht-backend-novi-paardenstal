package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.CustomerProfileDto;
import nl.pin.paardenstal.dtos.CustomerProfileInputDto;
import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.IdInputDto;
import nl.pin.paardenstal.services.CustomerProfileService;
import nl.pin.paardenstal.services.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;
    private final EnrollmentService enrollmentService;


    public CustomerProfileController(CustomerProfileService customerProfileService,
                                     EnrollmentService enrollmentService) {
        this.customerProfileService = customerProfileService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/customerprofiles")
    public ResponseEntity<List<CustomerProfileDto>> getAllCustomerProfiles() {
        List<CustomerProfileDto> dtos = customerProfileService.getAllCustomerProfiles();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/customerprofiles/{id}")
    public ResponseEntity<CustomerProfileDto> getCustomerProfile(@PathVariable Long id) {
        CustomerProfileDto dto = customerProfileService.getCustomerProfile(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/customerprofiles/{id}/enrollments")
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollmentsByCustomerProfileId(@PathVariable Long id) {
        List<EnrollmentDto> enrollments = enrollmentService.getAllEnrollmentsByCustomerProfileId(id);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("customerprofiles/enrollments/{customerProfileId}")
    public ResponseEntity<BigDecimal> getTotalPriceOfAssignedSubscriptions(@PathVariable("customerProfileId")
                                                                           Long customerProfileId) {
        BigDecimal totalPrice = enrollmentService.getTotalPriceOfAssignedSubscriptionsByCustomerId(customerProfileId);
        return ResponseEntity.ok(totalPrice);
    }

    @PostMapping("/customerprofiles")
    public ResponseEntity<Object> createNewCustomerProfile(@Valid @RequestBody CustomerProfileInputDto customerInputDto,
                                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {

            Long newId = customerProfileService.createNewCustomerProfile(customerInputDto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newId).toUri();

            return ResponseEntity.created(location).build();
        }
    }

    @PatchMapping("/customerprofiles/{id}")
    public ResponseEntity<Object> updateCustomerProfile(@PathVariable Long id, @Valid
                                                               @RequestBody CustomerProfileInputDto inputDto,
                                                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            customerProfileService.updateCustomerProfile(id, inputDto);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/customerprofiles/{id}")
    public ResponseEntity<Object> deleteCustomerProfile(@PathVariable Long id) {
        customerProfileService.deleteCustomerProfile(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customerprofiles/{id}/user")
    public ResponseEntity<Object> assignUserToCustomerProfile(@PathVariable Long id, @RequestBody IdInputDto userInput){
        customerProfileService.assignUserToCustomerProfile(id, userInput.username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customerprofiles/{id}/users/{username}")
    public ResponseEntity<Object> removeUserFromCustomerProfile(@PathVariable ("id") Long customerId,
                                                                @PathVariable ("username") String username) {
        customerProfileService.removeUserFromCustomerProfile(customerId,username);
        return ResponseEntity.noContent().build();
    }

}
