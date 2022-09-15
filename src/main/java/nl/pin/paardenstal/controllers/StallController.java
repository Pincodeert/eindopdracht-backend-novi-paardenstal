package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.services.StallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
public class StallController {

    private final StallService stallService;

    @Autowired
    public StallController(StallService stallService){
        this.stallService = stallService;
    }

    List<Stall> stalls = new ArrayList<>();

    @GetMapping("/stalls")
    public ResponseEntity<List<Stall>> getAllStalls(){
        stalls = stallService.getAllStalls();
        return ResponseEntity.ok(stalls);
    }

    @GetMapping("/stalls/{id}")
    public ResponseEntity<Stall> getStall(@PathVariable long id){
        Stall stall = stallService.getStall(id);
        return ResponseEntity.ok(stall);
    }

    @PostMapping("/stalls")
    public ResponseEntity<Object> addStable(@RequestBody Stall stall){
        long newId = stallService.addStall(stall);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }



}
