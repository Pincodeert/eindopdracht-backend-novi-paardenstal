package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.*;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.Owner;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.models.Subscription;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.repositories.OwnerRepository;
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
    private final OwnerRepository ownerRepository;
    private final OwnerService ownerService;

    @Autowired
    public StallService(StallRepository stallRepository,
                        HorseRepository horseRepository,
                        HorseService horseService,
                        SubscriptionRepository subscriptionRepository,
                        SubscriptionService subscriptionService,
                        OwnerRepository ownerRepository,
                        OwnerService ownerService){
        this.stallRepository = stallRepository;
        this.horseRepository = horseRepository;
        this.horseService = horseService;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
        this.ownerRepository = ownerRepository;
        this.ownerService = ownerService;
    }

    public List<StallDto> getAllStalls(){
        List<Stall> stalls = stallRepository.findAll();
        List<StallDto> dtos = new ArrayList<>();

        for(Stall s: stalls){
            StallDto dto = transferToDto(s);
            if(s.getHorse() != null){
                HorseDto horseDto = horseService.transferToDto(s.getHorse());
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
                HorseDto horseDto = horseService.transferToDto(optionalStall.get().getHorse());
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
        if(stall.getSubscription() != null){
            SubscriptionDto subscriptionDto = subscriptionService.transferToSubscriptionDto(stall.getSubscription());
            dto.setSubscription(subscriptionDto);
        }
        if(stall.getOwner() != null){
            OwnerDto ownerDto = ownerService.transferToDto(stall.getOwner());
            dto.setOwner(ownerDto);
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

    public void assignOwnerToStall(long id, long ownerId){
        Optional<Stall> optionalStall = stallRepository.findById(id);
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);

        if(optionalStall.isPresent() && optionalOwner.isPresent()){
            Stall stall = optionalStall.get();
            Owner owner = optionalOwner.get();
            stall.setOwner(owner);
            Stall assignedStall = stallRepository.save(stall);

        } else if(!optionalStall.isPresent() && !optionalOwner.isPresent()) {
            throw new RecordNotFoundException("Can't find neither a stall by this ID, nor an owner by this ID");
        } else if(!optionalStall.isPresent()){
            throw new RecordNotFoundException("Can't find any stall by this ID");
        } else if(!optionalOwner.isPresent()){
            throw new RecordNotFoundException("Can't find any owner by this ID");
        }
    }


}
