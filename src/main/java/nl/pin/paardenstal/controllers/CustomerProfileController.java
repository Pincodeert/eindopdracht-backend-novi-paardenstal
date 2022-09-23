package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.CustomerProfileDto;
import nl.pin.paardenstal.dtos.CustomerProfileInputDto;
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

}
