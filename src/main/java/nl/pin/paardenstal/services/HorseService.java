package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.repositories.HorseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HorseService {

    private final HorseRepository horseRepository;

    @Autowired
    public HorseService(HorseRepository horseRepository){
        this.horseRepository = horseRepository;
    }

    List<Horse> horses = new ArrayList<>();
    List<HorseDto> dtos = new ArrayList<>();

    public List<HorseDto> getAllHorses(){
        horses = horseRepository.findAll();

        for(Horse h: horses){
            HorseDto dto = transfertoDto(h);
            dtos.add(dto);
        }
        return dtos;
    }

    public HorseDto getHorse(long id){
        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if(optionalHorse.isPresent()){
            HorseDto horseDto = transfertoDto(optionalHorse.get());
            return horseDto;
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }
    }

    public long addNewHorse(HorseInputDto horseInputDto){
        Horse newHorse = transferToHorse(horseInputDto);
                horseRepository.save(newHorse);
        long newId = newHorse.getId();
        return newId;
    }

    public void updateHorse(long id, HorseInputDto horseInputDto){
        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if (optionalHorse.isPresent()){
            Horse storedHorse = horseRepository.findById(id).orElse(null);

            if(horseInputDto.getName() != null && !horseInputDto.getName().isEmpty()){
                storedHorse.setName(horseInputDto.getName());
            }

            if(horseInputDto.getTypeOfFeed() != null && !horseInputDto.getTypeOfFeed().isEmpty()){
                storedHorse.setTypeOfFeed(horseInputDto.getTypeOfFeed());
            }
            if(horseInputDto.getTypeOfBedding() != null && !horseInputDto.getTypeOfBedding().isEmpty()){
                storedHorse.setTypeOfBedding(horseInputDto.getTypeOfBedding());
            }
            if(horseInputDto.getNameOfVet() != null && !horseInputDto.getNameOfVet().isEmpty()){
                storedHorse.setNameOfVet(horseInputDto.getNameOfVet());
            }
            if(horseInputDto.getResidenceOfVet() != null && !horseInputDto.getResidenceOfVet().isEmpty()){
                storedHorse.setResidenceOfVet(horseInputDto.getResidenceOfVet())    ;
            }
            if(horseInputDto.getTelephoneOfVet() != null && !horseInputDto.getTelephoneOfVet().isEmpty()){
                storedHorse.setTelephoneOfVet(horseInputDto.getTelephoneOfVet());
            }

            horseRepository.save(storedHorse);
        } else {
            throw new RecordNotFoundException("Sorry, can't find any horse by this ID");
        }
    }

    public void deleteHorse(long id) {

        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if (optionalHorse.isPresent()) {
            horseRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("sorry, can't find any horse by this ID");
        }
    }

    public HorseDto transfertoDto(Horse horse){
        HorseDto dto = new HorseDto();

        dto.setId(horse.getId());
        dto.setName(horse.getName());
        dto.setTypeOfFeed(horse.getTypeOfFeed());
        dto.setTypeOfBedding(horse.getTypeOfBedding());
        dto.setNameOfVet(horse.getNameOfVet());
        dto.setResidenceOfVet(horse.getResidenceOfVet());
        dto.setTelephoneOfVet(horse.getTelephoneOfVet());

        return dto;
    }

    public Horse transferToHorse(HorseInputDto horseInputDto){
        Horse horse = new Horse();

        horse.setName(horseInputDto.getName());
        horse.setTypeOfFeed(horseInputDto.getTypeOfFeed());
        horse.setTypeOfBedding(horseInputDto.getTypeOfBedding());
        horse.setNameOfVet(horseInputDto.getNameOfVet());
        horse.setResidenceOfVet(horseInputDto.getResidenceOfVet());
        horse.setTelephoneOfVet(horseInputDto.getTelephoneOfVet());

        return horse;

    }


}
