package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.dtos.IdInputDto;
import nl.pin.paardenstal.models.FileUploadResponse;
import nl.pin.paardenstal.services.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class HorseController {

    private final HorseService horseService;

    private final FileController fileController;

    @Autowired
    public HorseController(HorseService horseService, FileController fileController){
        this.horseService = horseService;
        this.fileController = fileController;
    }

    @GetMapping("/horses")
    @Transactional
    public ResponseEntity<List<HorseDto>> getAllHorses(){
        List<HorseDto> horseDtos = horseService.getAllHorses();
        return ResponseEntity.ok(horseDtos);
    }

    @GetMapping("/horses/{id}")
    @Transactional
    public ResponseEntity<HorseDto> getHorse(@PathVariable Long id){
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
    public ResponseEntity<Object> deleteHorse(@PathVariable Long id){
        horseService.deleteHorse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/horses/{id}")
    public ResponseEntity<Object> updateHorse(@PathVariable Long id, @Valid @RequestBody HorseInputDto horseInputDto,
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

    @PutMapping("/horses/{id}/customerprofile")
    public ResponseEntity<Object> assignCustomerProfileToHorse(@PathVariable Long horseId, @RequestBody IdInputDto input) {
        horseService.assignCustomerProfileToHorse(horseId, input.id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("horses/{id}/passport")
    public ResponseEntity<Object> assignPassportToHorse(@PathVariable("id") Long horseId, @RequestBody MultipartFile file) {
        FileUploadResponse passport = fileController.singleFileUpload(file);

        horseService.assignPassportToHorse(passport.getFileName(), horseId);

        //zorgt ervoor dat de url voor de get-methode in de response in Postman wordt meegestuurd:
        return ResponseEntity.ok(passport.getUrl());
    }

}
