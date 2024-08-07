package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.dtos.IdInputDto;
import nl.pin.paardenstal.models.FileUploadResponse;
import nl.pin.paardenstal.services.HorseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class HorseController {

    private final HorseService horseService;

    private final FileController fileController;


    public HorseController(HorseService horseService, FileController fileController) {
        this.horseService = horseService;
        this.fileController = fileController;
    }

    @GetMapping("/horses")
    public ResponseEntity<List<HorseDto>> getAllHorses() {
        List<HorseDto> horseDtos = horseService.getAllHorses();
        return ResponseEntity.ok(horseDtos);
    }

    @GetMapping("/horses/{id}")
    public ResponseEntity<HorseDto> getHorse(@PathVariable Long id) {
        HorseDto horseDto = horseService.getHorse(id);
        return ResponseEntity.ok(horseDto);
    }

//    @GetMapping("/horses/customerprofile")
//    public ResponseEntity<List<HorseDto>> getAllHorsesByCustomerProfileId(@RequestBody IdInputDto input) {
//        List<HorseDto> dtos = horseService.getAllHorsesByCustomerProfileId(input.id);
//        return ResponseEntity.ok(dtos);
//    }

    @GetMapping("/horses/customerprofile/{id}")
    public ResponseEntity<List<HorseDto>> getAllHorsesByCustomerProfileId(@PathVariable Long id) {
        List<HorseDto> dtos = horseService.getAllHorsesByCustomerProfileId(id);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/horses")
    public ResponseEntity<Object> addNewHorse(@Valid @RequestBody HorseInputDto horseInputDto,
                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            Long newId = horseService.addNewHorse(horseInputDto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newId).toUri();

//            return ResponseEntity.created(location).build();
            return ResponseEntity.created(location).body(newId);
        }
    }

    @PatchMapping("/horses/{id}")
    public ResponseEntity<Object> updateHorse(@PathVariable Long id, @Valid @RequestBody HorseInputDto horseInputDto,
                                              BindingResult bindingResult) {
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

    @DeleteMapping("/horses/{id}")
    public ResponseEntity<Object> deleteHorse(@PathVariable Long id) {
        horseService.deleteHorse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/horses/{id}/customerprofile")
    public ResponseEntity<Object> assignCustomerProfileToHorse(@PathVariable("id") Long horseId, @RequestBody IdInputDto input) {
        horseService.assignCustomerProfileToHorse(horseId, input.id);
        return ResponseEntity.noContent().build();
    }

    //zorgt ervoor dat het paspoort wordt geupload en gelijk gekoppeld aan het paard
    @PostMapping("horses/{id}/passport")
    public ResponseEntity<Object> assignPassportToHorse(@PathVariable("id") Long horseId, @RequestBody MultipartFile file) {
        FileUploadResponse passport = fileController.singleFileUpload(file);
        horseService.assignPassportToHorse(passport.getFileName(), horseId);
        return ResponseEntity.ok(passport.getUrl());
    }

}
