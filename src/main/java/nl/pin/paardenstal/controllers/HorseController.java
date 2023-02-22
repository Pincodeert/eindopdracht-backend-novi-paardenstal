package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.services.HorseService;
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

    List<HorseDto> horseDtos = new ArrayList<>();

    @GetMapping("/horses")
    public ResponseEntity<List<HorseDto>> getAllHorses(){
        horseDtos = horseService.getAllHorses();
        return ResponseEntity.ok(horseDtos);
    }

    @GetMapping("/horses/{id}")
    public ResponseEntity<HorseDto> getHorse(@PathVariable long id){
        HorseDto horseDto = horseService.getHorse(id);
        return ResponseEntity.ok(horseDto);
    }

    @PostMapping("/horses")
    public ResponseEntity<Object> addNewHorse(@RequestBody HorseInputDto horseInputDto){
        long newId = horseService.addNewHorse(horseInputDto);

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
    public ResponseEntity<Object> updateHorse(@PathVariable long id, @RequestBody HorseInputDto horseInputDto){
        horseService.updateHorse(id, horseInputDto);
        return ResponseEntity.noContent().build();
    }


}
