package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.CancellationDto;
import nl.pin.paardenstal.dtos.CancellationInputDto;
import nl.pin.paardenstal.models.Cancellation;
import nl.pin.paardenstal.services.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CancellationController {

    private final CancellationService cancellationService;

    @Autowired
    public CancellationController(CancellationService cancellationService){
        this.cancellationService = cancellationService;
    }

    @GetMapping("/cancellations")
    public ResponseEntity<List<CancellationDto>> getAllCancellations(){
        List<CancellationDto> cancellations = cancellationService.getAllCancellations();
        return ResponseEntity.ok(cancellations);
    }

    @GetMapping("/cancellations/{id}")
    public ResponseEntity<CancellationDto> getCancellation(@PathVariable long id){
        CancellationDto cancellation = cancellationService.getCancellation(id);
        return ResponseEntity.ok(cancellation);
    }

    @PostMapping("/cancellations")
    public ResponseEntity<Long> createCancellation(@RequestBody CancellationInputDto inputDto){
        long newId = cancellationService.createCancellation(inputDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/cancellations/{id}")
    public ResponseEntity<Object> deleteCancellation(@PathVariable long id){
        cancellationService.deleteCancellation(id);
        return ResponseEntity.noContent().build();
    }

}
