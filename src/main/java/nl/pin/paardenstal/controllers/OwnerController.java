package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.OwnerDto;
import nl.pin.paardenstal.dtos.OwnerInputDto;
import nl.pin.paardenstal.models.Owner;
import nl.pin.paardenstal.services.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService){
        this.ownerService = ownerService;
    }

    List<OwnerDto> owners = new ArrayList<>();

    @GetMapping("/owners")
    public ResponseEntity<List<OwnerDto>> getAllOwners(){
        owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable long id){
        OwnerDto ownerDto = ownerService.getOwner(id);
        return ResponseEntity.ok(ownerDto);
    }

    @PostMapping("/owners")
    public ResponseEntity<Long> addNewOwner(@RequestBody OwnerInputDto ownerInputDto){
        long newId = ownerService.createNewOwner(ownerInputDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<Object> deleteOwner(@PathVariable long id){
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<Object> updateOwner(@PathVariable long id, @RequestBody OwnerInputDto ownerInputDto){
        ownerService.updateOwner(id,ownerInputDto);
        return ResponseEntity.noContent().build();
    }

}
