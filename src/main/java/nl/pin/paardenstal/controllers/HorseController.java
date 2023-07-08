package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.services.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    @GetMapping("/horses")
    public ResponseEntity<List<HorseDto>> getAllHorses(){
        List<HorseDto> horseDtos = horseService.getAllHorses();
        return ResponseEntity.ok(horseDtos);
    }

    @GetMapping("/horses/{id}")
    public ResponseEntity<HorseDto> getHorse(@PathVariable long id){
        HorseDto horseDto = horseService.getHorse(id);
        return ResponseEntity.ok(horseDto);
    }

    @PostMapping("/horses")
    public ResponseEntity<Object> addNewHorse(@Valid @RequestBody HorseInputDto horseInputDto,
                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            long newId = horseService.addNewHorse(horseInputDto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newId).toUri();

            return ResponseEntity.created(location).build();
        }
    }

    @DeleteMapping("/horses/{id}")
    public ResponseEntity<Object> deleteHorse(@PathVariable long id){
        horseService.deleteHorse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/horses/{id}")
    public ResponseEntity<Object> updateHorse(@PathVariable long id, @Valid @RequestBody HorseInputDto horseInputDto,
                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            horseService.updateHorse(id, horseInputDto);
            return ResponseEntity.noContent().build();
        }
    }


}
