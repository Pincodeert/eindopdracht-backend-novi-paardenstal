package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.models.Stable;
import nl.pin.paardenstal.services.StableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
public class StableController {

    private final StableService stableService;

    @Autowired
    public StableController(StableService stableService){
        this.stableService= stableService;
    }

    List<Stable> stables = new ArrayList<>();

    @GetMapping("/stables")
    public ResponseEntity<List<Stable>> getAllStables(){
        stables = stableService.getAllStables();
        return ResponseEntity.ok(stables);
    }

    @GetMapping("/stables/{id}")
    public ResponseEntity<Stable> getStable(@PathVariable long id){
        Stable stable = stableService.getStable(id);
        return ResponseEntity.ok(stable);
    }

    @PostMapping("/stables")
    public ResponseEntity<Object> addStable(@RequestBody Stable stable){
        long newId = stableService.addStable(stable);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }



}
