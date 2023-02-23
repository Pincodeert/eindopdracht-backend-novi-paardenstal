package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.OwnerDto;
import nl.pin.paardenstal.dtos.OwnerInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Owner;
import nl.pin.paardenstal.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository){
        this.ownerRepository = ownerRepository;
    }

    List<Owner> owners = new ArrayList<>();
    List<OwnerDto> dtos = new ArrayList<>();

    public List<OwnerDto> getAllOwners(){
        owners = ownerRepository.findAll();
        OwnerDto ownerDto = new OwnerDto();
        for(Owner o:owners){
            ownerDto = transferToDto(o);
            dtos.add(ownerDto);
        }
        return dtos;
    }

    public OwnerDto getOwner(long id){

        Optional<Owner> optionalOwner = ownerRepository.findById(id);

        if(optionalOwner.isPresent()){
            OwnerDto ownerDto = transferToDto(optionalOwner.get());
            return ownerDto;
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }

    }

    public long createNewOwner(OwnerInputDto ownerInputDto){
        Owner newOwner = transferToOwner(ownerInputDto);
        ownerRepository.save(newOwner);
        long newId = newOwner.getId();
        return newId;
    }

    public void deleteOwner(long id){

        Optional<Owner> optionalOwner = ownerRepository.findById(id);

        if(optionalOwner.isPresent()){
            ownerRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This ID doesn't exist");
        }
    }

    public void updateOwner(long id, OwnerInputDto ownerInputDto){

        Optional<Owner> optionalOwner = ownerRepository.findById(id);

        if(optionalOwner.isPresent()){
            Owner storedOwner = optionalOwner.get();

            Owner updatedOwner = transferToOwner(ownerInputDto);
            updatedOwner.setId(storedOwner.getId());
            ownerRepository.save(updatedOwner);

        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }
    }

    public OwnerDto transferToDto(Owner owner){

        OwnerDto dto = new OwnerDto();

        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setEmailAddress(owner.getEmailAddress());

        return dto;
    }

    public Owner transferToOwner(OwnerInputDto ownerInputDto){

        Owner owner = new Owner();

        owner.setFirstName(ownerInputDto.getFirstName());
        owner.setLastName(ownerInputDto.getLastName());
        owner.setEmailAddress(ownerInputDto.getEmailAddress());

        return owner;
    }

}
