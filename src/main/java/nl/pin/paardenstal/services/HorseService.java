package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.CustomerProfileDto;
import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.exceptions.NotYetRemovedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.models.FileUploadResponse;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import nl.pin.paardenstal.repositories.FileUploadRepository;
import nl.pin.paardenstal.repositories.HorseRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HorseService {

    private final HorseRepository horseRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerProfileService customerProfileService;
    private final StallService stallService;
    private final FileUploadRepository fileUploadRepository;


    public HorseService(HorseRepository horseRepository, CustomerProfileRepository customerProfileRepository,
                        @Lazy CustomerProfileService customerProfileService, @Lazy StallService stallService,
                        FileUploadRepository fileUploadRepository) {
        this.horseRepository = horseRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.customerProfileService = customerProfileService;
        this.stallService = stallService;
        this.fileUploadRepository = fileUploadRepository;
    }

    public List<HorseDto> getAllHorses() {
        List<Horse> horses = horseRepository.findAll();
        List<HorseDto> dtos = new ArrayList<>();

        for(Horse h: horses){
            HorseDto dto = transferToDto(h);
            if(h.getOwner() != null) {
                CustomerProfileDto ownerDto = customerProfileService.transferToDto(h.getOwner());
                dto.setOwner(ownerDto);
            }
            if(h.getStall() != null) {
                StallDto stallDto = stallService.transferToDto(h.getStall());
                dto.setStall(stallDto);
            }
            if(h.getPassport() != null) {
                dto.setPassport(h.getPassport());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public HorseDto getHorse(Long id) {
        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if(optionalHorse.isPresent()){
            HorseDto horseDto = transferToDto(optionalHorse.get());

            if(optionalHorse.get().getOwner() != null) {
                CustomerProfileDto ownerDto = customerProfileService.transferToDto(optionalHorse.get().getOwner());
                horseDto.setOwner(ownerDto);
            }
            if(optionalHorse.get().getStall() != null) {
                StallDto stallDto = stallService.transferToDto(optionalHorse.get().getStall());
                horseDto.setStall(stallDto);
            }
            if(optionalHorse.get().getPassport() != null) {
                horseDto.setPassport(optionalHorse.get().getPassport());
            }
            return horseDto;
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }
    }

//    public List<HorseDto> getAllHorsesByCustomerProfileId(Long customerProfileId) {
//        List<HorseDto> dtos = new ArrayList<>();
//        List<Horse> horses = horseRepository.findAllByOwnerId(customerProfileId);
//        for(Horse h: horses) {
//            HorseDto dto = transferToDto(h);
//            if(h.getStall() != null) {
//                StallDto stallDto = stallService.transferToDto(h.getStall());
//                dto.setStall(stallDto);
//            }
//            if(h.getPassport() != null) {
//                dto.setPassport(h.getPassport());
//            }
//            dtos.add(dto);
//        }
//        return dtos;
//    }

    public List<HorseDto> getAllHorsesByCustomerProfileId(Long customerProfileId) {
        List<HorseDto> dtos = new ArrayList<>();
        List<Horse> horses = horseRepository.findAllByOwnerId(customerProfileId);
        for(Horse h: horses) {
            HorseDto dto = transferToDto(h);
            if(h.getStall() != null) {
                StallDto stallDto = stallService.transferToDto(h.getStall());
                dto.setStall(stallDto);
            }
            if(h.getPassport() != null) {
                dto.setPassport(h.getPassport());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public Long addNewHorse(HorseInputDto horseInputDto) {
        Horse horse = transferToHorse(horseInputDto);
        Horse newHorse = horseRepository.save(horse);
        Long newId = newHorse.getId();
        return newId;
    }

    public void updateHorse(Long id, HorseInputDto horseInputDto) {
        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if (optionalHorse.isPresent()){
            Horse storedHorse = horseRepository.findById(id).orElse(null);

            if(horseInputDto.getName() != null && !horseInputDto.getName().isEmpty()){
                storedHorse.setName(horseInputDto.getName());
            }

            if(horseInputDto.getHorseNumber() != null && !horseInputDto.getHorseNumber().isEmpty()) {
                storedHorse.setHorseNumber(horseInputDto.getHorseNumber());
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

    public void deleteHorse(Long id) {

        Optional<Horse> optionalHorse = horseRepository.findById(id);

        if (optionalHorse.isPresent()) {
            Horse horse = optionalHorse.get();
            if(horse.getStall() != null) {
                throw new NotYetRemovedException("First remove horse from stall");
            }
            if(horse.getEnrollment() != null) {
                throw new NotYetRemovedException("There's still an ongoing enrollment, you have to terminate first.");
            }
            if(horse.getOwner() == null) {
                horseRepository.delete(horse);
            } else {
                Horse unAssignedHorse = removeCustomerProfileFromHorse(horse);
                horseRepository.delete(unAssignedHorse);
            }
        } else {
            throw new RecordNotFoundException("sorry, can't find any horse by this ID");
        }
    }

    public Horse removeCustomerProfileFromHorse(Horse horse) {
        horse.setOwner(null);
        Horse lonelyHorse = horseRepository.save(horse);
        return lonelyHorse;
    }

    public HorseDto transferToDto(Horse horse){
        HorseDto dto = new HorseDto();

        dto.setId(horse.getId());
        dto.setName(horse.getName());
        dto.setHorseNumber(horse.getHorseNumber());
        dto.setTypeOfFeed(horse.getTypeOfFeed());
        dto.setTypeOfBedding(horse.getTypeOfBedding());
        dto.setNameOfVet(horse.getNameOfVet());
        dto.setResidenceOfVet(horse.getResidenceOfVet());
        dto.setTelephoneOfVet(horse.getTelephoneOfVet());
        dto.setPreferredSubscription(horse.getPreferredSubscription());

        return dto;
    }

    public Horse transferToHorse(HorseInputDto horseInputDto) {
        Horse horse = new Horse();

        horse.setName(horseInputDto.getName());
        horse.setHorseNumber(horseInputDto.getHorseNumber());
        horse.setTypeOfFeed(horseInputDto.getTypeOfFeed());
        horse.setTypeOfBedding(horseInputDto.getTypeOfBedding());
        horse.setNameOfVet(horseInputDto.getNameOfVet());
        horse.setResidenceOfVet(horseInputDto.getResidenceOfVet());
        horse.setTelephoneOfVet(horseInputDto.getTelephoneOfVet());
        horse.setPreferredSubscription(horseInputDto.getPreferredSubscription());

        return horse;
    }

    public void assignCustomerProfileToHorse(Long horseId, Long ownerId) {
        Optional<Horse> optionalHorse = horseRepository.findById(horseId);
        Optional<CustomerProfile> optionalOwner = customerProfileRepository.findById(ownerId);

        if (optionalHorse.isPresent() && optionalOwner.isPresent()) {
            Horse horse = optionalHorse.get();
            CustomerProfile owner = optionalOwner.get();
            horse.setOwner(owner);
            horseRepository.save(horse);
        } else if (!optionalHorse.isPresent()){
            throw new RecordNotFoundException("sorry, can't find any horse by this ID");
        } else if (!optionalOwner.isPresent()){
            throw new RecordNotFoundException("sorry, can't find any customer by this ID");
        }
    }

    public void assignPassportToHorse(String fileName, Long horseId) {
        Optional<Horse> optionalHorse = horseRepository.findById(horseId);
        Optional<FileUploadResponse> fileUploadResponse = fileUploadRepository.findByFileName(fileName);

        if (optionalHorse.isPresent() && fileUploadResponse.isPresent()) {
            Horse horse = optionalHorse.get();
            FileUploadResponse passport = fileUploadResponse.get();
            horse.setPassport(passport);
            horseRepository.save(horse);
        } else {
            throw new RecordNotFoundException("sorry, can't find any horse by this ID");
        }
    }

}
