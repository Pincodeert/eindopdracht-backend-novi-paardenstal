package nl.pin.paardenstal.controllers;

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

    List<Owner> owners = new ArrayList<>();

    @GetMapping("/owners")
    public ResponseEntity<List<Owner>> getAllOwners(){
        owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable long id){
        Owner owner = ownerService.getOwner(id);
        return ResponseEntity.ok(owner);
    }

    @PostMapping("/owners")
    public ResponseEntity<Long> addNewOwner(@RequestBody Owner owner){
        long newId = ownerService.createNewOwner(owner);

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
    public ResponseEntity<Object> updateOwner(@PathVariable long id, @RequestBody Owner owner){
        ownerService.updateOwner(id,owner);
        return ResponseEntity.noContent().build();
    }

}
