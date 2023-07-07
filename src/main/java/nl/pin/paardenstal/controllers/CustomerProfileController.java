package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.CustomerProfileDto;
import nl.pin.paardenstal.dtos.CustomerProfileInputDto;
import nl.pin.paardenstal.dtos.EnrollmentDto;
import nl.pin.paardenstal.dtos.IdInputDto;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.services.CustomerProfileService;
import nl.pin.paardenstal.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;
    private final EnrollmentService enrollmentService;

    @Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService,
                                     EnrollmentService enrollmentService){
        this.customerProfileService = customerProfileService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/customerprofiles")
    public ResponseEntity<List<CustomerProfileDto>> getAllCustomerProfiles(){
        List<CustomerProfileDto> dtos = customerProfileService.getAllCustomerProfiles();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/customerprofiles/{id}")
    public ResponseEntity<CustomerProfileDto> getCustomerProfile(@PathVariable long id){
        CustomerProfileDto dto = customerProfileService.getCustomerProfile(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/customerprofiles")
    public ResponseEntity<Long> createNewCustomerProfile(@RequestBody CustomerProfileInputDto customerProfileInputDto){
        long newId = customerProfileService.createNewCustomerProfile(customerProfileInputDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/customerprofiles/{id}")
    public ResponseEntity<Object> deleteCustomerProfile(@PathVariable long id){
        customerProfileService.deleteCustomerProfile(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customerprofiles/{id}")
    public ResponseEntity<Object> updateCustomerProfile(@PathVariable long id, @RequestBody CustomerProfileInputDto
            inputDto){
        customerProfileService.updateCustomerProfile(id, inputDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/customerprofiles/{id}")
    public ResponseEntity<Object> partialUpdateCustomerProfile(@PathVariable long id,
                                                               @RequestBody CustomerProfileInputDto inputDto){
        customerProfileService.partialUpdateCustomerProfile(id, inputDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customerprofiles/{id}/user")
    public ResponseEntity<Object> assignUserToCustomerProfile(@PathVariable long id, @RequestBody IdInputDto userInput){
        customerProfileService.assignUserToCustomerProfile(id, userInput.id);
        return ResponseEntity.noContent().build();
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
}
