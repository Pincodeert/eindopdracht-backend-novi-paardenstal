package nl.pin.paardenstal.services;

import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.models.Horse;
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

    public List<Owner> getAllOwners(){
        owners = ownerRepository.findAll();
        return owners;
    }

    public Owner getOwner(long id){

        Optional<Owner> optionalOwner = ownerRepository.findById(id);

        if(optionalOwner.isPresent()){
            return optionalOwner.get();
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }

    }

    public long createNewOwner(Owner owner){
        Owner newOwner = ownerRepository.save(owner);
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

    public void updateOwner(long id, Owner owner){

        Optional<Owner> optionalOwner = ownerRepository.findById(id);

        if(optionalOwner.isPresent()){
            Owner storedOwner = optionalOwner.get();

            Owner updatedOwner = new Owner();
            updatedOwner.setEmailAddress(owner.getEmailAddress());
            ownerRepository.save(updatedOwner);

        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }

    }

}
