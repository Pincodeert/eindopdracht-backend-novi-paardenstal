package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.OwnerDto;
import nl.pin.paardenstal.dtos.OwnerInputDto;
import nl.pin.paardenstal.dtos.UserDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Owner;
import nl.pin.paardenstal.models.User;
import nl.pin.paardenstal.repositories.OwnerRepository;
import nl.pin.paardenstal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, UserRepository userRepository, UserService userService){
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    public List<OwnerDto> getAllOwners(){
        List<Owner>  owners = ownerRepository.findAll();
        List<OwnerDto> dtos = new ArrayList<>();
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
            Owner storedOwner = optionalOwner.get();
            OwnerDto ownerDto = transferToDto(storedOwner);
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
        if(owner.getUser() != null){
            UserDto userDto = userService.transferToDto(owner.getUser());
            dto.setUser(userDto);
        }
        return dto;
    }

    public Owner transferToOwner(OwnerInputDto ownerInputDto){

        Owner owner = new Owner();

        owner.setFirstName(ownerInputDto.getFirstName());
        owner.setLastName(ownerInputDto.getLastName());
        owner.setEmailAddress(ownerInputDto.getEmailAddress());

        return owner;
    }

    public void assignUserToOwner(long id, long userId){
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalOwner.isPresent() && optionalUser.isPresent()){
            Owner owner = optionalOwner.get();
            User user = optionalUser.get();
            owner.setUser(user);
            ownerRepository.save(owner);
        } else if(!optionalOwner.isPresent() && !optionalUser.isPresent()){
            throw new RecordNotFoundException("There's no owner nor user by this ID");
        } else if(!optionalOwner.isPresent()){
            throw new RecordNotFoundException("There's no owner with this ID");
        } else if(!optionalUser.isPresent()){
            throw new RecordNotFoundException("There's no user with this ID");
        }
    }



}
