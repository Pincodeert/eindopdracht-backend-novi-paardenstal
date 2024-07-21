package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.IdInputDto;
import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.dtos.StallInputDto;
import nl.pin.paardenstal.services.StallService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;



@RestController
public class StallController {

    private final StallService stallService;


    public StallController(StallService stallService){
        this.stallService = stallService;
    }

    @GetMapping("/stalls")
    public ResponseEntity<List<StallDto>> getAllStalls(@RequestParam(name = "type", defaultValue = "") String type,
                                                       @RequestParam(name="isOccupied", defaultValue = "false") boolean isOccupied) {
        List<StallDto> stallDtos = stallService.getAllStalls(type, isOccupied);
        return ResponseEntity.ok(stallDtos);
    }

    @GetMapping("/stalls/isOccupied/{isOccupied}")
    public ResponseEntity<List<StallDto>> getAllStallsByIsOccupied(@PathVariable boolean isOccupied) {
        List<StallDto> dtos = stallService.getAllStallsByIsOccupied(isOccupied);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/stalls/type/{type}")
    public ResponseEntity<List<StallDto>> getAllStallsByType(@PathVariable String type) {
        List<StallDto> dtos = stallService.getAllStallsByType(type);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/stalls/{id}")
    public ResponseEntity<StallDto> getStall(@PathVariable Long id) {
        StallDto stallDto = stallService.getStall(id);
        return ResponseEntity.ok(stallDto);
    }

    @PostMapping("/stalls")
    public ResponseEntity<Object> addStall(@Valid @RequestBody StallInputDto stallInputDto,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            Long newId = stallService.addStall(stallInputDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newId).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PatchMapping("/stalls/{id}")
    public ResponseEntity<Object> updateStall(@PathVariable Long id, @Valid @RequestBody StallInputDto stallInputDto,
                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            stallService.updateStall(id, stallInputDto);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/stalls/{id}")
    public ResponseEntity<Object> deleteStall(@PathVariable Long id) {
        stallService.deleteStall(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/stalls/{id}/horse")
    public ResponseEntity<Object> assignHorseToStall(@PathVariable Long id, @RequestBody IdInputDto input) {
        stallService.assignHorseToStall(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/stalls/{id}")
    public ResponseEntity<Object> removeHorseFromStall(@PathVariable Long id) {
        stallService.removeHorseFromStall(id);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.ok("horse is succesfully removed from stall");
    }


}
