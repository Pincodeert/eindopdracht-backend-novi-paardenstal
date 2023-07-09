package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.IdInputDto;
import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.dtos.StallInputDto;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.services.StallService;
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
public class StallController {

    private final StallService stallService;

    @Autowired
    public StallController(StallService stallService){
        this.stallService = stallService;
    }

    @GetMapping("/stalls")
    public ResponseEntity<List<StallDto>> getAllStalls(){
        List<StallDto> stallDtos = stallService.getAllStalls();
        return ResponseEntity.ok(stallDtos);
    }

    @GetMapping("/stalls/{id}")
    public ResponseEntity<StallDto> getStall(@PathVariable long id){
        StallDto stallDto = stallService.getStall(id);
        return ResponseEntity.ok(stallDto);
    }

    @PostMapping("/stalls")
    public ResponseEntity<Object> addStall(@Valid @RequestBody StallInputDto stallInputDto,
                                            BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            long newId = stallService.addStall(stallInputDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newId).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping("/stalls/{id}/horse")
    public ResponseEntity<Object> assignHorseToStall(@PathVariable long id, @RequestBody IdInputDto input){
        stallService.assignHorseToStall(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/stalls/{id}/subscription")
    public ResponseEntity<Object> assignSubscriptionToStall(@PathVariable long id, @RequestBody IdInputDto input){
        stallService.assignSubscriptionToStall(id, input.id);
        return ResponseEntity.noContent().build();
    }



}
