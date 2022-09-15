package nl.pin.paardenstal.services;

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

    public List<Horse> getAllHorses(){
        horses = horseRepository.findAll();
        return horses;
    }

    public Horse getHorse(long id){
        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if(optionalHorse.isPresent()){
            return optionalHorse.get();
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }
    }

    public long addNewHorse(Horse horse){
        Horse newHorse = horseRepository.save(horse);
        long newId = newHorse.getId();
        return newId;
    }

    public void updateHorse(long id, Horse horse){
        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if (optionalHorse.isPresent()){
            Horse storedHorse = horseRepository.findById(id).orElse(null);

            if(horse.getName() != null && !horse.getName().isEmpty()){
                storedHorse.setName(horse.getName());
            }

            if(horse.getTypeOfFeed() != null && !horse.getTypeOfFeed().isEmpty()){
                storedHorse.setTypeOfFeed(horse.getTypeOfFeed());
            }
            if(horse.getTypeOfBedding() != null && !horse.getTypeOfBedding().isEmpty()){
                storedHorse.setTypeOfBedding(horse.getTypeOfBedding());
            }
            if(horse.getNameOfVet() != null && !horse.getNameOfVet().isEmpty()){
                storedHorse.setNameOfVet(horse.getNameOfVet());
            }
            if(horse.getResidenceOfVet() != null && !horse.getResidenceOfVet().isEmpty()){
                storedHorse.setResidenceOfVet(horse.getResidenceOfVet())    ;
            }
            if(horse.getTelephoneOfVet() != null && !horse.getTelephoneOfVet().isEmpty()){
                storedHorse.setTelephoneOfVet(horse.getTelephoneOfVet());
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


}
