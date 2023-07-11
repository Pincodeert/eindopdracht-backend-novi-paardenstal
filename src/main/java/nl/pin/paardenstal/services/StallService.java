package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.*;
import nl.pin.paardenstal.exceptions.AlreadyAssignedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.repositories.StallRepository;
import nl.pin.paardenstal.repositories.SubscriptionRepository;
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
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionService subscriptionService;

    @Autowired
    public StallService(StallRepository stallRepository,
                        HorseRepository horseRepository,
                        HorseService horseService,
                        SubscriptionRepository subscriptionRepository,
                        SubscriptionService subscriptionService){
        this.stallRepository = stallRepository;
        this.horseRepository = horseRepository;
        this.horseService = horseService;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
    }

    public List<StallDto> getAllStalls(String type, boolean isOccupied){
        List<StallDto> dtos = new ArrayList<>();

        if(type.isEmpty()) {
            List<Stall> stalls = stallRepository.findAll();

            for(Stall s: stalls){
                StallDto dto = transferToDto(s);
                if(s.getHorse() != null){
                    HorseDto horseDto = horseService.transferToDto(s.getHorse());
                    dto.setHorse(horseDto);
                }
                dtos.add(dto);
            }
        } else {
            List<Stall> stalls = stallRepository.findAllByTypeAndIsOccupied(type, isOccupied);

            for(Stall s: stalls) {
                StallDto dto = transferToDto(s);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    //zorgt ervoor dat stallen kunnen worden opgehaald obv beschikbaarheid
    public List<StallDto> getAllStallsByIsOccupied(boolean isOccupied) {
        List<StallDto> dtos = new ArrayList<>();
        List<Stall> stalls = stallRepository.findAllByIsOccupied(isOccupied);

        for(Stall s: stalls) {
            StallDto dto = transferToDto(s);
            dtos.add(dto);
        }
        return dtos;
    }

    //zorgt ervoor dat er gezocht kan worden op type stal.
    public List<StallDto> getAllStallsByType(String type) {
        List<StallDto> dtos = new ArrayList<>();
        List<Stall> stalls = stallRepository.findAllByTypeIgnoreCase(type);

        for(Stall s: stalls) {
            StallDto dto = transferToDto(s);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<StallDto> getAllStallsByTypeAndIsOccupied(String type, boolean isOccupied) {
        List<StallDto> dtos = new ArrayList<>();
        List<Stall> stalls = stallRepository.findAllByTypeAndIsOccupied(type, isOccupied);

        for(Stall s: stalls) {
            StallDto dto = transferToDto(s);
            dtos.add(dto);
        }
        return dtos;
    }

    public StallDto getStall(long id){
        Optional<Stall> optionalStall = stallRepository.findById(id);

        if(optionalStall.isPresent()){
            StallDto dto = transferToDto(optionalStall.get());
            if(optionalStall.get().getHorse() != null){
                HorseDto horseDto = horseService.transferToDto(optionalStall.get().getHorse());
                dto.setHorse(horseDto);
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
        dto.setOccupied(stall.isOccupied());
        if(stall.getSubscription() != null){
            SubscriptionDto subscriptionDto = subscriptionService.transferToSubscriptionDto(stall.getSubscription());
            dto.setSubscription(subscriptionDto);
        }
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
        Optional<Horse> optionalHorse = horseRepository.findById(horseId);

        if(!optionalStall.isPresent() && !optionalHorse.isPresent()) {
            throw new RecordNotFoundException("Can't find neither a stall by this ID, nor a horse by this ID");
        } else if(!optionalStall.isPresent()){
            throw new RecordNotFoundException("Can't find any stall by this ID");
        } else if(!optionalHorse.isPresent()){
            throw new RecordNotFoundException("Can't find any horse by this ID");
        }
        Stall stall = optionalStall.get();
        Horse horse = optionalHorse.get();
        //zorgt ervoor dat een paard niet toegewezen kan worden aan een stal die al bezet is en dat een paard niet aan
        // 2 stallen kan worden toegewezen.
        if(stall.getHorse() == null && horse.getStall() == null) {
            stall.setHorse(horse);
            stall.setOccupied(true);
            stallRepository.save(stall);
        } else if (stall.getHorse() != null){
            throw new AlreadyAssignedException("deze stal is al bezet");
        } else if (horse.getStall() != null){
            throw new AlreadyAssignedException("dit paard is al aan een andere stal toegewezen");
        }
    }

    public void assignSubscriptionToStall(long id, long subscriptionId){
        Optional<Stall> optionalStall = stallRepository.findById(id);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if(optionalStall.isPresent() && optionalSubscription.isPresent()){
            Stall stall = optionalStall.get();
            Subscription subscription = optionalSubscription.get();
            stall.setSubscription(subscription);
            stallRepository.save(stall);
        } else if(!optionalStall.isPresent() && !optionalSubscription.isPresent()) {
            throw new RecordNotFoundException("Can't find neither a stall by this ID, nor a subscription by this ID");
        } else if(!optionalStall.isPresent()){
            throw new RecordNotFoundException("Can't find any stall by this ID");
        } else if(!optionalSubscription.isPresent()){
            throw new RecordNotFoundException("Can't find any subscription by this ID");
        }
    }

}
