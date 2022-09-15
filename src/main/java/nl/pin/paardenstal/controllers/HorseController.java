package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.services.HorseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HorseController {

    private final HorseService horseService;

    @Autowired
    public HorseController(HorseService horseService){
        this.horseService = horseService;
    }

    List<Horse> horses = new ArrayList<>();

    @GetMapping("/horses")
    public ResponseEntity<List<Horse>> getAllHorses(){
        horses = horseService.getAllHorses();
        return ResponseEntity.ok(horses);
    }

    @GetMapping("/horses/{id}")
    public ResponseEntity<Horse> getHorse(@PathVariable long id){
        Horse horse = horseService.getHorse(id);
        return ResponseEntity.ok(horse);
    }

    @PostMapping("/horses")
    public ResponseEntity<Object> addNewHorse(@RequestBody Horse horse){
        long newId = horseService.addNewHorse(horse);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/horses/{id}")
    public ResponseEntity<Object> deleteHorse(@PathVariable long id){
        horseService.deleteHorse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/horses/{id}")
    public ResponseEntity<Object> updateHorse(@PathVariable long id, @RequestBody Horse horse){
        horseService.updateHorse(id, horse);
        return ResponseEntity.noContent().build();
    }


}
