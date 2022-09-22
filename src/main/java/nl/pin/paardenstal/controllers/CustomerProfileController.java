package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.services.CustomerProfileService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;

    public CustomerProfileController(CustomerProfileService customerProfileService){
        this.customerProfileService = customerProfileService;
    }

    @GetMapping("/customerprofiles")
    public ResponseEntity<List<CustomerProfile>> getAllCustomerProfiles(){
        List<CustomerProfile> customerProfiles = customerProfileService.getAllCustomerProfiles();
        return ResponseEntity.ok(customerProfiles);
    }

    @GetMapping("/customerprofiles/{id}")
    public ResponseEntity<CustomerProfile> getCustomerProfile(@PathVariable long id){
        CustomerProfile customerProfile = customerProfileService.getCustomerProfile(id);
        return ResponseEntity.ok(customerProfile);
    }

    @PostMapping("/customerprofiles")
    public ResponseEntity<Long> createNewCustomerProfile(@RequestBody CustomerProfile customerProfile){
        long newId = customerProfileService.createNewCustomerProfile(customerProfile);

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
    public ResponseEntity<Object> updateCustomerProfile(@PathVariable long id, @RequestBody CustomerProfile
            customerProfile){
        customerProfileService.updateCustomerProfile(id, customerProfile);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/customerprofiles/{id}")
    public ResponseEntity<Object> partialUpdateCustomerProfile(@PathVariable long id, @RequestBody CustomerProfile
            customerProfile){
        customerProfileService.partialUpdateCustomerProfile(id, customerProfile);
        return ResponseEntity.noContent().build();
    }

}
