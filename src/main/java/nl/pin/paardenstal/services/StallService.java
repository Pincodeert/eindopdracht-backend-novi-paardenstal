package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.dtos.StallInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.repositories.StallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StallService {

    private final StallRepository stallRepository;
    private final HorseRepository horseRepository;
    private final HorseService horseService;

    @Autowired
    public StallService(StallRepository stallRepository, HorseRepository horseRepository, HorseService horseService){
        this.stallRepository = stallRepository;
        this.horseRepository = horseRepository;
        this.horseService = horseService;
    }

    List<Stall> stalls = new ArrayList<>();
    List<StallDto> dtos = new ArrayList<>();

    public List<StallDto> getAllStalls(){
        stalls = stallRepository.findAll();
        for(Stall s: stalls){
            StallDto dto = transferToDto(s);
            if(s.getHorse() != null){
                HorseDto horseDto = horseService.transfertoDto(s.getHorse());
                dto.setHorseDto(horseDto);
            }
            dtos.add(dto);
        }

        return dtos;
    }

    public StallDto getStall(long id){
        Optional<Stall> optionalStall = stallRepository.findById(id);

        if(optionalStall.isPresent()){
            StallDto dto = transferToDto(optionalStall.get());
            if(optionalStall.get().getHorse() != null){
                HorseDto horseDto = horseService.transfertoDto(optionalStall.get().getHorse());
                dto.setHorseDto(horseDto);
            }
            return dto;
        } else {
            throw new RecordNotFoundException("this ID does not exist");
        }
    }

    public long addStall(StallInputDto stallInputDto){
        Stall stall = transferToStall(stallInputDto);
        Stall newStall = stallRepository.save(stall);
        long newId = newStall.getId();
        return newId;
    }

    public StallDto transferToDto(Stall stall){
        StallDto dto = new StallDto();

        dto.setId(stall.getId());
        dto.setName(stall.getName());
        dto.setSize(stall.getSize());
        dto.setType(stall.getType());
        return dto;
    }

    public Stall transferToStall(StallInputDto inputDto){
        Stall stall = new Stall();
        stall.setName(inputDto.getName());
        stall.setSize(inputDto.getSize());
        stall.setType(inputDto.getType());
        return stall;
    }

    public void assignHorseToStall(long id, long horseId){
        Optional<Stall> optionalStall = stallRepository.findById(id);
        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if(optionalStall.isPresent() && optionalHorse.isPresent()){
            Stall stall = optionalStall.get();
            Horse horse = optionalHorse.get();
            stall.setHorse(horse);
            stallRepository.save(stall);
        } else if(!optionalStall.isPresent() && !optionalHorse.isPresent()) {
            throw new RecordNotFoundException("Can't find neither a stall by this ID, nor a horse by this ID");
        } else if(!optionalStall.isPresent()){
            throw new RecordNotFoundException("Can't find any stall by this ID");
        } else if(!optionalHorse.isPresent()){
            throw new RecordNotFoundException("Can't find any horse by this ID");
        }
    }


}
