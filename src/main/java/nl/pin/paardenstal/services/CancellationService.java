package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.CancellationDto;
import nl.pin.paardenstal.dtos.CancellationInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Cancellation;
import nl.pin.paardenstal.repositories.CancellationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CancellationService {

    private final CancellationRepository cancellationRepository;

    @Autowired
    public CancellationService(CancellationRepository cancellationRepository){
        this.cancellationRepository = cancellationRepository;
    }

    public List<CancellationDto> getAllCancellations(){
        List<Cancellation> cancellations = cancellationRepository.findAll();
        List<CancellationDto> dtos = new ArrayList<>();

        for(Cancellation c: cancellations){
            CancellationDto dto = transferToDto(c);
            dtos.add(dto);
        }
        return dtos;
    }

    public CancellationDto getCancellation(long id){
        Optional<Cancellation> optionalCancellation = cancellationRepository.findById(id);

        if(optionalCancellation.isPresent()){
            CancellationDto dto = transferToDto(optionalCancellation.get());
            return dto;
        } else {
            throw new RecordNotFoundException("Can't find any cancellation by this ID");
        }
    }

    public long createCancellation(CancellationInputDto inputDto){
        Cancellation newCancellation = transferToCancellation(inputDto);
        cancellationRepository.save(newCancellation);
        long id = newCancellation.getId();
        return id;
    }

    public void deleteCancellation(long id){
        Optional<Cancellation> optionalCancellation = cancellationRepository.findById(id);

        if(optionalCancellation.isPresent()){
            cancellationRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Can't find any cancellation by this ID");
        }
    }

    public CancellationDto transferToDto(Cancellation cancellation){
        CancellationDto dto = new CancellationDto();

        dto.setId(cancellation.getId());
        dto.setEndDate(cancellation.getEndDate());

        return dto;
    }

    public Cancellation transferToCancellation(CancellationInputDto inputDto){
        Cancellation cancellation = new Cancellation();

        cancellation.setEndDate(inputDto.getEndDate());

        return cancellation;
    }

}
